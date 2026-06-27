package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.evaluators.CardMatchesEvaluator;
import com.gempukku.swccgo.cards.evaluators.ControlsEvaluator;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.ModifySabaccTotalEffect;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Dag Duh Dug
 */
public class Card305_151 extends AbstractAlien {
    public Card305_151() {
        super(Side.DARK, 1, 3, 2, 3, 6, "Dag Duh Dug", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Gambler. Mercenary. Information Broker. Dag has done it all for Kamjin over the decades. One of these days he even expects to get paid for it.");
        setGameText("Adds 2 to power of anything he pilots (3 if piloting Tatorutaimu). Power +1 for every Danktooine location you control. When playing Sabacc, may add 1 to or subtract 1 from your total.");
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR);
        addKeywords(Keyword.GAMBLER, Keyword.MERCENARY, Keyword.INFORMATION_BROKER);
        addPersona(Persona.DAG);
        setMatchingStarshipFilter(Filters.Tatorutaimu);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, new CardMatchesEvaluator(2, 3, Filters.Tatorutaimu)));
        modifiers.add(new PowerModifier(self, new ControlsEvaluator(self.getOwner(), Filters.Danktooine_location)));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, final EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        List<OptionalGameTextTriggerAction> actions = new LinkedList<OptionalGameTextTriggerAction>();

        // Check condition(s)
        if (TriggerConditions.isCalculatingSabaccTotals(game, effectResult)
                && GameConditions.isPlayingSabacc(game, self)) {

            final OptionalGameTextTriggerAction action1 = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
            action1.setText("Add 1 to Sabacc total");
            // Perform result(s)
            action1.appendEffect(
                    new ModifySabaccTotalEffect(action1, playerId, 1));
            actions.add(action1);

            // Check condition(s)
            float sabaccTotal = game.getModifiersQuerying().getSabaccTotal(game.getGameState(), playerId);
            if (sabaccTotal > 0) {

                final OptionalGameTextTriggerAction action2 = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
                action2.setText("Subtract 1 from Sabacc total");
                // Perform result(s)
                action2.appendEffect(
                        new ModifySabaccTotalEffect(action2, playerId, -1));
                actions.add(action2);
            }
        }
        return actions;
    }
}
