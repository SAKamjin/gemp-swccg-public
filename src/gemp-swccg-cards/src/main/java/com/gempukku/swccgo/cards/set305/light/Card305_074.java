package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractStarfighter;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.cards.conditions.HasPilotingCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.ForceDrainModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Sapphire 4
 */
public class Card305_074 extends AbstractStarfighter {
    public Card305_074() {
        super(Side.LIGHT, 2, 2, 3, null, 5, 4, 3, Title.Sapphire_4, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Piloted by Chirdo during the Battle of Quermia. Known for surviving a gear up landing by Chirdo during his flight certification.");
        setGameText("Deploys -2 to Quermia. May add 1 pilot. While Rya piloting, immune to attrition < 4. While at Quermia system, adds 1 to each of your Force drains there.");
        addIcons(Icon.ABT, Icon.COU, Icon.NAV_COMPUTER);
        addKeywords(Keyword.SAPPHIRE_SQUADRON);
        addModelType(ModelType.A_WING);
        setPilotCapacity(1);
        setMatchingPilotFilter(Filters.Chirdo);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, -2, Filters.Deploys_at_Quermia));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new HasPilotingCondition(self, Filters.Chirdo), 4));
        modifiers.add(new ForceDrainModifier(self, Filters.here(self), new AtCondition(self, Filters.Quermia_system), 1, self.getOwner()));
        return modifiers;
    }
}
