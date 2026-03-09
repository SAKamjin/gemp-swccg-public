package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.DrawsBattleDestinyIfUnableToOtherwiseModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.common.Species;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Chirdo, Sapphire 4
 */
public class Card305_073 extends AbstractAlien {
    public Card305_073() {
        super(Side.LIGHT, 3, 2, 2, 2, 4, Title.Chirdo, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Assigned to Sapphire 4 during the Battle of Quermia. Chirdo found himself in hot water after embarrassing himself in front of Mihoshi.");
        setGameText("Adds 2 to power of anything he pilots. While aboard Sapphire 4, draws one battle destiny if not able to otherwise, and opponent's droid starfighters are deploy +2 at same system.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT);
        setSpecies(Species.RODIAN);
        addKeywords(Keyword.SAPPHIRE_SQUADRON);
        setMatchingStarshipFilter(Filters.Sapphire_4);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition pilotingBravo4 = new PilotingCondition(self, Filters.Sapphire_4);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, pilotingBravo4, 1));
        modifiers.add(new DeployCostToLocationModifier(self, Filters.and(Filters.opponents(self), Filters.droid_starfighter),
                pilotingBravo4, 2, Filters.sameSystem(self)));
        return modifiers;
    }
}
