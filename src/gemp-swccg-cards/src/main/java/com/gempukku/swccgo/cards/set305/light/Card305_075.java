package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.PilotingAtCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.AddsDestinyToAttritionModifier;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Nivarra Salk’to, Sapphire 5
 */
public class Card305_075 extends AbstractAlien {
    public Card305_075() {
        super(Side.LIGHT, 2, 2, 2, 2, 4, Title.Nivarra, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Nivarra Salk’to grew up loathing her parents involvement with the Separatists. Vowing to do better she signed up with Odan-Urr's Sapphire squadron.");
        setGameText("Adds 2 to power of anythings she pilots. While piloting Sapphire 5 and at same system as a droid starfighter, adds one destiny to attrition only.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT);
        addKeywords(Keyword.FEMALE, Keyword.SAPPHIRE_SQUADRON);
        setMatchingStarshipFilter(Filters.Sapphire_5);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new AddsDestinyToAttritionModifier(self, new PilotingAtCondition(self, Filters.Sapphire_5,
                Filters.sameSystemAs(self, Filters.droid_starfighter)), 1));
        return modifiers;
    }
}
