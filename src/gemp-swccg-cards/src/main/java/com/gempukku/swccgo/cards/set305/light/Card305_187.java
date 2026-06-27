package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AboardCondition;
import com.gempukku.swccgo.cards.effects.PeekAtTopCardOfReserveDeckEffect;
import com.gempukku.swccgo.cards.effects.RevealTopCardOfReserveDeckEffect;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.cards.effects.usage.OncePerTurnEffect;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.AbstractActionProxy;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.actions.TriggerAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.decisions.MultipleChoiceAwaitingDecision;
import com.gempukku.swccgo.logic.decisions.YesNoDecision;
import com.gempukku.swccgo.logic.effects.*;
import com.gempukku.swccgo.logic.effects.choose.ChooseCardOnTableEffect;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.AttritionModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Rebel
 * Title: Vez Hirundo
 */
public class Card305_187 extends AbstractRebel {
    public Card305_187() {
        super(Side.LIGHT, 1, 5, 2, 4, 6, "Vez Hirundo", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Throughout her life, Vez has struggled with her Force sensitivity and the intrusive dreams and feelings that manifest. She's tried to self medicate through spice. Information Broker. Padawan.");
        setGameText("[Pilot] 2. Once during your control phase, predict either a Force drain location or the location of a Battle. If your opponent Force drains at that location or battles at that location during their turn, retrieve 1 Force. If opponent does not Force drain nor battles at the location, lose 1 Force.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT, Icon.WARRIOR);
        addKeywords(Keyword.INFORMATION_BROKER, Keyword.PADAWAN);
        addPersona(Persona.VEZ);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(
            final String playerId,
            final SwccgGame game,
            final PhysicalCard self,
            final int gameTextSourceCardId) {

        final String opponent = game.getOpponent(playerId);
        final GameTextActionId gameTextActionId =
                GameTextActionId.OTHER_CARD_ACTION_1;

        if (GameConditions.isOnceDuringYourPhase(
                game,
                self,
                playerId,
                gameTextSourceCardId,
                gameTextActionId,
                Phase.CONTROL)
                && GameConditions.canSpot(game, self, Filters.location)) {

            final TopLevelGameTextAction action =
                    new TopLevelGameTextAction(
                            self,
                            playerId,
                            gameTextSourceCardId,
                            gameTextActionId);

            action.setText("Predict location");
            action.setActionMsg("Make a prediction");

            action.appendUsage(
                    new OncePerPhaseEffect(action));

            action.appendTargeting(
                    new PlayoutDecisionEffect(action, playerId,
                            new MultipleChoiceAwaitingDecision(
                                    "Choose prediction type",
                                    new String[]{"Force Drain", "Battle"}) {
                        @Override
                        protected void validDecisionMade(final int index, String result) {
                            final boolean predictForceDrain = index == 0;

                            action.appendTargeting(
                                    new ChooseCardOnTableEffect(
                                            action,
                                            playerId,
                                            "Choose location",
                                            Filters.location) {
                                                @Override
                                                protected void cardSelected(final PhysicalCard predictedLocation) {
                                                    final Filter locationFilter =
                                                            Filters.samePermanentCardId(predictedLocation);
                                                    final int selfPermId =
                                                            self.getPermanentCardId();

                                                    action.appendEffect(
                                                            new AddUntilEndOfPlayersNextTurnActionProxyEffect(
                                                                    action,
                                                                    new AbstractActionProxy() {
                                                                        private boolean _predictionSatisfied;

                                                                        @Override
                                                                        public List<TriggerAction> getRequiredAfterTriggers(
                                                                                SwccgGame game,
                                                                                EffectResult effectResult) {

                                                                            final PhysicalCard source =
                                                                                    game.findCardByPermanentId(selfPermId);

                                                                            if (source == null) {
                                                                                return null;
                                                                            }

                                                                            boolean predictionOccurred =
                                                                                    predictForceDrain
                                                                                            ? TriggerConditions.forceDrainInitiatedBy(
                                                                                                    game,
                                                                                                    effectResult,
                                                                                                    opponent,
                                                                                                    locationFilter)
                                                                                            : TriggerConditions.battleInitiatedAt(
                                                                                                    game,
                                                                                                    effectResult,
                                                                                                    opponent,
                                                                                                    locationFilter);

                                                                            if (!_predictionSatisfied && predictionOccurred) {
                                                                                _predictionSatisfied = true;

                                                                                RequiredGameTextTriggerAction action1 =
                                                                                        new RequiredGameTextTriggerAction(
                                                                                                source,
                                                                                                gameTextSourceCardId);

                                                                                action1.setText("Retrieve 1 Force");
                                                                                action1.appendEffect(
                                                                                        new RetrieveForceEffect(
                                                                                                action1,
                                                                                                playerId,
                                                                                                1));

                                                                                return Collections.singletonList(action1);
                                                                            }

                                                                            if (!_predictionSatisfied
                                                                                    && TriggerConditions.isEndOfYourTurn(
                                                                                            game,
                                                                                            effectResult,
                                                                                            opponent)) {

                                                                                RequiredGameTextTriggerAction action1 =
                                                                                        new RequiredGameTextTriggerAction(
                                                                                                source,
                                                                                                gameTextSourceCardId);

                                                                                action1.setText("Lose 1 Force");
                                                                                action1.appendEffect(
                                                                                        new LoseForceEffect(
                                                                                                action1,
                                                                                                playerId,
                                                                                                1));

                                                                                return Collections.singletonList(action1);
                                                                            }

                                                                            return null;
                                                                        }
                                                                    },
                                                                    opponent));
                                                }
                                            });
                        }
                    }));

            return Collections.singletonList(action);
        }

        return null;
    }

}
