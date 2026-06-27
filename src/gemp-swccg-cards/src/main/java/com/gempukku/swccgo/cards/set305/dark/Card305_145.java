package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.AbstractAlienRebel;
import com.gempukku.swccgo.cards.conditions.AtSameLocationAsCondition;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.PlaceCardInUsedPileFromTableEffect;
import com.gempukku.swccgo.logic.modifiers.*;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.PassthruEffect;
import com.gempukku.swccgo.logic.timing.results.AboutToLoseCardFromTableResult;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Slyth
 */
public class Card305_145 extends AbstractAlien {
    public Card305_145() {
        super(Side.DARK, 1, 4, 6, 2, 6, "Slyth", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("A long-time companion of Dag Duh Dug. Slyth has worked as a Smuggler, Bounty Hunter, and more. Is called Trandoboo by Lyn.");
        setGameText("Power +1 at same location as Dag Duh Dug. Adds 2 to power of anything he pilots. When piloting Tātorutaimu, also adds 1 to maneuver. While with opponent's Jedi or Wookiee, opponent may not draw more than one battle destiny here.");
        addPersona(Persona.SLYTH);
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR);
        addKeywords(Keyword.SMUGGLER, Keyword.BOUNTY_HUNTER);
        setSpecies(Species.TRANDOSHAN);
        setMatchingStarshipFilter(Filters.Tatorutaimu);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        String opponent = game.getOpponent(self.getOwner());

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, new AtSameLocationAsCondition(self, Filters.Dag), 1));
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new ManeuverModifier(self, Filters.hasPiloting(self), new PilotingCondition(self, Filters.Tatorutaimu), 1));
        modifiers.add(new MayNotDrawMoreThanBattleDestinyModifier(self, Filters.here(self), new WithCondition(self,
                Filters.and(Filters.opponents(self), Filters.or(Filters.Jedi, Filters.Wookiee))), 1, opponent));
        return modifiers;
    }
}
