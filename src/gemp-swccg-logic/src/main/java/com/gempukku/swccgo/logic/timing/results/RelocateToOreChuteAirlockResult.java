package com.gempukku.swccgo.logic.timing.results;

import com.gempukku.swccgo.common.Zone;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.timing.EffectResult;

/**
 * This effect result is triggered when a card is relocated to Ore Chute Airlock.
 */
public class RelocateToOreChuteAirlockResult extends EffectResult {
    private PhysicalCard _card;
    private Zone _fromZone;

    /**
     * Creates an effect result that is triggered when a card is relocated to Ore Chute Airlock.
     * @param performingPlayer the player performing the action
     * @param card the card
     * @param fromZone the zone the card was relocated from
     */
    public RelocateToOreChuteAirlockResult(String performingPlayer, PhysicalCard card, Zone fromZone) {
        super(Type.RELOCATED_TO_ORE_CHUTE_AIRLOCK, performingPlayer);
        _card = card;
        _fromZone = fromZone;
    }

    /**
     * Gets the card relocated to Ore Chute Airlock.
     * @return the card
     */
    public PhysicalCard getCard() {
        return _card;
    }

    /**
     * Gets the zone the card was relocated from.
     * @return the zone the card was relocated from
     */
    public Zone getFromZone() {
        return _fromZone;
    }

    /**
     * Gets the text to show to describe the effect result.
     * @param game the game
     * @return the text
     */
    @Override
    public String getText(SwccgGame game) {
        return "Relocated " + GameUtils.getCardLink(_card) + " to Ore Chute Airlock";
    }
}
