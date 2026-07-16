package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractMandalorian;
import com.gempukku.swccgo.cards.conditions.EscortingCaptiveCondition;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.GameState;
import com.gempukku.swccgo.logic.modifiers.*;
import com.gempukku.swccgo.logic.modifiers.querying.ModifiersQuerying;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Mandalorian
 * Title: Siorc
 */
public class Card305_195 extends AbstractMandalorian {
    public Card305_195() {
        super(Side.DARK, 1, 6, 5, 4, 7, "Siorc", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setArmor(5);
        setLore("A formidable Evereni warrior and bounty hunter. A trainee and foundling of Korvis Manda'vod, Siorc now operates as a nomad. Known for his relentless heavy assault tactics and personal code of honor.");
        setGameText("[Pilot] 3. When piloting Salty Trinitaur, also adds 2 to maneuver and may draw one battle destiny if not able to otherwise. Opponent's characters of lesser ability are power -1 here. When escorting a captive, captive is forfeit +5. Immune to attrition < 3.");
        addPersona(Persona.SIORC);
        addIcons(Icon.ABT, Icon.VIZSLA, Icon.PILOT, Icon.WARRIOR);
        addKeywords(Keyword.BOUNTY_HUNTER);
        setMatchingStarshipFilter(Filters.Salty_Trinitaur);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
            Filter abilityLessThanSelf = new Filter() {
                @Override
                public boolean accepts(GameState gameState, ModifiersQuerying modifiersQuerying, PhysicalCard physicalCard) {
                    return modifiersQuerying.getAbility(gameState, physicalCard) < modifiersQuerying.getAbility(gameState, self);
                }
            };

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 3));
        modifiers.add(new ManeuverModifier(self, Filters.and(Filters.Salty_Trinitaur, Filters.hasPiloting(self)), 2));
        modifiers.add(new DrawsBattleDestinyIfUnableToOtherwiseModifier(self, new PilotingCondition(self, Filters.Salty_Trinitaur), 1));
        modifiers.add(new ForfeitModifier(self, Filters.escortedBy(self), new EscortingCaptiveCondition(self), 5));
        modifiers.add(new PowerModifier(self, Filters.and(Filters.opponents(self), Filters.character, Filters.here(self), abilityLessThanSelf), -1));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, 3));
        return modifiers;
    }
}
