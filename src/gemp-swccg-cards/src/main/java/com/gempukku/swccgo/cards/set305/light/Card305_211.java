package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractStarfighter;
import com.gempukku.swccgo.cards.conditions.HasPilotingCondition;
import com.gempukku.swccgo.cards.conditions.PresentCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Colo-Claw Squadron 1
 */
public class Card305_211 extends AbstractStarfighter {
    public Card305_211() {
        super(Side.LIGHT, 3, 2, 3, null, 5, 4, 4, Title.Colo_Claw_Squadron_1, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Modified by the Odan-Urr Navy these A-Wings are the pride of the Resurgent. ");
        setGameText("May add 1 pilot. Power -1 when opponent has a starfighter present with higher maneuver. Immune to attrition < 4 when Green Leader piloting.");
        addIcons(Icon.ABT, Icon.COU, Icon.NAV_COMPUTER, Icon.SCOMP_LINK);
        addModelType(ModelType.A_WING);
        setPilotCapacity(1);
        setMatchingPilotFilter(Filters.Skoo_Wah_Nock);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, new PresentCondition(self, Filters.and(Filters.opponents(self),
                Filters.starfighter, Filters.maneuverHigherThanManeuverOf(self))), -1));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new HasPilotingCondition(self, Filters.Skoo_Wah_Nock), 4));
        return modifiers;
    }
}
