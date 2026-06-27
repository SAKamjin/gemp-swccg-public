package com.gempukku.swccgo.cards.effects;

import com.gempukku.swccgo.common.Zone;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.GameState;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.timing.AbstractSuccessfulEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.results.RelocateToOreChuteAirlockResult;


/**
 * An effect that relocates a card from a location to Ore Chute Airlock.
 */
public class RelocateFromLocationToOreChuteAirlock extends AbstractSuccessfulEffect {
    private PhysicalCard _card;

    /**
     * Creates an effect that relocates a card from a location to Weather Vane.
     * @param action the action performing this effect
     * @param card the card to relocate to Weather Vane
     */
    public RelocateFromLocationToOreChuteAirlock(Action action, PhysicalCard card) {
        super(action);
        _card = card;
    }

    @Override
    protected void doPlayEffect(SwccgGame game) {
        GameState gameState = game.getGameState();
        if (Filters.at(Filters.location).accepts(game, _card)) {
            Zone fromZone = _card.getZone();
            PhysicalCard oreChuteAirlock = Filters.findFirstActive(game, null, Filters.Ore_Chute_Airlock);
            if (oreChuteAirlock != null) {
                // Restore the card back to "normal" and give it a new card id before attaching
                gameState.invertCard(game, _card, false);
                gameState.turnCardSideways(game, _card, true);
                gameState.clearCardStats(_card);
                gameState.relocateCardAsStacked(_card, oreChuteAirlock, false, true);
                gameState.sendMessage(GameUtils.getCardLink(_card) + " is relocated to " + GameUtils.getCardLink(oreChuteAirlock));
                game.getActionsEnvironment().emitEffectResult(new RelocateToOreChuteAirlockResult(_action.getPerformingPlayer(), _card, fromZone));
            }
        }
    }
}
