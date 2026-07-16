package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractDroid;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.cards.evaluators.ConditionEvaluator;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.ImmuneToTitleModifier;
import com.gempukku.swccgo.logic.modifiers.MayMoveOtherCardsAsReactToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Droid
 * Title: R3-N0 "Nemo"
 */
public class Card305_191 extends AbstractDroid {
    public Card305_191() {
        super(Side.LIGHT, 2, 2, 1, 3, "R3-N0 (Nemo)", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLore("A veteran of The Clone Wars, R3-N0 'Nemo' has seen it all over the years. He's been a long-time companion of Eeno.");
        setGameText("Your starships here with an astromech character aboard are power +1 (or +2 if also at Quermia), immune to Lateral Damage, and may move to systems or sectors as a 'react.'");
        addIcons(Icon.ABT, Icon.NAV_COMPUTER);
        addModelType(ModelType.ASTROMECH);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, PhysicalCard self) {
        Filter filter = Filters.and(Filters.your(self), Filters.starship, Filters.here(self),
                Filters.hasAboard(self, Filters.and(Filters.astromech_droid, Filters.character)));
        List<Modifier> modifiers = new LinkedList<>();
        modifiers.add(new PowerModifier(self, filter, new ConditionEvaluator(1, 2, new AtCondition(self, Filters.Quermia_system))));
        modifiers.add(new ImmuneToTitleModifier(self, filter, Title.Lateral_Damage));
        modifiers.add(new MayMoveOtherCardsAsReactToLocationModifier(self, "Move a starship as a react", self.getOwner(), filter, Filters.or(Filters.system, Filters.sector)));
        return modifiers;
    }
}
