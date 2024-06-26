package com.gempukku.swccgo.cards.set7.light;

import com.gempukku.swccgo.cards.AbstractUsedInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.choose.ChooseAndLoseCardFromHandEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.ReduceBattleDamageEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.RestoreForfeitToNormalEffect;
import com.gempukku.swccgo.logic.effects.TargetCardOnTableEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.results.ForfeitReducedToZeroResult;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: Special Edition
 * Type: Interrupt
 * Subtype: Used
 * Title: First Aid
 */
public class Card7_089 extends AbstractUsedInterrupt {
    public Card7_089() {
        super(Side.LIGHT, 4, "First Aid", Uniqueness.UNIQUE, ExpansionSet.SPECIAL_EDITION, Rarity.F);
        setLore("The Alliance used stolen medical droids to help carry on the battle against the Empire.");
        setGameText("If your character's forfeit was just reduced to 0, restore it to normal. OR Reduce your battle damage by 5 by losing from hand a starship, vehicle, or character. (Immune to Sense.)");
        addIcons(Icon.SPECIAL_EDITION);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, final EffectResult effectResult, final PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();

        // Check condition(s)
        if (TriggerConditions.forfeitJustReducedToZero(game, effectResult, Filters.and(Filters.your(self), Filters.character))) {
            final PhysicalCard cardToRestore = ((ForfeitReducedToZeroResult) effectResult).getCard();
            if (GameConditions.canTarget(game, self, cardToRestore)) {

                final PlayInterruptAction action = new PlayInterruptAction(game, self);
                action.setText("Restore " + GameUtils.getFullName(cardToRestore) + "'s forfeit to normal");
                action.setImmuneTo(Title.Sense);
                // Choose target(s)
                action.appendTargeting(
                        new TargetCardOnTableEffect(action, playerId, "Choose character", cardToRestore) {
                            @Override
                            protected void cardTargeted(final int targetGroupId, final PhysicalCard cardTargeted) {
                                action.addAnimationGroup(cardTargeted);
                                // Allow response(s)
                                action.allowResponses("Restore " + GameUtils.getCardLink(cardToRestore) + "'s forfeit to normal",
                                        new RespondablePlayCardEffect(action) {
                                            @Override
                                            protected void performActionResults(Action targetingAction) {
                                                // Perform result(s)
                                                action.appendEffect(
                                                        new RestoreForfeitToNormalEffect(action, cardToRestore));
                                            }
                                        }
                                );
                            }
                        }
                );
                actions.add(action);
            }
        }

        Filter filter = Filters.or(Filters.starship, Filters.vehicle, Filters.character);

        // Check condition(s)
        if (TriggerConditions.isResolvingBattleDamageAndAttrition(game, effectResult, playerId)
                && GameConditions.isBattleDamageRemaining(game, playerId)
                && GameConditions.hasInHand(game, playerId, filter)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Reduce battle damage by 5");
            action.setImmuneTo(Title.Sense);
            // Pay cost(s)
            action.appendCost(
                    new ChooseAndLoseCardFromHandEffect(action, playerId, filter));
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new ReduceBattleDamageEffect(action, playerId, 5));
                        }
                    }
            );
            actions.add(action);
        }

        return actions;
    }
}