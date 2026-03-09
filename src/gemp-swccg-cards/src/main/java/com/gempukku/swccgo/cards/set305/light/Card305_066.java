package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.DuringBattleAtCondition;
import com.gempukku.swccgo.cards.conditions.HereCondition;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.AndCondition;
import com.gempukku.swccgo.logic.modifiers.AddsBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Ken Iode, Sapphire Leader
 */
public class Card305_066 extends AbstractAlien {
    public Card305_066() {
        super(Side.LIGHT, 2, 3, 3, 3, 6, "Ken Iode, Sapphire Leader", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Leader of Sapphire Squadron during the Battle of Quermia. Formally an Imperial pilot, Ken now flies for Clan Odan-Urr. He's an accomplished mechanic known for tinkering with his ships.");
        setGameText("Adds 3 to power of anything he pilots. While piloting during a battle at a system where you have two piloted Sapphire Squadron starfighters, adds two battle destiny.");
        addPersona(Persona.KEN_IODE);
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT, Icon.WARRIOR);
        addKeywords(Keyword.LEADER, Keyword.SAPPHIRE_SQUADRON);
        setMatchingStarshipFilter(Filters.Sapphire_1);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 3));
        modifiers.add(new AddsBattleDestinyModifier(self, new AndCondition(new PilotingCondition(self, Filters.any),
                new DuringBattleAtCondition(Filters.system), new HereCondition(self, 2, Filters.and(Filters.your(self),
                Filters.piloted, Filters.Sapphire_Squadron_starfigher))), 2));
        return modifiers;
    }
}
