package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractUsedInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.PayRelocateBetweenLocationsCostEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.ModifyLandspeedUntilEndOfTurnEffect;
import com.gempukku.swccgo.logic.effects.RelocateBetweenLocationsEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.TargetCardOnTableEffect;
import com.gempukku.swccgo.logic.effects.choose.ChooseStackedCardEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeStackedCardIntoHandEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Used
 * Title: We've Gotta Get Closer
 */
public class Card305_081 extends AbstractUsedInterrupt {
    public Card305_081() {
        super(Side.LIGHT, 5, "We've Gotta Get Closer", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Guardian Syrena Valkar knew if the Droid Army were to reach the city there would be mass casualties. She pushed to take the fight to the droids early in the conflict.");
        setGameText("Relocate Syrena Valkar to a battle just initiated at an adjacent site. OR Increase Syrena Valkar's landspeed by 1 for remainder of turn. OR If Syrena Valkar on Light Morning Brunch, take her into hand.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, final EffectResult effectResult, final PhysicalCard self) {
        Filter filter = Filters.and(Filters.SYRENA, Filters.canBeRelocatedToLocation(Filters.battleLocation, 0));

        // Check condition(s)
        if (TriggerConditions.battleInitiatedAt(game, effectResult, Filters.adjacentSiteTo(self, filter))
                && GameConditions.canTarget(game, self, filter)) {
            final PhysicalCard battleLocation = game.getGameState().getBattleLocation();

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Relocate Syrena Valkar to battle");
            // Choose target(s)
            action.appendTargeting(
                    new TargetCardOnTableEffect(action, playerId, "Choose Syrena Valkar to relocate", filter) {
                        @Override
                        protected void cardTargeted(int targetGroupId, final PhysicalCard targetedCard) {
                            action.addAnimationGroup(targetedCard);
                            action.addAnimationGroup(battleLocation);
                            // Pay cost(s)
                            action.appendCost(
                                    new PayRelocateBetweenLocationsCostEffect(action, playerId, targetedCard, battleLocation, 0));
                            // Allow response(s)
                            action.allowResponses("Relocate " + GameUtils.getCardLink(targetedCard) + " to " + GameUtils.getCardLink(battleLocation),
                                    new RespondablePlayCardEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Perform result(s)
                                            action.appendEffect(
                                                    new RelocateBetweenLocationsEffect(action, targetedCard, battleLocation));
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
    protected List<PlayInterruptAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();

        Filter filter = Filters.SYRENA;

        // Check condition(s)
        if (GameConditions.canTarget(game, self, filter)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Add 1 to Syrena Valkar's landspeed");
            // Choose target(s)
            action.appendTargeting(
                    new TargetCardOnTableEffect(action, playerId, "Choose Syrena Valkar", filter) {
                        @Override
                        protected void cardTargeted(final int targetGroupId, final PhysicalCard targetedCard) {
                            action.addAnimationGroup(targetedCard);
                            // Allow response(s)
                            action.allowResponses("Add 1 to " + GameUtils.getCardLink(targetedCard) + "'s landspeed",
                                    new RespondablePlayCardEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Get the targeted card(s) from the action using the targetGroupId.
                                            // This needs to be done in case the target(s) were changed during the responses.
                                            final PhysicalCard finalTarget = action.getPrimaryTargetCard(targetGroupId);

                                            // Perform result(s)
                                            action.appendEffect(
                                                    new ModifyLandspeedUntilEndOfTurnEffect(action, finalTarget, 1));
                                        }
                                    }
                            );
                        }
                    }
            );
            actions.add(action);
        }

        Filter filter2 = Filters.and(Filters.Light_Morning_Brunch, Filters.hasStacked(Filters.SYRENA));

        // Check condition(s)
        if (GameConditions.canSpot(game, self, filter2)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Take Syrena Valkar into hand");
            // Choose target(s)
            action.appendTargeting(
                    new ChooseStackedCardEffect(action, playerId, Filters.Light_Morning_Brunch, Filters.SYRENA) {
                        @Override
                        public String getChoiceText(int numCardsToChoose) {
                            return "Choose Syrena Valkar";
                        }
                        @Override
                        protected void cardSelected(final PhysicalCard selectedCard) {
                            action.addAnimationGroup(selectedCard);
                            // Allow response(s)
                            action.allowResponses("Take " + GameUtils.getCardLink(selectedCard) + " into hand",
                                    new RespondablePlayCardEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Perform result(s)
                                            action.appendEffect(
                                                    new TakeStackedCardIntoHandEffect(action, playerId, Filters.hasStacked(selectedCard), selectedCard));
                                        }
                                    }
                            );
                        }
                    }
            );
            actions.add(action);
        }
        return actions;
    }
}