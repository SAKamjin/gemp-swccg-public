package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractLostInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.AddBattleDestinyEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.GameState;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.DrawDestinyEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.choose.ChooseCardOnTableEffect;
import com.gempukku.swccgo.logic.effects.choose.ChooseCardToLoseFromTableEffect;
import com.gempukku.swccgo.logic.modifiers.querying.ModifiersQuerying;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.GuiUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Lost
 * Title: I'll Make Time For This
 */
public class Card305_090 extends AbstractLostInterrupt {
    public Card305_090() {
        super(Side.LIGHT, 5, "I'll Make Time For This", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Mihoshi and her Nihilgenia have trained for years for combat.");
        setGameText("If Mihoshi and a member of the Nihilgenia are in a battle together, add two battle destiny. OR Target an interior Quermia site where opponent has a battle droid. Draw destiny. If destiny > number of battle droids at that site, one of them is lost (opponent's choice).");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();

        // Check condition(s)
        if (GameConditions.isDuringBattleWithParticipant(game, Filters.Mihoshi)
                && GameConditions.isDuringBattleWithParticipant(game, Filters.Nihilgenia)
                && GameConditions.canAddBattleDestinyDraws(game, self)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Add two battle destiny");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new AddBattleDestinyEffect(action, 2));
                        }
                    }
            );
            actions.add(action);
        }

        final String opponent = game.getOpponent(playerId);
        final Filter locationFilter = Filters.and(Filters.interior_Quermia_site, Filters.sameSiteAs(self, Filters.and(Filters.opponents(self), Filters.battle_droid)));

        // Check condition(s)
        if (GameConditions.canSpotLocation(game, locationFilter)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setActionMsg("Target an interior Quermia site");
            // Choose target(s)
            action.appendTargeting(
                    new ChooseCardOnTableEffect(action, playerId, "Choose interior Quermia site", locationFilter) {
                        @Override
                        protected void cardSelected(final PhysicalCard location) {
                            action.addAnimationGroup(location);
                            // Allow response(s)
                            action.allowResponses("Target " + GameUtils.getCardLink(location),
                                    new RespondablePlayCardEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Perform result(s)
                                            action.appendEffect(
                                                    new DrawDestinyEffect(action, playerId) {
                                                        @Override
                                                        protected void destinyDraws(SwccgGame game, List<PhysicalCard> destinyCardDraws, final List<Float> destinyDrawValues, Float totalDestiny) {
                                                            final GameState gameState = game.getGameState();
                                                            final ModifiersQuerying modifiersQuerying = game.getModifiersQuerying();
                                                            if (totalDestiny == null) {
                                                                gameState.sendMessage("Result: No result due to failed destiny draw");
                                                                return;
                                                            }

                                                            gameState.sendMessage("Destiny: " + GuiUtils.formatAsString(totalDestiny));
                                                            Collection<PhysicalCard> battleDroids = Filters.filterActive(game, self,
                                                                    Filters.and(Filters.opponents(self), Filters.battle_droid, Filters.at(location)));
                                                            int numberOfBattleDroids = battleDroids.size();
                                                            gameState.sendMessage("Number of battle droids: " + numberOfBattleDroids);

                                                            if (totalDestiny > numberOfBattleDroids) {
                                                                gameState.sendMessage("Result: Succeeded");
                                                                String playerToChoose = modifiersQuerying.getPlayerToChooseCardTargetAtLocation(gameState, self, location, opponent);
                                                                action.appendEffect(
                                                                        new ChooseCardToLoseFromTableEffect(action, playerToChoose, Filters.in(battleDroids)));
                                                            }
                                                            else {
                                                                gameState.sendMessage("Result: Failed");
                                                            }
                                                        }
                                                    }
                                            );
                                        }
                                    }
                            );
                        }
                    }
            );
            actions.add(action);
        }
        return actions;
    }
}