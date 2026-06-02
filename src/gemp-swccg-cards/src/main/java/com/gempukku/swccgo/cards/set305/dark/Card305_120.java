package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractCapitalStarship;
import com.gempukku.swccgo.cards.AbstractPermanentAboard;
import com.gempukku.swccgo.cards.AbstractPermanentPilot;
import com.gempukku.swccgo.cards.conditions.HereCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.AddsBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Capital
 * Title: Vauzem Dominion Droid Control Ship
 */
public class Card305_120 extends AbstractCapitalStarship {
    public Card305_120() {
        super(Side.DARK, 2, 6, 5, 7, null, 3, 6, "Vauzem Dominion Droid Control Ship", Uniqueness.DIAMOND_1, ExpansionSet.ABT, Rarity.R);
        setLore("These heavily modified battleships are used to control and direct the Vauzem Dominion's automated army. Restored from Clone Wars era junk planets.");
        setGameText("May add 4 pilots, 4 passengers, 2 vehicles, and 4 starfighters. Has ship-docking capability. Permanent pilot provides ability of 2. While a droid starfighter here, adds one battle destiny.");
        addIcons(Icon.ABT, Icon.VAUZEM, Icon.PILOT, Icon.NAV_COMPUTER);
        addKeywords(Keyword.DROID_CONTROL_SHIP);
        addModelType(ModelType.TRADE_FEDERATION_BATTLESHIP);
        setPilotCapacity(4);
        setPassengerCapacity(4);
        setVehicleCapacity(2);
        setStarfighterCapacity(4);
    }

    @Override
    protected List<? extends AbstractPermanentAboard> getGameTextPermanentsAboard() {
        return Collections.singletonList(new AbstractPermanentPilot(2) {});
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsBattleDestinyModifier(self, new HereCondition(self, Filters.droid_starfighter), 1));
        return modifiers;
    }
}
