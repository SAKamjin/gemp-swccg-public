package com.gempukku.swccgo.cards.set305.light;

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
import com.gempukku.swccgo.logic.modifiers.EachWeaponDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Location
 * Subtype: Site
 * Title: Quermia: Senate Courtyard
 */
public class Card305_092 extends AbstractSite {
    public Card305_092() {
        super(Side.LIGHT, Title.Senate_Courtyard, Title.Quermia, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLocationLightSideGameText("Add 1 to each of your weapon destiny draws here.");
        addIcon(Icon.DARK_FORCE, 1);
        addIcon(Icon.LIGHT_FORCE, 2);
        addIcons(Icon.ABT, Icon.EXTERIOR_SITE, Icon.PLANET);
        addKeywords(Keyword.QUERMIA_SENATE_SITE);
    }

    @Override
    protected List<Modifier> getGameTextLightSideWhileActiveModifiers(String playerOnLightSideOfLocation, SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new EachWeaponDestinyModifier(self, Filters.and(Filters.your(playerOnLightSideOfLocation), Filters.weapon, Filters.here(self)), 1));
        return modifiers;
    }
}