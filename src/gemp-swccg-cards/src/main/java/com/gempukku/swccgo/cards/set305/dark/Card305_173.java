package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractLostInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.AddBattleDestinyEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.ModifyTotalPowerUntilEndOfBattleEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.timing.Action;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Lost
 * Title: Scaly Hug
 */
public class Card305_173 extends AbstractLostInterrupt {
    public Card305_173() {
        super(Side.DARK, 4, "Scaly Hug", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Slyth was never close with any of his clutch siblings. While Slyth loaths Kamjin he has formed a strong bond with Komilia and looks after her like a brother.");
        setGameText("If Komilia and Slyth are in a battle together, you may add two battle destiny. OR If you have a Sith and an alien in a battle together, you may add 4 to power only.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();

        // Check condition(s)
        if (GameConditions.isDuringBattleWithParticipant(game, Filters.Komilia)
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
        if (GameConditions.isDuringBattleWithParticipant(game, Filters.and(Filters.your(self), Filters.Sith))
                && GameConditions.isDuringBattleWithParticipant(game, Filters.and(Filters.your(self), Filters.alien))) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Add 4 to power");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new ModifyTotalPowerUntilEndOfBattleEffect(action, 4, playerId, "Adds 4 to power"));
                        }
                    }
            );
            actions.add(action);
        }
        return actions;
    }
}