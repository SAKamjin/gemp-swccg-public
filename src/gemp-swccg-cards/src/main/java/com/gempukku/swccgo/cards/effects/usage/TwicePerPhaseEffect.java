package com.gempukku.swccgo.cards.effects.usage;

import com.gempukku.swccgo.logic.actions.GameTextAction;

/**
 * An effect is used for a card action that can be performed twice per phase. The effect will be successful if the limit
 * has not yet been reached.
 */
public class TwicePerPhaseEffect extends NumTimesPerPhaseEffect {

    /**
     * Creates an effect that checks if the card's "twice per phase" limit for an action has been reached.
     * @param action the action performing this effect
     */
    public TwicePerPhaseEffect(GameTextAction action) {
        super(action, 2);
    }
}