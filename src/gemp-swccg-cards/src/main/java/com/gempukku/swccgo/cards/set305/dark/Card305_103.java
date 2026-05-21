package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractDarkJediMasterSith;
import com.gempukku.swccgo.cards.conditions.ArmedWithCondition;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.cards.evaluators.ConditionEvaluator;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Persona;
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
import com.gempukku.swccgo.logic.modifiers.ImmuneToTitleModifier;
import com.gempukku.swccgo.logic.modifiers.LightsaberCombatForceLossModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Dark Jedi Master / Sith
 * Title: Selika Roh di Plagia, Dread Lord
 */
public class Card305_103 extends AbstractDarkJediMasterSith {
    public Card305_103() {
        super(Side.DARK, 6, 8, 7, 6, 8, "Selika Roh di Plagia, Dread Lord", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.UR);
        setLore("Selika has long pursued a personal goal with Turel; turning the Jedi to the Dark Side. She feels that Turel would make a formidable Sith but, if he won't be turned, he will be destroyed.");
        setGameText("Deploys -2 to Quermia. While with Turel, your battle destinies here are each +1. When Selika wins a lightsaber combat, adds 2 to opponent's Force loss. Immune to Disarmed, Clash Of Sabers, and attrition < 5 (or < 6 if armed with a lightsaber).");
        addPersona(Persona.SELIKA);
        addIcons(Icon.PLAG, Icon.PILOT, Icon.WARRIOR);
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
        modifiers.add(new EachBattleDestinyModifier(self, Filters.here(self), new WithCondition(self, Filters.Turel), 1, self.getOwner()));
        modifiers.add(new LightsaberCombatForceLossModifier(self, 2));
        modifiers.add(new ImmuneToTitleModifier(self, Title.Disarmed));
        modifiers.add(new ImmuneToTitleModifier(self, Title.Clash_Of_Sabers));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, new ConditionEvaluator(5, 6, new ArmedWithCondition(self, Filters.lightsaber))));
        return modifiers;
    }
}
