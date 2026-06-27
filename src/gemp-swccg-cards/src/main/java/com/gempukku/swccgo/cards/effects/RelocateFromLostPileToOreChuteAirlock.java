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
 * An effect that relocates a card from Lost Pile to Ore Chute Airlock.
 */
public class RelocateFromLostPileToOreChuteAirlock extends AbstractSuccessfulEffect {
    private PhysicalCard _card;

    /**
     * Creates an effect that relocates a card from Lost Pile to Ore Chute Airlock.
     * @param action the action performing this effect
     * @param card the card to relocate to Ore Chute Airlock
     */
    public RelocateFromLostPileToOreChuteAirlock(Action action, PhysicalCard card) {
        super(action);
        _card = card;
    }

    @Override
    protected void doPlayEffect(SwccgGame game) {
        GameState gameState = game.getGameState();
        Zone fromZone = GameUtils.getZoneFromZoneTop(_card.getZone());
        if (fromZone == Zone.LOST_PILE) {
            PhysicalCard oreChuteAirlock = Filters.findFirstActive(game, null, Filters.Ore_Chute_Airlock);
            if (oreChuteAirlock != null) {
                gameState.removeCardFromZone(_card);
                gameState.stackCard(_card, oreChuteAirlock, false, true, false);
                gameState.sendMessage(GameUtils.getCardLink(_card) + " is relocated from " + fromZone.getHumanReadable() + " to " + GameUtils.getCardLink(oreChuteAirlock));
                game.getActionsEnvironment().emitEffectResult(new RelocateToOreChuteAirlockResult(_action.getPerformingPlayer(), _card, fromZone));
            }
        }
    }
}
