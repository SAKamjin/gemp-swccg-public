package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractStarfighter;
import com.gempukku.swccgo.cards.conditions.HasAboardCondition;
import com.gempukku.swccgo.cards.evaluators.ConditionEvaluator;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.AndCondition;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.OrCondition;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Eeno's Delta-7b
 */
public class Card305_208 extends AbstractStarfighter {
    public Card305_208() {
        super(Side.LIGHT, 2, 3, 2, null, 4, 5, 6, "Eeno's Delta-7b", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("The Delta-7b was the standard fighter transport for the Jedi Order. The Jedi would often customize the paint color to their personal preference.");
        setGameText("May add one pilot and one astromech.  While Eeno or R3-N0 (Nemo) aboard, immune to attrition < 3 (4 if both).");
        addIcons(Icon.ABT, Icon.NAV_COMPUTER, Icon.SCOMP_LINK, Icon.COU);
        addModelType(ModelType.JEDI_INTERCEPTOR);
        setPilotCapacity(1);
        setAstromechCapacity(1);
        setMatchingPilotFilter(Filters.or(Filters.Eeno, Filters.Nemo));
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition eenoAboard = new HasAboardCondition(self, Filters.Eeno);
        Condition nemoAboard = new HasAboardCondition(self, Filters.Nemo);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new OrCondition(eenoAboard, nemoAboard), new ConditionEvaluator(3, 4, new AndCondition(eenoAboard, nemoAboard))));
        return modifiers;
    }
}
