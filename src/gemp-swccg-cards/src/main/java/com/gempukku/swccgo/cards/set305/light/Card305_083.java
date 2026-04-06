package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractUsedInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromReserveDeckEffect;
import com.gempukku.swccgo.logic.timing.Action;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Used
 * Title: We're Here To Help
 */
public class Card305_083 extends AbstractUsedInterrupt {
    public Card305_083() {
        super(Side.LIGHT, 5, "We're Here To Help", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Where ever member of Odan-Urr travel they seek to help the local populations find peace and happiness.");
        setGameText("Use 3 Force to take one Effect of any kind into hand from your Reserve Deck; reshuffle. OR Take Vesper II, or one Interrupt with the word 'Jedi(s)' in its game text, into hand from Reserve Deck; reshuffle.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();

        GameTextActionId gameTextActionId = GameTextActionId.WERE_HERE_TO_HELP__UPLOAD_EFFECT;

        // Check condition(s)
        if (GameConditions.canTakeCardsIntoHandFromReserveDeck(game, playerId, self, gameTextActionId)
                && GameConditions.canUseForceToPlayInterrupt(game, playerId, self, 3)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self, gameTextActionId);
            action.setText("Take Effect into hand from Reserve Deck");
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerId, 3));
            // Allow response(s)
            action.allowResponses("Take an Effect of any kind into hand from Reserve Deck",
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new TakeCardIntoHandFromReserveDeckEffect(action, playerId, Filters.Effect_of_any_Kind, true));
                        }
                    }
            );
            actions.add(action);
        }

        gameTextActionId = GameTextActionId.WERE_HERE_TO_HELP__UPLOAD_VESPER_II_OR_INTERRUPT;

        // Check condition(s)
        if (GameConditions.canTakeCardsIntoHandFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self, gameTextActionId);
            action.setText("Take Vesper II or Interrupt into hand from Reserve Deck");
            // Allow response(s)
            action.allowResponses("Take Vesper II, or an Interrupt with the word 'Jedi(s)' in its game text, into hand from Reserve Deck",
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new TakeCardIntoHandFromReserveDeckEffect(action, playerId, Filters.or(Filters.Vesper_II,
                                            Filters.and(Filters.Interrupt, Filters.or(Filters.gameTextContains("Jedi"), Filters.gameTextContains("Jedis")))), true));
                        }
                    }
            );
            actions.add(action);
        }

        return actions;
    }
}