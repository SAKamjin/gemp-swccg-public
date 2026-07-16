package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractStarfighter;
import com.gempukku.swccgo.cards.conditions.HasPilotingCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.MayDeployAsReactModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Salty Trinitaur
 */
public class Card305_196 extends AbstractStarfighter {
    public Card305_196() {
        super(Side.DARK, 1, 5, 4, null, 4, 4, 4, "Salty Trinitaur", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("A modified 'Nite Owl Edition' of the Kom'rk-class Fighter. Also known as the Gauntlet Fighter, the ship was designed by a splinter group of Mandalorians known as the Death Watch.");
        setGameText("May add 1 pilot (must be a Mandalorian) and 3 passengers. May deploy with a pilot as a 'react'. Immune to attrition < 5 if Siorc piloting. Has ship-docking capability.");
        addIcons(Icon.ABT, Icon.INDEPENDENT, Icon.NAV_COMPUTER, Icon.SCOMP_LINK);
        addModelType(ModelType.KOMRK_CLASS_ATTACK_SHIP);
        addKeywords(Keyword.SHIP_DOCKING_CAPABILITY);
        setPilotCapacity(1);
        setPassengerCapacity(3);
        setMatchingPilotFilter(Filters.Siorc);
    }

    @Override
    protected Filter getGameTextValidPilotFilter(String playerId, SwccgGame game, PhysicalCard self) {
        return Filters.Mandalorian;
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayDeployAsReactModifier(self));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new HasPilotingCondition(self, Filters.Siorc), 5));
        return modifiers;
    }
}
