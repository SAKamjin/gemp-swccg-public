package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.ArmedWithCondition;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.*;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Quermian Warrior
 */
public class Card305_183 extends AbstractAlien {
    public Card305_183() {
        super(Side.LIGHT, 3, 2, 2, 1, 3, "Quermian Warrior", Uniqueness.UNRESTRICTED, ExpansionSet.ABT, Rarity.C);
        setLore("Possessing two brains, one located in their head and the other inside their chest cavity. Quermians were created by ancient Arkanian scientists from the Xexto species.");
        setGameText("Deploys -1 to a Quermia site opponent occupies. While with another Quermian, draws one battle destiny if unable to otherwise. May use electropoles and is power +3 while armed with one.");
        addIcons(Icon.ABT, Icon.WARRIOR);
        setSpecies(Species.QUERMIAN);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, -1, Filters.and(Filters.Quermia_site, Filters.occupies(game.getOpponent(self.getOwner())))));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, new WithCondition(self, Filters.Quermian), 1));
        modifiers.add(new MayUseWeaponModifier(self, Filters.electropole));
        modifiers.add(new PowerModifier(self, new ArmedWithCondition(self, Filters.electropole), 3));
        return modifiers;
    }
}
