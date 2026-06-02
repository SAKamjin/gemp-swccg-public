package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractDarkJediMasterSith;
import com.gempukku.swccgo.cards.conditions.AloneCondition;
import com.gempukku.swccgo.cards.conditions.ArmedWithCondition;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.cards.evaluators.ConditionEvaluator;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.AndCondition;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.OrCondition;
import com.gempukku.swccgo.logic.modifiers.*;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Dark Jedi Master / Sith
 * Title: Iphis Melinoe
 */
public class Card305_133 extends AbstractDarkJediMasterSith {
    public Card305_133() {
        super(Side.DARK, 6, 8, 5, 7, 8, "Iphis Melinoe", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.UR);
        setLore("A Krath-trained Sith who returned to the Brotherhood after fleeing Lyspair during the Fall of Antei. Despite her public persona as a decadent, shallow mean girl, Iphis has a brilliant and inquisitive mind.");
        setGameText("May deploy as a 'react' to a site. Deploys -2 to a site where opponent has more characters than you. Power +2 when equipped with a sword. While armed with a sword at a site, Force drain +1 here. Immune to Disarmed and attrition < 5 (or < 6 if armed with a sword).");
        addPersona(Persona.IPHIS);
        addIcons(Icon.ABT, Icon.WARRIOR);
        addKeyword(Keyword.FEMALE);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();

        Filter siteWithMoreOpponentsCharacters = Filters.and(Filters.site, Filters.wherePlayerHasFewerCharacters(self, self.getOwner()));
        modifiers.add(new DeployCostToLocationModifier(self, -2, siteWithMoreOpponentsCharacters));
        modifiers.add(new MayDeployAsReactToLocationModifier(self, Filters.site));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition armedWithSword = new ArmedWithCondition(self, Filters.sword);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, armedWithSword, 2));
        modifiers.add(new ForceDrainModifier(self, Filters.sameSite(self), new ArmedWithCondition(self, Filters.sword),
                1, self.getOwner()));
        modifiers.add(new ImmuneToTitleModifier(self, Title.Disarmed));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new ConditionEvaluator(5, 6, new ArmedWithCondition(self, Filters.sword))));
        return modifiers;
    }
}
