package com.gempukku.swccgo.cards.set215.light;

import com.gempukku.swccgo.cards.AbstractResistance;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.PresentAtCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.SpotOverride;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.FlipCardEffect;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.modifiers.MayNotBeFlippedModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: Set 15
 * Type: Character
 * Subtype: Resistance
 * Title: Larma D'Acy
 */
public class Card215_015 extends AbstractResistance {
    public Card215_015() {
        super(Side.LIGHT, 2, 3, 3, 3, 5, "Larma D'Acy", Uniqueness.UNIQUE, ExpansionSet.SET_15, Rarity.V);
        setLore("Female commander. Leader.");
        setGameText("Finn and Poe are power +1 here. During your control phase, if with an opponent's spy, opponent loses 1 Force (2 if spy is Undercover). While Leia present at a battleground site, Their Fire Has Gone Out Of The Universe flips and may not flip back.");
        addIcons(Icon.PILOT, Icon.EPISODE_VII, Icon.VIRTUAL_SET_15);
        addKeywords(Keyword.FEMALE, Keyword.COMMANDER, Keyword.LEADER);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<>();
        modifiers.add(new PowerModifier(self, Filters.and(Filters.here(self), Filters.or(Filters.Finn, Filters.Poe)), 1));
        modifiers.add(new MayNotBeFlippedModifier(self, new PresentAtCondition(Filters.Leia, Filters.battleground), Filters.Hunt_Down_And_Destroy_The_Jedi));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(final SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();

        List<RequiredGameTextTriggerAction> actionList = new LinkedList<>();

        // Check condition(s)
        if (TriggerConditions.isTableChanged(game, effectResult)
                && GameConditions.canSpot(game, self, Filters.Leia)
                && GameConditions.isPresentAt(game, Filters.findFirstActive(game, self, Filters.Leia), Filters.battleground)
        ) {
            PhysicalCard theirFireHasGoneOutOfTheUniverse = Filters.findFirstActive(game, self, Filters.Their_Fire_Has_Gone_Out_Of_The_Universe);
            if (theirFireHasGoneOutOfTheUniverse != null
                    && GameConditions.canBeFlipped(game, theirFireHasGoneOutOfTheUniverse)) {

                RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
                action.setSingletonTrigger(true);
                action.setText("Flip " + GameUtils.getFullName(theirFireHasGoneOutOfTheUniverse));
                action.setActionMsg("Flip " + GameUtils.getCardLink(theirFireHasGoneOutOfTheUniverse));
                // Perform result(s)
                action.appendEffect(
                        new FlipCardEffect(action, theirFireHasGoneOutOfTheUniverse));
                actionList.add(action);
            }
        }


        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;
        Filter spyFilter = Filters.and(Filters.opponents(self), Filters.spy, Filters.with(self));

        // Check condition(s)
        // Check if reached end of your control phase and action was not performed yet.
        if (TriggerConditions.isEndOfYourPhase(game, effectResult, Phase.CONTROL, playerId)
                && GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.CONTROL)
                && GameConditions.isWith(game, self, SpotOverride.INCLUDE_UNDERCOVER, spyFilter)) {

            int forceToLose = 1;
            if (GameConditions.isWith(game, self, SpotOverride.INCLUDE_UNDERCOVER, Filters.and(spyFilter, Filters.undercover_spy))) {
                forceToLose = 2;
            }

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setPerformingPlayer(playerId);
            action.setText("Make opponent lose " + forceToLose + " Force");
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, game.getOpponent(playerId), forceToLose));
            actionList.add(action);
        }

        return actionList;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;
        Filter spyFilter = Filters.and(Filters.opponents(self), Filters.spy, Filters.with(self));

        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.CONTROL)
                && GameConditions.isWith(game, self, SpotOverride.INCLUDE_UNDERCOVER, spyFilter)) {
            int forceToLose = 1;
            if (GameConditions.canSpot(game, self, SpotOverride.INCLUDE_UNDERCOVER, Filters.and(spyFilter, Filters.undercover_spy))) {
                forceToLose = 2;
            }

            TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Make opponent lose " + forceToLose + " Force");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, game.getOpponent(playerId), forceToLose));

            return Collections.singletonList(action);
        }
        return null;
    }
}
