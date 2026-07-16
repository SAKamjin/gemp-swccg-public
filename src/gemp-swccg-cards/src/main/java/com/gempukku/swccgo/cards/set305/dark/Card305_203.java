package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractUsedInterrupt;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.decisions.YesNoDecision;
import com.gempukku.swccgo.logic.effects.PlaceUsedPileOnReserveDeckEffect;
import com.gempukku.swccgo.logic.effects.PlayoutDecisionEffect;
import com.gempukku.swccgo.logic.effects.PutCardFromVoidInUsedPileEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Used
 * Title: She's Deaf!
 */
public class Card305_203 extends AbstractUsedInterrupt {
    public Card305_203() {
        super(Side.DARK, 7, "She's Deaf!", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("The Scholae Palatinae Empire is an equal opportunity employer and recognizes each citizens unique abilities.");
        setGameText("If opponent just deployed or moved Lulaire Sol'Vida, yell 'She's Deaf!' After placing interrupt in Used Pile, may place Used Pile on top of Reserve Deck.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, final SwccgGame game, final EffectResult effectResult, final PhysicalCard self) {
        String opponent = game.getOpponent(playerId);

        // Check condition(s)
        if (TriggerConditions.justDeployed(game, effectResult, opponent, Filters.Lula)
                || TriggerConditions.moved(game, effectResult, opponent, Filters.Lula)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Yell 'She's Deaf!'");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new PutCardFromVoidInUsedPileEffect(action, playerId, self) {
                                        @Override
                                        protected final void afterCardPutInCardPile() {
                                            action.appendEffect(
                                                    new PlayoutDecisionEffect(action, playerId,
                                                            new YesNoDecision("Do you want to place Used Pile on top of Reserve Deck?") {
                                                                @Override
                                                                protected void yes() {
                                                                    action.appendEffect(
                                                                            new PlaceUsedPileOnReserveDeckEffect(action, playerId));
                                                                }
                                                                @Override
                                                                protected void no() {
                                                                    game.getGameState().sendMessage(playerId + " chooses to not place Used Pile on top of Reserve Deck");
                                                                }
                                                            }
                                                    )
                                            );
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
}