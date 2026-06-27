package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractLostInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.AddBattleDestinyEffect;
import com.gempukku.swccgo.cards.effects.SatisfyAllBattleDamageAndAttritionEffect;
import com.gempukku.swccgo.cards.effects.choose.ChooseAndLoseCardFromHandEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.CancelCardActionBuilder;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
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
 * Title: Slyth's Sssacrifice
 */
public class Card305_167 extends AbstractLostInterrupt {
    public Card305_167() {
        super(Side.DARK, 4, "Slyth's Sssacrifice", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("'I'd sssacrifice everything for you.'");
        setGameText("If Lyn and Slyth are in a battle together, you may add two battle destiny. OR If Lyn is participating in a battle, you may lose Slyth from hand to satisfy all battle damage and attrition against you. OR Cancel It Can Wait or Innocent Scoundrel.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();

        // Check condition(s)
        if (GameConditions.isDuringBattleWithParticipant(game, Filters.Lyn)
                && GameConditions.isDuringBattleWithParticipant(game, Filters.Slyth)
                && GameConditions.canAddBattleDestinyDraws(game, self)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Add two battle destiny");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new AddBattleDestinyEffect(action, 2));
                        }
                    }
            );
            actions.add(action);
        }
        // Check condition(s)
        if (GameConditions.canTargetToCancel(game, self, Filters.It_Can_Wait)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            // Build action using common utility
            CancelCardActionBuilder.buildCancelCardAction(action, Filters.It_Can_Wait, Title.It_Can_Wait);
            actions.add(action);
        }
        // Check condition(s)
        if (GameConditions.canTargetToCancel(game, self, Filters.Innocent_Scoundrel)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            // Build action using common utility
            CancelCardActionBuilder.buildCancelCardAction(action, Filters.Innocent_Scoundrel, Title.Innocent_Scoundrel);
            actions.add(action);
        }
        return actions;
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        // Check condition(s)
        if (TriggerConditions.isResolvingBattleDamageAndAttrition(game, effectResult, playerId)
                && (GameConditions.isBattleDamageRemaining(game, playerId) || GameConditions.isAttritionRemaining(game, playerId))
                && GameConditions.isDuringBattleWithParticipant(game, Filters.Lyn)
                && GameConditions.hasInHand(game, playerId, Filters.Slyth)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Cancel all remaining battle damage and attrition");
            // Pay cost(s)
            action.appendCost(
                    new ChooseAndLoseCardFromHandEffect(action, playerId, Filters.Slyth));
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new SatisfyAllBattleDamageAndAttritionEffect(action, playerId));
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalBeforeActions(final String playerId, SwccgGame game, final Effect effect, final PhysicalCard self) {
        // Check condition(s)
        if (TriggerConditions.isPlayingCard(game, effect, Filters.or(Filters.It_Can_Wait, Filters.Innocent_Scoundrel))
                && GameConditions.canCancelCardBeingPlayed(game, self, effect)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            // Build action using common utility
            CancelCardActionBuilder.buildCancelCardBeingPlayedAction(action, effect);
            return Collections.singletonList(action);
        }
        return null;
    }
}