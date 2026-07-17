package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractDarkJediMasterSith;
import com.gempukku.swccgo.cards.conditions.PresentCondition;
import com.gempukku.swccgo.cards.evaluators.CardMatchesEvaluator;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.UnlessCondition;
import com.gempukku.swccgo.logic.modifiers.*;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Dark Jedi Master/Sith
 * Title: Kamjin Lap'lamiz, Sith Master
 */
public class Card305_140 extends AbstractDarkJediMasterSith {
    public Card305_140() {
        super(Side.DARK, 6, 5, 7, 7, 9, "Kamjin Lap'lamiz, Sith Master", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.UR);
        setLore("When Kamjin first wanted to train his children it split his family apart.  A decade later, he finally is given the chance to pass on what he's learned to Komilia and Rohan. He fears Kai is forever lost to the light.");
        setGameText("May only deploy to a Shadow Academy location. [Pilot] 3. When the mentor, adds 1 to training destiny, 2 if training a Lap'lamiz. Where present, battles and attacks may not occur unless a Light Side character of ability > 3 is present. Immune to attrition.");
        addIcons(Icon.ABT, Icon.WARRIOR, Icon.PILOT);
        addKeywords(Keyword.DARK_COUNCILOR, Keyword.LEADER, Keyword.INSTRUCTOR);
        addPersona(Persona.KAMJIN);
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.Shadow_Academy_location;
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayDeployToShadowAcademyLocationModifier(self));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition unlessLightSideCharacterOfAbilityMoreThanThreePresent = new UnlessCondition(new PresentCondition(self,
                Filters.and(Filters.Light_Side, Filters.character, Filters.abilityMoreThan(3))));
        Filter wherePresent = Filters.wherePresent(self);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new TotalTrainingDestinyModifier(self,
                Filters.jediTestTargetingMentor(Filters.sameCardId(self)),
                new CardMatchesEvaluator(1, 2,
                        Filters.jediTestTargetingApprentice(Filters.Laplamiz_Children_Dark))));
        modifiers.add(new MayNotInitiateBattleAtLocationModifier(self, wherePresent, unlessLightSideCharacterOfAbilityMoreThanThreePresent));
        modifiers.add(new MayNotInitiateAttacksAtLocationModifier(self, wherePresent, unlessLightSideCharacterOfAbilityMoreThanThreePresent));
        modifiers.add(new ImmuneToAttritionModifier(self));
        return modifiers;
    }
}
