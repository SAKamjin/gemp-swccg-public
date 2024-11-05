package com.gempukku.swccgo.cards.conditions;

import com.gempukku.swccgo.common.Filterable;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.state.BattleState;
import com.gempukku.swccgo.game.state.GameState;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.ModifiersQuerying;

/**
 * A condition that is fulfilled during the weapons segment of a battle that includes a card (or at least a specified
 * number of cards) accepted by the specified filter participating.
 */
public class DuringWeaponsSegmentWithParticipantCondition implements Condition {
    private int _count;
    private Filter _filters;

    /**
     * Creates a condition that is fulfilled during the weapons segment of a battle that includes a card accepted by the
     * specified filter participating.
     * @param filters the filter
     */
    public DuringWeaponsSegmentWithParticipantCondition(Filterable filters) {
        _filters = Filters.and(filters);
    }

    /**
     * Creates a condition that is fulfilled during a battle that includes at least a specified number of cards accepted
     * by the specified filter participating.
     * @param count the number of cards
     * @param filters the filter
     */
    public DuringWeaponsSegmentWithParticipantCondition(int count, Filterable filters) {
        _count = count;
        _filters = Filters.and(filters);
    }

    @Override
    public boolean isFulfilled(GameState gameState, ModifiersQuerying modifiersQuerying) {
        if (!gameState.isDuringBattle())
            return false;

        BattleState battleState = gameState.getBattleState();
        if (battleState.isReachedPowerSegment())
            return false;

        Filter filterToUse = Filters.and(Filters.or(_filters, Filters.hasPermanentAboard(_filters), Filters.hasPermanentWeapon(_filters)));
        return Filters.filterCount(battleState.getAllCardsParticipating(), gameState.getGame(), _count, filterToUse).size() >= _count;
    }
}
