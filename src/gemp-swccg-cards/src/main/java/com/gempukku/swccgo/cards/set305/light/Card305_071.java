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
import com.gempukku.swccgo.logic.modifiers.EachBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Sapphire 3
 */
public class Card305_071 extends AbstractStarfighter {
    public Card305_071() {
        super(Side.LIGHT, 2, 2, 3, null, 5, 4, 3, Title.Sapphire_3, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Hand selected by Travitz before the Battle of Quermia. One of the best maintained A-wings available.");
        setGameText("Deploys -2 to Quermia. May add one pilot. While Travitz piloting, immune to attrition < 4. While at Quermia system, opponent's battle destiny draws are -1 here.");
        addIcons(Icon.ABT, Icon.COU, Icon.NAV_COMPUTER);
        addKeywords(Keyword.SAPPHIRE_SQUADRON);
        addModelType(ModelType.A_WING);
        setPilotCapacity(1);
        setMatchingPilotFilter(Filters.Travitz);
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
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new HasPilotingCondition(self, Filters.Travitz), 4));
        modifiers.add(new EachBattleDestinyModifier(self, Filters.here(self), new AtCondition(self, Filters.Quermia_system),
                -1, game.getOpponent(self.getOwner())));
        return modifiers;
    }
}
