package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtSameLocationAsCondition;
import com.gempukku.swccgo.cards.conditions.InBattleWithCondition;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.PlaceCardInUsedPileFromTableEffect;
import com.gempukku.swccgo.logic.effects.ReduceAttritionEffect;
import com.gempukku.swccgo.logic.modifiers.*;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.PassthruEffect;
import com.gempukku.swccgo.logic.timing.results.AboutToLoseCardFromTableResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Tiny
 */
public class Card305_150 extends AbstractAlien {
    public Card305_150() {
        super(Side.DARK, 3, 2, 2, 2, 3, "Tiny", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Jawa companion of Hibbity Jibbity. She stowed away on the Tatorutaimu and has been a member of Dag's crew ever since.");
        setGameText("When your total battle destiny at Tiny's site is greater than opponent's total battle destiny, Tiny reduces attrition against you by 3. Your vehicles, starships and droids at same site go to Used Pile (rather than Lost Pile) when they are 'hit.'");
        addPersona(Persona.TINY);
        addIcons(Icon.ABT);
        addKeywords(Keyword.FEMALE);
        setSpecies(Species.JAWA);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ForfeitedToUsedPileModifier(self, Filters.and(Filters.your(self), Filters.or(Filters.vehicle,
                Filters.starship, Filters.droid), Filters.hit, Filters.atSameSite(self))));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();

        // Check condition(s)
        if (TriggerConditions.isInitialAttritionJustCalculated(game, effectResult)
                && GameConditions.isDuringBattleAt(game, Filters.sameSite(self))
                && GameConditions.hasGreaterBattleDestinyTotal(game, playerId, false)
                && GameConditions.isAttritionRemaining(game, playerId)
                && GameConditions.canModifyAttritionAgainst(game, playerId)) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Reduce attrition");
            // Perform result(s)
            action.appendEffect(
                    new ReduceAttritionEffect(action, playerId, 3));
            return Collections.singletonList(action);
        }

        // Check condition(s)
        if (TriggerConditions.isAboutToBeLost(game, effectResult, Filters.and(Filters.your(self), Filters.or(Filters.vehicle,
                Filters.starship, Filters.droid), Filters.hit, Filters.atSameSite(self)))) {
            final AboutToLoseCardFromTableResult result = (AboutToLoseCardFromTableResult) effectResult;
            final PhysicalCard cardToBeLost = result.getCardToBeLost();

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Place " + GameUtils.getFullName(cardToBeLost) + " in Used Pile");
            action.setActionMsg("Place " + GameUtils.getCardLink(cardToBeLost) + " in Used Pile");
            // Perform result(s)
            action.appendEffect(
                    new PassthruEffect(action) {
                        @Override
                        protected void doPlayEffect(SwccgGame game) {
                            result.getPreventableCardEffect().preventEffectOnCard(cardToBeLost);
                            action.appendEffect(
                                    new PlaceCardInUsedPileFromTableEffect(action, result.getCardToBeLost()));
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }
}
