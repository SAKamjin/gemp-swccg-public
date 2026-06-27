package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.GameState;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.effects.ModifyPowerEffect;
import com.gempukku.swccgo.logic.effects.RefreshPrintedDestinyValuesEffect;
import com.gempukku.swccgo.logic.effects.RetrieveForceEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.modifiers.AddsBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.querying.ModifiersQuerying;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.StandardEffect;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Dag Duh Dug, Scoundrel
 */
public class Card305_152 extends AbstractAlien {
    public Card305_152() {
        super(Side.DARK, 5, 5, 6, 3, 7, "Dag Duh Dug, Scoundrel", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Gambler. Mercenary. Information Broker. Years of being unpaid by Kamjin means Dag has had to become creative in finding ways to ensure he gets paid.");
        setGameText("Adds one battle destiny. If opponent initiates battle at same location, retrieve 1 Force and add its destiny number to Dag's power, or power of Tatorutaimu he is piloting. End of your turn: Use 1 Force to maintain OR Lose 2 Force to place in Used Pile OR Place out of play.");
        addPersona(Persona.DAG);
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR, Icon.MAINTENANCE);
        addKeywords(Keyword.GAMBLER, Keyword.MERCENARY, Keyword.INFORMATION_BROKER);
        setMatchingStarshipFilter(Filters.Tatorutaimu);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsBattleDestinyModifier(self, 1));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(final SwccgGame game, final EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();
        String opponent = game.getOpponent(playerId);

        // Check condition(s)
        if (TriggerConditions.battleInitiatedAt(game, effectResult, opponent, Filters.sameLocation(self))) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Retrieve 1 Force");
            // Perform result(s)
            action.appendEffect(
                    new RetrieveForceEffect(action, playerId, 1) {
                        @Override
                        protected void cardRetrieved(final PhysicalCard retrievedCard) {
                            final GameState gameState = game.getGameState();
                            final ModifiersQuerying modifiersQuerying = game.getModifiersQuerying();

                            action.appendEffect(
                                    new RefreshPrintedDestinyValuesEffect(action, Collections.singletonList(retrievedCard)) {
                                        @Override
                                        protected void refreshedPrintedDestinyValues() {
                                            float destiny = modifiersQuerying.getDestiny(gameState, retrievedCard);
                                            if (Filters.piloting(Filters.Tatorutaimu).accepts(game, self)) {
                                                PhysicalCard tatorutaimu = Filters.findFirstFromAllOnTable(game, Filters.Tatorutaimu);
                                                if (tatorutaimu != null) {
                                                    action.appendEffect(
                                                            new ModifyPowerEffect(action, tatorutaimu, destiny));
                                                }
                                            }
                                            else {
                                                action.appendEffect(
                                                        new ModifyPowerEffect(action, self, destiny));
                                            }
                                        }
                                    }
                            );
                        }
                    });
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected StandardEffect getGameTextMaintenanceMaintainCost(Action action, final String playerId) {
        return new UseForceEffect(action, playerId, 1);
    }

    @Override
    protected StandardEffect getGameTextMaintenanceRecycleCost(Action action, final String playerId) {
        return new LoseForceEffect(action, playerId, 2, true);
    }
}
