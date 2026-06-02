package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractRepublic;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.DrawsBattleDestinyIfUnableToOtherwiseModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Retha Kyr
 */
public class Card305_112 extends AbstractRepublic {
    public Card305_112() {
        super(Side.DARK, 2, 2, 2, 2, 4, "Retha Kyr", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Sephian engineer and pilot who assisted with reactivating the CIS droid factory for the Vauzem Dominion. Specialized in battle droid control programming and interfaces. Hates Morvayne Korr.");
        setGameText("Adds 3 to power of anything she pilots. While aboard a battleship, that battleship is immune to attrition < 4 and draws a battle destiny if not able to otherwise.");
        addIcons(Icon.ABT, Icon.PILOT);
        addKeywords(Keyword.FEMALE);
        setSpecies(Species.SEPHI);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Filter battleshipAboard = Filters.and(Filters.battleship, Filters.hasAboard(self));

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 3));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, battleshipAboard, 4));
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, battleshipAboard, 1));
        return modifiers;
    }
}
