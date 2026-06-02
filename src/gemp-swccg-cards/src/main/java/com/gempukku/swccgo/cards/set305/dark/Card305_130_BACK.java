package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractObjective;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.GameTextModificationCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Zone;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.NotCondition;
import com.gempukku.swccgo.logic.effects.CancelCardsOnTableEffect;
import com.gempukku.swccgo.logic.effects.PlaceCardOutOfPlayFromTableEffect;
import com.gempukku.swccgo.logic.effects.RetrieveForceEffect;
import com.gempukku.swccgo.logic.effects.ReturnCardToHandFromTableEffect;
import com.gempukku.swccgo.logic.effects.TargetCardOnTableEffect;
import com.gempukku.swccgo.logic.effects.UnrespondableEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.modifiers.IgnoresLocationDeploymentRestrictionsWhenDeployingToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.JediTestSuspendedInsteadOfLostModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotPlayModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.ModifyGameTextModifier;
import com.gempukku.swccgo.logic.modifiers.ModifyGameTextType;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Objective
 * Title: I Have Neglected Your Training / Your Training Is Now Complete
 */
public class Card305_130_BACK extends AbstractObjective {
    public Card305_130_BACK() {
        super(Side.DARK, 7, Title.Your_Training_Is_Now_Complete, ExpansionSet.ABT, Rarity.R);
        setGameText("Immediately retrieve 10 Force and place destiny card from Sith Test #5 on that Sith Test. While this side up, during your move phase, may use 3 Force to take Komilia into hand from a location you control (cards on Komilia go to owner's Used Pile). Komilia's Sith Test are suspended (not lost) whenever Komilia not on table. Komilia may ignore location deployment restrictions. Opponent may not play Sense or Alter. Place out of play if you Force drain at Shadow Academy location or if Komilia is placed out of play. Cancel Komilia's Sith Tests.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(final SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        final String playerId = self.getOwner();

        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;

        // Check condition(s)
        if (TriggerConditions.cardFlipped(game, effectResult, self)) {
            int amountToRetrieve = 10;

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Retrieve 10 Force");
            action.setActionMsg("Have " + playerId + " retrieve " + amountToRetrieve + " Force");
            // Perform result(s)
            action.appendEffect(
                    new RetrieveForceEffect(action, playerId, amountToRetrieve));
            return Collections.singletonList(action);
        }

        // Check condition(s)
        final Filter komiliaOrRohan = GameConditions.hasGameTextModification(game, self, ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA) ? Filters.Rohan : Filters.Komilia;
        if (TriggerConditions.forceDrainInitiatedBy(game, effectResult, playerId, Filters.Shadow_Academy_location)
                || TriggerConditions.justPlacedOutOfPlayFromTable(game, effectResult, komiliaOrRohan)) {
            Collection<PhysicalCard> jediTests = Filters.filterAllOnTable(game, Filters.jediTestTargetingApprentice(komiliaOrRohan));

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Place out of play");
            action.setActionMsg("Place " + GameUtils.getCardLink(self) + " out of play");
            // Perform result(s)
            action.appendEffect(
                    new PlaceCardOutOfPlayFromTableEffect(action, self));
            action.appendEffect(
                    new CancelCardsOnTableEffect(action, jediTests));
            return Collections.singletonList(action);
        }

        return null;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        boolean targetsRohanInsteadOfKomilia = GameConditions.hasGameTextModification(game, self, ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA);
        final Filter filter = Filters.and(targetsRohanInsteadOfKomilia ? Filters.Rohan : Filters.Komilia, Filters.at(Filters.controls(playerId)));

        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_2;

        // Check condition(s)
        if ((GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, Phase.MOVE)
                || (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, Phase.DEPLOY)
                    && GameConditions.hasGameTextModification(game, self, ModifyGameTextType.YOUR_TRAINING_IS_NOW_COMPLETE__MOVE_PHASE_MAY_BE_TREATED_AS_DEPLOY_PHASE)))
                && GameConditions.canUseForce(game, playerId, 3)
                && GameConditions.canTarget(game, self, filter)) {
            String komiliaOrRohanText = targetsRohanInsteadOfKomilia ? "Rohan" : "Komilia";

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Take " + komiliaOrRohanText + " into hand");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Choose target(s)
            action.appendTargeting(
                    new TargetCardOnTableEffect(action, playerId, "Choose " + komiliaOrRohanText + " to take into hand", filter) {
                        @Override
                        protected void cardTargeted(int targetGroupId, final PhysicalCard targetedCard) {
                            action.addAnimationGroup(targetedCard);
                            // Pay cost(s)
                            action.appendCost(
                                    new UseForceEffect(action, playerId, 3));
                            // Allow response(s)
                            action.allowResponses("Take " + GameUtils.getCardLink(targetedCard) + " into hand",
                                    new UnrespondableEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Perform result(s)
                                            action.appendEffect(
                                                    new ReturnCardToHandFromTableEffect(action, targetedCard, Zone.USED_PILE));
                                        }
                                    }
                            );
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, PhysicalCard self) {
        String playerId = self.getOwner();
        String opponent = game.getOpponent(playerId);
        Condition targetsRohanInsteadOfKomilia = new GameTextModificationCondition(self, ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ModifyGameTextModifier(self, Filters.SITH_TEST_5, ModifyGameTextType.IT_IS_THE_FUTURE_YOU_SEE__STACK_DESTINY_CARD_ON_JEDI_TEST_5));
        modifiers.add(new JediTestSuspendedInsteadOfLostModifier(self, Filters.jediTestTargetingApprentice(Filters.Komilia), new NotCondition(targetsRohanInsteadOfKomilia)));
        modifiers.add(new JediTestSuspendedInsteadOfLostModifier(self, Filters.jediTestTargetingApprentice(Filters.Rohan), targetsRohanInsteadOfKomilia));
        modifiers.add(new IgnoresLocationDeploymentRestrictionsWhenDeployingToLocationModifier(self, Filters.Komilia, new NotCondition(targetsRohanInsteadOfKomilia), Filters.any, true));
        modifiers.add(new IgnoresLocationDeploymentRestrictionsWhenDeployingToLocationModifier(self, Filters.Rohan, targetsRohanInsteadOfKomilia, Filters.any, true));
        modifiers.add(new MayNotPlayModifier(self, Filters.or(Filters.Sense, Filters.Alter), opponent));
        return modifiers;
    }
}