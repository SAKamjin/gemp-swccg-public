package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractRepublic;
import com.gempukku.swccgo.cards.conditions.AtSameSiteAsCondition;
import com.gempukku.swccgo.cards.conditions.OnCondition;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.AgendaModifier;
import com.gempukku.swccgo.logic.modifiers.ForceDrainModifier;
import com.gempukku.swccgo.logic.modifiers.ForceGenerationModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Republic
 * Title: Councillor of Uur, Edgar Drachen
 */
public class Card305_054 extends AbstractRepublic {
    public Card305_054() {
        super(Side.LIGHT, 3, 6, 5, 6, 7, "Councillor of Uur, Edgar Drachen", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.PM);
        setPolitics(2);
        setLore("As a Councillor of Uur, Edgar serves as a leader within the clan. Specifically assisting Mihoshi Keibatsu with the Quermia campaign to ensure peace and diplomatic freedom reign.");
        setGameText("Agenda: order. While at same site as Mihoshi, your Force generation is +2 here. While on Quermia, subtracts one from opponent's Force drains at adjacent sites, and your other [COU] characters present are power +1.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT, Icon.WARRIOR);
        addPersona(Persona.EDGAR);
        addKeyword(Keyword.LEADER);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AgendaModifier(self, Agenda.ORDER));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        String playerId = self.getOwner();
        String opponent = game.getOpponent(playerId);
        Condition onNaboo = new OnCondition(self, Title.Quermia);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ForceGenerationModifier(self, Filters.here(self), new AtSameSiteAsCondition(self, Filters.Mihoshi), 2, playerId));
        modifiers.add(new ForceDrainModifier(self, Filters.adjacentSite(self), onNaboo, -1, opponent));
        modifiers.add(new PowerModifier(self, Filters.and(Filters.your(self), Filters.other(self), Filters.COU_character,
                Filters.character, Filters.present(self)), onNaboo, 1));
        return modifiers;
    }
}
