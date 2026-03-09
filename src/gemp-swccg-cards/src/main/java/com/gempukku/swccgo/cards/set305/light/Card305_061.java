package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.AboardCondition;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.DefenseValueModifier;
import com.gempukku.swccgo.logic.modifiers.DrawsBattleDestinyIfUnableToOtherwiseModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToTitleModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Odan-Urr Summit Guard
 */
public class Card305_061 extends AbstractAlien {
    public Card305_061() {
        super(Side.LIGHT, 3, 2, 2, 1, 4, "Odan-Urr Summit Guard", Uniqueness.UNRESTRICTED, ExpansionSet.ABT, Rarity.U);
        setArmor(3);
        setLore("Trained guards of the Odan-Urr Summit. Modelled after the Galactic Senate guards their armor is meant to be both protective and ceremonial.");
        setGameText("Odan-Urr members are defense value +2 here.  While with an Odan-Urr member, draws one battle destiny if unable to otherwise and characters here are immune to You Are Beaten.");
        addIcons(Icon.ABT, Icon.COU, Icon.WARRIOR);
        addKeywords(Keyword.COU_SUMMIT_GUARD);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Filter COUAtSameLocation = Filters.and(Filters.COU_character, Filters.atSameLocation(self));
        Condition WithCOUCharacter = new WithCondition(self, Filters.COU_character);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DefenseValueModifier(self, COUAtSameLocation, 2));
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, WithCOUCharacter, 1));
        modifiers.add(new ImmuneToTitleModifier(self, Filters.and(Filters.character, Filters.atSameLocation(self)),WithCOUCharacter, Title.You_Are_Beaten));

        return modifiers;
    }
}