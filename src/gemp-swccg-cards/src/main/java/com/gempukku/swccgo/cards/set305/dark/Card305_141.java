package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractDefensiveShield;
import com.gempukku.swccgo.cards.conditions.OccupiesCondition;
import com.gempukku.swccgo.cards.conditions.OnTableCondition;
import com.gempukku.swccgo.cards.evaluators.NegativeEvaluator;
import com.gempukku.swccgo.cards.evaluators.OccupiesEvaluator;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.UnlessCondition;
import com.gempukku.swccgo.logic.modifiers.KeywordModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PodraceForceLossModifier;
import com.gempukku.swccgo.logic.modifiers.SuspendsCardModifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Defensive Shield
 * Title: You've Never Won A Race?
 */
public class Card305_141 extends AbstractDefensiveShield {
    public Card305_141() {
        super(Side.DARK, PlayCardZoneOption.YOUR_SIDE_OF_TABLE,"A Weapon From A More Civilized Time", ExpansionSet.ABT, Rarity.C);
        setLore("Before the age of lightsabers the Sith Empire battled with Dark Side imbued blades. These blades are still powerful enough to clash with modern lightsabers.");
        setGameText("Plays on table. All cards that target or affect Lightsabers will now target or affect Swords as well.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new KeywordModifier(self, Filters.sword, Keyword.LIGHTSABER));
        return modifiers;
    }
}