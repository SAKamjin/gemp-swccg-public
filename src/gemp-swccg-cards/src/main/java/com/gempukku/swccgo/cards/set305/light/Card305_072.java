package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.CantSpotCondition;
import com.gempukku.swccgo.cards.conditions.PilotingAtCondition;
import com.gempukku.swccgo.cards.conditions.TotalAbilityPilotingMoreThanCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.AndCondition;
import com.gempukku.swccgo.logic.conditions.UnlessCondition;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.TotalBattleDestinyModifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Ji
 */
public class Card305_072 extends AbstractAlien {
    public Card305_072() {
        super(Side.LIGHT, 1, 5, 4, 6, 6, "Ji", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("");
        setGameText("Adds 2 to power of anything he pilots. While piloting Vesper II and you have no other starships at same system, unless opponent has total ability > 6 piloting here, opponent's total battle destiny here is -3.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT, Icon.WARRIOR);
        addPersona(Persona.JI);
        setMatchingStarshipFilter(Filters.Vesper_II);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        String opponent = game.getOpponent(self.getOwner());

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new TotalBattleDestinyModifier(self, Filters.here(self),
                new AndCondition(new PilotingAtCondition(self, Filters.Vesper_II, Filters.system),
                        new CantSpotCondition(self, Filters.and(Filters.your(self), Filters.starship, Filters.not(Filters.Vesper_II), Filters.atSameSystem(self))),
                        new UnlessCondition(new TotalAbilityPilotingMoreThanCondition(opponent, 6, Filters.here(self)))),
                -3, opponent));
        return modifiers;
    }
}
