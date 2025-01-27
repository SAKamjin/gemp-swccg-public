package com.gempukku.swccgo.cards.set302.light;

import com.gempukku.swccgo.cards.AbstractLostInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Filterable;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.TargetingReason;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.AbstractActionProxy;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TriggerAction;
import com.gempukku.swccgo.logic.effects.AddUntilEndOfBattleActionProxyEffect;
import com.gempukku.swccgo.logic.effects.LoseCardFromTableEffect;
import com.gempukku.swccgo.logic.effects.PlaceCardOutOfPlayFromTableEffect;
import com.gempukku.swccgo.logic.effects.ResetForfeitEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.TargetCardOnTableEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Set: Dark Jedi Brotherhood Core
 * Type: Interrupt
 * Subtype: Lost
 * Title: I Will Not Suffer Failure!
 */
public class Card302_030 extends AbstractLostInterrupt {
    public Card302_030() {
        super(Side.LIGHT, 5, "I Will Not Suffer Failure!", Uniqueness.UNRESTRICTED, ExpansionSet.DJB_CORE, Rarity.V);
        setLore("There are few absolutes like the Sith. They are constantly prone to fight amongst themselves and do not suffer failure.");
        setGameText("If Evant is on table when opponent has just initiated a battle, target one opponent's Councilors involved in that battle. If opponent does not win battle, target is forfeit = 0 and is immediately lost (placed out of play if Dacien).");
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, final SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        final String opponent = game.getOpponent(playerId);
        final int gameTextSourceCardId = self.getCardId();

        // Check condition(s)
        if (TriggerConditions.battleInitiated(game, effectResult, opponent)
                && GameConditions.canSpot(game, self, Filters.Evant)) {
            final Filter filter = Filters.and(Filters.opponents(self), Filters.Dark_Councilor, Filters.participatingInBattle);
            final Filter notBubbaFilter = Filters.and(filter, Filters.not(Filters.Bubba));
            final Filter bubbaFilter = Filters.and(filter, Filters.Bubba);
            if (GameConditions.canTarget(game, self, TargetingReason.TO_BE_LOST, notBubbaFilter)
                    || GameConditions.canTarget(game, self, TargetingReason.TO_BE_PLACED_OUT_OF_PLAY, bubbaFilter)) {

                final PlayInterruptAction action = new PlayInterruptAction(game, self);
                action.setText("Target opponent's councilor");
                // Choose target(s)
                Map<TargetingReason, Filterable> targetFiltersMap = new HashMap<TargetingReason, Filterable>();
                targetFiltersMap.put(TargetingReason.TO_BE_LOST, notBubbaFilter);
                targetFiltersMap.put(TargetingReason.TO_BE_PLACED_OUT_OF_PLAY, bubbaFilter);
                action.appendTargeting(
                        new TargetCardOnTableEffect(action, playerId, "Choose councilor", targetFiltersMap) {
                            @Override
                            protected void cardTargeted(final int targetGroupId, PhysicalCard dark_councilor) {
                                action.addAnimationGroup(dark_councilor);
                                // Allow response(s)
                                action.allowResponses("Target " + GameUtils.getCardLink(dark_councilor),
                                        new RespondablePlayCardEffect(action) {
                                            @Override
                                            protected void performActionResults(Action targetingAction) {
                                                // Get the targeted card(s) from the action using the targetGroupId.
                                                // This needs to be done in case the target(s) were changed during the responses.
                                                final PhysicalCard finalLeader = action.getPrimaryTargetCard(targetGroupId);

                                                // Perform result(s)
                                                final int permCardId = self.getPermanentCardId();
                                                action.appendEffect(
                                                        new AddUntilEndOfBattleActionProxyEffect(action,
                                                                new AbstractActionProxy() {
                                                                    @Override
                                                                    public List<TriggerAction> getRequiredAfterTriggers(SwccgGame game, EffectResult effectResult) {
                                                                        List<TriggerAction> actions = new LinkedList<TriggerAction>();
                                                                        final PhysicalCard self = game.findCardByPermanentId(permCardId);

                                                                        // Check condition(s)
                                                                        if ((TriggerConditions.battleTied(game, effectResult) || TriggerConditions.lostBattle(game, effectResult, opponent)
                                                                                && Filters.in_play.accepts(game, finalLeader))) {

                                                                            RequiredGameTextTriggerAction action2 = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
                                                                            action2.appendEffect(
                                                                                    new ResetForfeitEffect(action, finalLeader, 0));
                                                                            if (Filters.Bubba.accepts(game, finalLeader)) {
                                                                                action2.setText("Reset forfeit to 0 and place out of play");
                                                                                action2.appendEffect(
                                                                                        new PlaceCardOutOfPlayFromTableEffect(action2, finalLeader));
                                                                            }
                                                                            else {
                                                                                action2.setText("Reset forfeit to 0 and make lost");
                                                                                action2.appendEffect(
                                                                                        new LoseCardFromTableEffect(action2, finalLeader));
                                                                            }
                                                                            actions.add(action2);
                                                                        }
                                                                        return actions;
                                                                    }
                                                                }
                                                        ));
                                            }
                                        }
                                );
                            }
                        }
                );
                return Collections.singletonList(action);
            }
        }
        return null;
    }
}