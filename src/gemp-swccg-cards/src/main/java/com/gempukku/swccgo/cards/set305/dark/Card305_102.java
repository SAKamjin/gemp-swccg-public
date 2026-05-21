package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractSite;
import com.gempukku.swccgo.cards.conditions.PresentCondition;
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
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.NotCondition;
import com.gempukku.swccgo.logic.modifiers.DockingBayTransitFromCostModifier;
import com.gempukku.swccgo.logic.modifiers.DockingBayTransitFromForFreeModifier;
import com.gempukku.swccgo.logic.modifiers.DockingBayTransitToCostModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Location
 * Subtype: Site
 * Title: Aliso: Docking Bay
 */
public class Card305_102 extends AbstractSite {
    public Card305_102() {
        super(Side.DARK, "Aliso: Docking Bay", Title.Aliso, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLocationDarkSideGameText("Your docking bay transit from here requires 1 Force (free if an Sith present).");
        setLocationLightSideGameText("Your docking bay transit from here requires 3 Force. Your transit to here requires +8 Force.");
        addIcon(Icon.DARK_FORCE, 1);
        addIcons(Icon.ABT, Icon.EXTERIOR_SITE, Icon.PLANET, Icon.SCOMP_LINK);
        addKeywords(Keyword.DOCKING_BAY);
    }

    @Override
    protected List<Modifier> getGameTextDarkSideWhileActiveModifiers(String playerOnDarkSideOfLocation, SwccgGame game, PhysicalCard self) {
        Condition sithPresent = new PresentCondition(self, Filters.Sith);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DockingBayTransitFromCostModifier(self, new NotCondition(sithPresent), 1, playerOnDarkSideOfLocation));
        modifiers.add(new DockingBayTransitFromForFreeModifier(self, sithPresent, playerOnDarkSideOfLocation));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextLightSideWhileActiveModifiers(String playerOnLightSideOfLocation, SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DockingBayTransitFromCostModifier(self, 3, playerOnLightSideOfLocation));
        modifiers.add(new DockingBayTransitToCostModifier(self, 8, playerOnLightSideOfLocation));
        return modifiers;
    }
}