package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.DrawsBattleDestinyIfUnableToOtherwiseModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.TotalWeaponDestinyModifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Captain Skoo-Wah Nock
 */
public class Card305_210 extends AbstractAlien {
    public Card305_210() {
        super(Side.LIGHT, 3, 2, 2, 2, 4, Title.Skoo_Wah_Nock, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("A former pilot for the New Republic, Skoo-Wah Nock signed on with the Odan-Urr Navy to protect his new home. Stationed aboard the Resurgent, he is the leader of Colo-Claw Squadron.");
        setGameText("Adds 2 to power of anything he pilots. When piloting Colo-Claw Squadron 1, draws on battle destiny if not able to otherwise. Adds 3 to total weapon destiny of any starfighter he pilots firing at a starfighter with lower maneuver.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT);
        addKeywords(Keyword.LEADER);
        setMatchingStarshipFilter(Filters.Colo_Claw_Squadron_1);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Filter starfighterPiloted = Filters.and(Filters.starfighter, Filters.hasPiloting(self));

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, new PilotingCondition(self, Filters.Colo_Claw_Squadron_1), 1));
        modifiers.add(new TotalWeaponDestinyModifier(self, Filters.any, starfighterPiloted, 3,
                Filters.and(Filters.starfighter, Filters.maneuverLowerThanManeuverOf(self, starfighterPiloted))));
        return modifiers;
    }
}
