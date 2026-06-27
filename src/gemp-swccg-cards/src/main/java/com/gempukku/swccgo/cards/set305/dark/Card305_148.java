package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.MayDeployAsReactToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.MayMoveAsReactToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Lyn
 */
public class Card305_148 extends AbstractAlien {
    public Card305_148() {
        super(Side.DARK, 1, 4, 3, 3, 7, "Lyn", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("A former mercenary with the Children of Mortis. Lyn fell in love with Slyth when he and Dag's crew breached their base. She hasn't left his side since and is insanely jealous.");
        setGameText("Adds 1 to power of anything she pilots. May deploy or move as a 'react' to same site as Slyth or Dag. Immune to attrition < 3.");
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR);
        addKeywords(Keyword.FEMALE, Keyword.MERCENARY);
        addPersona(Persona.LYN);
        setSpecies(Species.TWILEK);
    }


    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 1));
        modifiers.add(new MayMoveAsReactToLocationModifier(self, Filters.sameSiteAs(self, Filters.or(Filters.Dag, Filters.Slyth))));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, 3));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayDeployAsReactToLocationModifier(self, Filters.sameSiteAs(self, Filters.or(Filters.Han, Filters.Luke))));
        return modifiers;
    }
}
