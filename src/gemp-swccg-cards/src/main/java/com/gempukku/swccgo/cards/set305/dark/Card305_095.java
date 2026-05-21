package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractSite;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.DockingBayTransitFromCostModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Location
 * Subtype: Site
 * Title: Quermia: Senate Docking Bay
 */
public class Card305_095 extends AbstractSite {
    public Card305_095() {
        super(Side.DARK, "Quermia: Senate Docking Bay", Title.Quermia, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLocationDarkSideGameText("Your docking bay transit from here requires 1 Force.");
        setLocationLightSideGameText("Your docking bay transit from here requires 2 Force.");
        addIcon(Icon.DARK_FORCE, 1);
        addIcon(Icon.LIGHT_FORCE, 1);
        addIcons(Icon.ABT, Icon.SCOMP_LINK, Icon.INTERIOR_SITE, Icon.EXTERIOR_SITE, Icon.PLANET);
        addKeywords(Keyword.DOCKING_BAY, Keyword.QUERMIA_SENATE_SITE);
    }

    @Override
    protected List<Modifier> getGameTextDarkSideWhileActiveModifiers(String playerOnDarkSideOfLocation, SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DockingBayTransitFromCostModifier(self, 1, playerOnDarkSideOfLocation));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextLightSideWhileActiveModifiers(String playerOnLightSideOfLocation, SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DockingBayTransitFromCostModifier(self, 2, playerOnLightSideOfLocation));
        return modifiers;
    }
}