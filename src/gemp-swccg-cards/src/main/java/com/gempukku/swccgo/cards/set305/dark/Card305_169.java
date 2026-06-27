package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractLostInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.usage.OncePerGameEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.TargetingReason;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.CancelCardActionBuilder;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.TakeFirstBattleWeaponsSegmentActionEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.Effect;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Lost
 * Title: It Is Expected
 */
public class Card305_169 extends AbstractLostInterrupt {
    public Card305_169() {
        super(Side.DARK, 6, "It Is Expected", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setVirtualSuffix(true);
        setLore("Failure is not an option for Sith training. You are expected to succeed or perish.");
        setGameText("Cancel A Jedi's Resilience, Rebel Barrier (targeting your Dark Jedi), Blast The Door, Kid! (where your Dark Jedi participating), or Clash of Sabers (except when canceling Chaotic Komilia). [Immune to Sense] OR Once per game, if opponent just initiated a battle, you may take the first weapons segment action.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalBeforeActions(String playerId, SwccgGame game, Effect effect, PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();
        Filter yourDarkJedi = Filters.and(Filters.your(self), Filters.Dark_Jedi);

        // Check condition(s)
        if (TriggerConditions.isPlayingCard(game, effect, Filters.A_Jedis_Resilience)
                && GameConditions.canCancelCardBeingPlayed(game, self, effect)) {

            PlayInterruptAction action = new PlayInterruptAction(game, self);
            // Build action using common utility
            CancelCardActionBuilder.buildCancelCardBeingPlayedAction(action, effect);
            action.setImmuneTo(Title.Sense);
            actions.add(action);
        }

        // Check condition(s)
        if (TriggerConditions.isPlayingCardTargeting(game, effect, Filters.Rebel_Barrier, yourDarkJedi)
                && GameConditions.canCancelCardBeingPlayed(game, self, effect)) {

            PlayInterruptAction action = new PlayInterruptAction(game, self);
            // Build action using common utility
            CancelCardActionBuilder.buildCancelCardBeingPlayedAction(action, effect);
            action.setImmuneTo(Title.Sense);
            actions.add(action);
        }

        // Check condition(s)
        if (TriggerConditions.isPlayingCard(game, effect, Filters.Blast_The_Door_Kid)
                && GameConditions.isDuringBattleWithParticipant(game, yourDarkJedi)
                && GameConditions.canCancelCardBeingPlayed(game, self, effect)) {

            PlayInterruptAction action = new PlayInterruptAction(game, self);
            // Build action using common utility
            CancelCardActionBuilder.buildCancelCardBeingPlayedAction(action, effect);
            action.setImmuneTo(Title.Sense);
            actions.add(action);
        }

        // Check condition(s)
        if (TriggerConditions.isPlayingCard(game, effect, Filters.Clash_Of_Sabers)
                && !TriggerConditions.isPlayingCardTargeting(game, effect, Filters.Clash_Of_Sabers, TargetingReason.TO_BE_CANCELED, Filters.Chaotic_Komilia)
                && GameConditions.canCancelCardBeingPlayed(game, self, effect)) {

            PlayInterruptAction action = new PlayInterruptAction(game, self);
            // Build action using common utility
            CancelCardActionBuilder.buildCancelCardBeingPlayedAction(action, effect);
            action.setImmuneTo(Title.Sense);
            actions.add(action);
        }

        return actions;
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        final String opponent = game.getOpponent(playerId);

        GameTextActionId gameTextActionId = GameTextActionId.IT_IS_EXPECTED__TAKE_FIRST_WEAPONS_SEGMENT_ACTION;

        // Check condition(s)
        if (TriggerConditions.battleInitiated(game, effectResult, opponent)
                && GameConditions.isOncePerGame(game, self, gameTextActionId)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self, gameTextActionId);
            action.setText("Take first weapons segment action");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerGameEffect(action));
            // Allow response(s)
            action.allowResponses("Take the first weapons segment action",
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new TakeFirstBattleWeaponsSegmentActionEffect(action, playerId));
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }
}