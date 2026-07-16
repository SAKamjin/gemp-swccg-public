package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.DuringBattleWithParticipantCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.MayMoveAsReactToLocationForFreeModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Odan-Urr Padawan
 */
public class Card305_199 extends AbstractAlien {
    public Card305_199() {
        super(Side.LIGHT, 3, 3, 3, 3, 3, "Odan-Urr Padawan", Uniqueness.UNRESTRICTED, ExpansionSet.ABT, Rarity.C);
        setLore("A padawan's journey is tied to that of their master. Odan-Urr padawans rarely are seen in the Velastari temple as the Council believes first hand experience is best.");
        setGameText("May move as a 'react' (for free) to a battle where you have a [COU] character of ability > 3 or a leader.");
        addIcons(Icon.WARRIOR, Icon.ABT, Icon.COU);
        addKeywords(Keyword.PADAWAN);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayMoveAsReactToLocationForFreeModifier(self, new DuringBattleWithParticipantCondition(Filters.and(Filters.your(self),
                Filters.or(Filters.and(Filters.COU_character, Filters.abilityMoreThan(3)), Filters.leader))), Filters.battleLocation));
        return modifiers;
    }
}
