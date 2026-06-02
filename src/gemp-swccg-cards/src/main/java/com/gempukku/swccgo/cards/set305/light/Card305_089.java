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
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.AddUntilEndOfTurnModifierEffect;
import com.gempukku.swccgo.logic.effects.DrawDestinyAndChooseInsteadEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.AttemptToBlowAwayEchoofVauzemTotalModifier;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Place
 * Type: Interrupt
 * Subtype: Used
 * Title: Here's Your Answer
 */
public class Card305_089 extends AbstractUsedInterrupt {
    public Card305_089() {
        super(Side.LIGHT, 5, "Here's Your Answer", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLore("'For Light and Life!'");
        setGameText("If you are about to draw weapon destiny for Concussion Missiles, instead draw 3 and choose 1. OR For rest of turn, add 2 to any attempt to 'blow away' Echo of Vauzem. OR Take Concussion Missiles or Sapphire Fighter into hand from Reserve Deck; reshuffle.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        // Check condition(s)
        if (TriggerConditions.isAboutToDrawWeaponDestiny(game, effectResult, playerId, Filters.Concussion_Missiles)
                && GameConditions.canDrawDestinyAndChoose(game, 3)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Draw three and choose one");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new DrawDestinyAndChooseInsteadEffect(action, 3, 1));
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

        // Check condition(s)
        if (GameConditions.canSpot(game, self, Filters.ECHO_OF_VAUZEM)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Add 2 to attempts to 'blow away' Echo of Vauzem");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new AddUntilEndOfTurnModifierEffect(action,
                                            new AttemptToBlowAwayEchoofVauzemTotalModifier(self, 2),
                                            "Adds 2 to attempts to 'blow away' Echo of Vauzem"));
                        }
                    }
            );
            actions.add(action);
        }

        GameTextActionId gameTextActionId = GameTextActionId.TAKE_THIS__UPLOAD_PROTON_TORPEDOES_OR_BRAVO_FIGHTER;

        // Check condition(s)
        if (GameConditions.canTakeCardsIntoHandFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self, gameTextActionId);
            action.setText("Take card into hand from Reserve Deck");
            // Allow response(s)
            action.allowResponses("Take Concussion Missiles or Sapphire Fighter into hand from Reserve Deck",
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new TakeCardIntoHandFromReserveDeckEffect(action, playerId, Filters.or(Filters.Concussion_Missiles, Filters.Sapphire_Fighter), true));
                        }
                    }
            );
            actions.add(action);
        }
        return actions;
    }
}