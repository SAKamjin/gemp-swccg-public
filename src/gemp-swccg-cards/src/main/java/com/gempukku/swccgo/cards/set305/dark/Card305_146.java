package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractStarfighter;
import com.gempukku.swccgo.cards.conditions.HasPilotingCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Tatorutaimu
 */
public class Card305_146 extends AbstractStarfighter {
    public Card305_146() {
        super(Side.DARK, 1, 3, 3, null, 4, 4, 7, "Tatorutaimu", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Originally owned by Kamjin, the Tatortutaimu was gifted to Dag and his gang of adventurers. Dag has spent considerable resources keeping the ship operational.");
        setGameText("May add 2 pilots and 3 passengers. Immune to attrition < 5 if Dag, Slyth, or Hibbity Jibbity piloting. Has ship-docking capabilitiy.");
        addPersona(Persona.TATORUTAIMU);
        addIcons(Icon.NAV_COMPUTER, Icon.SCOMP_LINK);
        addModelType(ModelType.MODIFIED_LIGHT_FREIGHTER);
        addKeywords(Keyword.SHIP_DOCKING_CAPABILITY);
        setPilotCapacity(2);
        setPassengerCapacity(3);
        setMatchingPilotFilter(Filters.or(Filters.Dag, Filters.Slyth, Filters.Hibbity));
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new HasPilotingCondition(self, Filters.or(Filters.Dag, Filters.Slyth, Filters.Hibbity)), 5));
        return modifiers;
    }
}
