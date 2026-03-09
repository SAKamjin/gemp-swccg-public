package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.DrawsBattleDestinyIfUnableToOtherwiseModifier;
import com.gempukku.swccgo.logic.modifiers.EachBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Frenek Mausma
 */
public class Card305_068 extends AbstractAlien {
    public Card305_068() {
        super(Side.LIGHT, 3, 2, 3, 3, 4, Title.Frenek_Mausma, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Frenek Mausma makes an honest living hauling cargo between systems in the clunky company-owned freighter, lovingly held together with elbow grease.");
        setGameText("Adds 2 to power of anything he pilots. While aboard Sapphire 2, draws one battle destiny if not able to otherwise, and opponent's battle destiny draws are -2 at same system.");
        addIcons(Icon.ABT, Icon.COU, Icon.WARRIOR, Icon.PILOT);
        addPersona(Persona.FRENEK_MAUSMA);
        addKeywords(Keyword.SAPPHIRE_SQUADRON);
        setMatchingStarshipFilter(Filters.Sapphire_2);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition pilotingBravo2 = new PilotingCondition(self, Filters.Sapphire_2);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, pilotingBravo2, 1));
        modifiers.add(new EachBattleDestinyModifier(self, Filters.sameSystem(self), pilotingBravo2, -2, game.getOpponent(self.getOwner())));
        return modifiers;
    }
}
