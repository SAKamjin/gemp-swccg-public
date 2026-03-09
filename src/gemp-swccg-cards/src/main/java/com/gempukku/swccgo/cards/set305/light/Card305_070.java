package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.PilotingAtCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.AddsDestinyToAttritionModifier;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Travitz, Sapphire 3
 */
public class Card305_070 extends AbstractAlien {
    public Card305_070() {
        super(Side.LIGHT, 2, 6, 4, 6, 7, "Travitz, Sapphire 3", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Assigned to pilot Sapphire 3 during the Battle of Quermia. Also known as the 'Last Knight of Alderaan', he is the final incarnation of an unnamed Sith Sorcerer known as the Excoriator.");
        setGameText("Deploys -4 to Quermia. Adds 2 to power of anything he pilots. While piloting Sapphire 3 at same system as a battleship, adds one destiny to attrition only.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT, Icon.WARRIOR);
        addKeywords(Keyword.SAPPHIRE_SQUADRON);
        addPersona(Persona.TRAVITZ);
        setMatchingStarshipFilter(Filters.Sapphire_3);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, -4, Filters.Deploys_at_Quermia));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new AddsDestinyToAttritionModifier(self, new PilotingAtCondition(self, Filters.Sapphire_3,
                Filters.sameSystemAs(self, Filters.battleship)), 1));
        return modifiers;
    }
}
