package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractStarfighter;
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
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.DrawsBattleDestinyIfUnableToOtherwiseModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Sapphire 1
 */
public class Card305_067 extends AbstractStarfighter {
    public Card305_067() {
        super(Side.LIGHT, 3, 2, 3, null, 5, 4, 3, Title.Sapphire_1, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Ken Iode's A-wing starfighter at the Battle of Quermia. Specially modified by Ken himself for greater response times.");
        setGameText("Deploys -2 to Quermia. May add 1 pilot. While Ken Iode piloting, immune to attrition < 4 and draws one battle destiny if unable to otherwise.");
        addIcons(Icon.ABT, Icon.COU, Icon.NAV_COMPUTER);
        addKeywords(Keyword.SAPPHIRE_SQUADRON);
        addModelType(ModelType.A_WING);
        setPilotCapacity(1);
        setMatchingPilotFilter(Filters.Ken_Iode);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, -2, Filters.Deploys_at_Quermia));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition ricPiloting = new HasPilotingCondition(self, Filters.Ken_Iode);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, ricPiloting, 4));
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, ricPiloting, 1));
        return modifiers;
    }
}
