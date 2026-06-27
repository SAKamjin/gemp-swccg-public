package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractSite;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Location
 * Subtype: Site
 * Title: Danktooine: Lower Corridor
 */
public class Card305_143 extends AbstractSite {
    public Card305_143() {
        super(Side.DARK, Title.Danktooine_Lower_Corridor, Title.Danktooine, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLocationDarkSideGameText("Your characters with lightsabers are each power +2 here.");
        addIcon(Icon.DARK_FORCE, 2);
        addIcon(Icon.LIGHT_FORCE, 1);
        addIcons(Icon.ABT, Icon.INTERIOR_SITE, Icon.SCOMP_LINK);
        addKeywords(Keyword.DANKTOOINE_LOCATION);
    }

    @Override
    protected List<Modifier> getGameTextDarkSideWhileActiveModifiers(String playerOnDarkSideOfLocation, SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, Filters.and(Filters.your(playerOnDarkSideOfLocation), Filters.character_with_a_lightsaber, Filters.here(self)), 2));
        return modifiers;
    }
}