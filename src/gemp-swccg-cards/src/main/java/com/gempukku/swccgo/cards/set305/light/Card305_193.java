package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractDroid;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AboardCondition;
import com.gempukku.swccgo.cards.evaluators.CardMatchesEvaluator;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.effects.DeionizeStarshipEffect;
import com.gempukku.swccgo.logic.evaluators.Evaluator;
import com.gempukku.swccgo.logic.modifiers.HyperspeedModifier;
import com.gempukku.swccgo.logic.modifiers.ManeuverModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Droid
 * Title: R5-S7 (Lazy)
 */
public class Card305_193 extends AbstractDroid {
    public Card305_193() {
        super(Side.LIGHT, 4, 2, 1, 3, "R5-S7 (Lazy)", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("eHas seen service with the Separatist Alliance, Galactic Empire, First Order, and the Collective. He's always on the wrong side of a  conflict. Currently with the Brotherhood...hopefully for the better.");
        setGameText("While aboard any freighter, adds 2 to power, maneuver, and hyperspeed (3 on The Argentum Baet). During your control phase, if aboard your starship damaged by an Ion cannon, restores armor/maneuver and hyperspeed.");
        addModelType(ModelType.ASTROMECH);
        addIcons(Icon.ABT, Icon.NAV_COMPUTER);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition aboardFreighter = new AboardCondition(self, Filters.freighter);
        Filter freighterAboard = Filters.and(Filters.freighter, Filters.hasAboard(self));
        Evaluator onArgentumBaetEvaluator = new CardMatchesEvaluator(2, 3, Filters.Argentum_Baet);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, freighterAboard, aboardFreighter, onArgentumBaetEvaluator));
        modifiers.add(new ManeuverModifier(self, freighterAboard, aboardFreighter, onArgentumBaetEvaluator));
        modifiers.add(new HyperspeedModifier(self, freighterAboard, aboardFreighter, onArgentumBaetEvaluator));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_2;
        // Check condition(s)
        // Check if reached end of each control phase and action was not performed yet.
        // Check if this card is aboard a ship that has been ionized
        if (TriggerConditions.isEndOfYourPhase(game, effectResult, Phase.CONTROL, playerId)
                && GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.CONTROL)
                && GameConditions.isAboard(game, self, Filters.and(Filters.or(Filters.starship_defense_ionized, Filters.starship_hyperspeed_ionized), Filters.your(self)))) {

            PhysicalCard starship = Filters.findFirstActive(game, self, Filters.and(Filters.or(Filters.starship_defense_ionized, Filters.starship_hyperspeed_ionized), Filters.your(self), Filters.hasAboard(self)));
            if (starship != null) {
                final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
                action.setText("Restore armor/maneuver and hyperspeed");
                // Perform result(s)
                action.appendEffect(
                        new DeionizeStarshipEffect(action, starship, false, true, true));
                return Collections.singletonList(action);
            }
        }
        return null;
    }
}