package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractEpicEventDeployable;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.EscortingCaptiveCondition;
import com.gempukku.swccgo.cards.conditions.PlayersTurnCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.cards.evaluators.InPlayDataAsFloatEvaluator;
import com.gempukku.swccgo.cards.evaluators.SubtractEvaluator;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.PlayCardOptionId;
import com.gempukku.swccgo.common.PlayCardZoneOption;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.TargetingReason;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.WhileInPlayData;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.AndCondition;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.decisions.DecisionResultInvalidException;
import com.gempukku.swccgo.logic.decisions.IntegerAwaitingDecision;
import com.gempukku.swccgo.logic.effects.CaptureCharacterOnTableEffect;
import com.gempukku.swccgo.logic.effects.PlayoutDecisionEffect;
import com.gempukku.swccgo.logic.effects.RespondableEffect;
import com.gempukku.swccgo.logic.effects.RestoreCardToNormalEffect;
import com.gempukku.swccgo.logic.effects.ShuffleReserveDeckEffect;
import com.gempukku.swccgo.logic.effects.TargetCardOnTableEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.EachBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.ForceDrainModifier;
import com.gempukku.swccgo.logic.modifiers.MayUseOpponentsForceModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.Effect;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.results.AboutToForfeitCardFromTableResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Epic Event
 * Title: I Know He's Here
 */
public class Card305_126 extends AbstractEpicEventDeployable {
    public Card305_126() {
        super(Side.DARK, PlayCardZoneOption.ATTACHED, Title.I_Know_Hes_Here, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setGameText("Deploy on She Will Bring Him To Me. Once during each of your deploy phases may deploy a Plagueis Probe Droid from Reserve Deck; reshuffle. If Mihoshi forfeited at same site as Selika, she is captured instead of lost. While Selika escorting Mihoshi, opponent's battle destiny draws are -1 and during your turn you may use up to 3 Force in opponent's Force Pile. While Selika escorting Mihoshi at a non-Quermia battleground site, opponent's Force drains at Quermia sites are -1 and at the end of every turn, may reshuffle opponent's Reserve Deck.");
        addIcons(Icon.ABT);
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.She_Will_Bring_Him_To_Me;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.I_KNOW_HES_HERE__DOWNLOAD_PLAGUEIS_PROBE_DROID;

        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.DEPLOY)
                && GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Deploy a Plagueis Probe Droid from Reserve Deck");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new DeployCardFromReserveDeckEffect(action, Filters.Plagueis_Probe_Droid, true));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        List<RequiredGameTextTriggerAction> actions = new LinkedList<RequiredGameTextTriggerAction>();
        String playerId = self.getOwner();

        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;
        TargetingReason targetingReason = TargetingReason.TO_BE_CAPTURED;
        Filter mihoshiFilter = Filters.and(Filters.Mihoshi, Filters.canBeTargetedBy(self, targetingReason), Filters.at(Filters.sameSiteAs(self, Filters.Selika)));

        // Check condition(s)
        if (TriggerConditions.isAboutToBeForfeited(game, effectResult, mihoshiFilter)) {
            final AboutToForfeitCardFromTableResult result = (AboutToForfeitCardFromTableResult) effectResult;
            final PhysicalCard cardToBeForfeited = result.getCardToBeForfeited();

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setPerformingPlayer(playerId);
            action.setText("Capture " + GameUtils.getFullName(cardToBeForfeited));
            // Choose target(s)
            action.appendTargeting(
                    new TargetCardOnTableEffect(action, playerId, "Choose Mihoshi", targetingReason, cardToBeForfeited) {
                        @Override
                        protected void cardTargeted(final int targetGroupId, PhysicalCard targetedCard) {
                            action.addAnimationGroup(targetedCard);
                            // Allow response(s)
                            action.allowResponses("Capture " + GameUtils.getCardLink(targetedCard),
                                    new RespondableEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Get the targeted card(s) from the action using the targetGroupId.
                                            // This needs to be done in case the target(s) were changed during the responses.
                                            PhysicalCard finalTarget = action.getPrimaryTargetCard(targetGroupId);

                                            // Perform result(s)
                                            result.getForfeitCardEffect().preventEffectOnCard(finalTarget);
                                            action.appendEffect(
                                                    new RestoreCardToNormalEffect(action, finalTarget));
                                            action.appendEffect(
                                                    new CaptureCharacterOnTableEffect(action, finalTarget));
                                        }
                                    }
                            );
                        }
                        @Override
                        protected boolean getUseShortcut() {
                            return true;
                        }
                    }
            );
            actions.add(action);
        }
        // Check condition(s)
        if (TriggerConditions.isStartOfEachTurn(game, effectResult)) {
            self.setWhileInPlayData(null);
        }
        return actions;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        String playerId = self.getOwner();
        String opponent = game.getOpponent(playerId);
        Condition selikaEscortingMihoshi = new EscortingCaptiveCondition(self, Filters.Selika, Filters.Mihoshi);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new EachBattleDestinyModifier(self, selikaEscortingMihoshi, -1, opponent));
        modifiers.add(new MayUseOpponentsForceModifier(self, new AndCondition(new PlayersTurnCondition(playerId), selikaEscortingMihoshi),
                new SubtractEvaluator(3, new InPlayDataAsFloatEvaluator(self)), playerId));
        modifiers.add(new ForceDrainModifier(self, Filters.Quermia_site, new EscortingCaptiveCondition(self, Filters.and(Filters.Selika,
                Filters.at(Filters.and(Filters.non_Quermia_location, Filters.battleground_site))), Filters.Mihoshi), -1, opponent));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalBeforeTriggers(final String playerId, SwccgGame game, final Effect effect, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_2;

        // Check condition(s)
        if (TriggerConditions.isUsingForce(game, effect, playerId)
                && GameConditions.isDuringYourTurn(game, self)
                && GameConditions.canSpot(game, self, Filters.and(Filters.Selika, Filters.escorting(Filters.Mihoshi))))  {
            final UseForceEffect useForceEffect = (UseForceEffect) effect;
            final int maxForceToUseViaCard = game.getModifiersQuerying().getMaxOpponentsForceToUseViaCard(game.getGameState(), playerId, self, useForceEffect.getAmountForOpponentToUse(), 0);
            if (maxForceToUseViaCard > 0) {
                final int maxForceToUse = Math.min(maxForceToUseViaCard, useForceEffect.getTotalAmountOfForceToUse());

                final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
                action.setRepeatableTrigger(true);
                action.setText("Use opponent's Force");
                // Perform result(s)
                action.appendEffect(
                        new PlayoutDecisionEffect(action, playerId,
                                new IntegerAwaitingDecision("Choose amount of opponent's Force to use", 1, maxForceToUse, maxForceToUse) {
                                    @Override
                                    public void decisionMade(int result) throws DecisionResultInvalidException {
                                        final int validatedResult = Math.min(maxForceToUse, result);
                                        useForceEffect.setAmountForOpponentToUse(useForceEffect.getAmountForOpponentToUse() + validatedResult);
                                        Float forceUsed = self.getWhileInPlayData() != null ? self.getWhileInPlayData().getFloatValue() : null;
                                        if (forceUsed == null)
                                            forceUsed = (float) validatedResult;
                                        else
                                            forceUsed += validatedResult;
                                        self.setWhileInPlayData(new WhileInPlayData(forceUsed));
                                    }
                                }
                        )
                );
                return Collections.singletonList(action);
            }
        }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredBeforeTriggers(SwccgGame game, Effect effect, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_2;
        String playerId = self.getOwner();

        // Check condition(s)
        if (TriggerConditions.isUsingForce(game, effect, playerId)
                && GameConditions.isDuringYourTurn(game, self)
                && GameConditions.canSpot(game, self, Filters.and(Filters.Selika, Filters.escorting(Filters.Mihoshi)))) {
            final UseForceEffect useForceEffect = (UseForceEffect) effect;
            int minOpponentForceToUse = Math.max(0, useForceEffect.getTotalAmountOfForceToUse() - game.getGameState().getForcePile(playerId).size());
            if (minOpponentForceToUse > 0) {
                final int maxForceToUseViaCard = game.getModifiersQuerying().getMaxOpponentsForceToUseViaCard(game.getGameState(), playerId, self, useForceEffect.getAmountForOpponentToUse(), minOpponentForceToUse);
                if (maxForceToUseViaCard > 0) {
                    final int maxForceToUse = Math.min(maxForceToUseViaCard, useForceEffect.getTotalAmountOfForceToUse());

                    final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
                    action.setRepeatableTrigger(true);
                    action.setText("Use opponent's Force");
                    // Perform result(s)
                    action.appendEffect(
                            new PlayoutDecisionEffect(action, playerId,
                                    new IntegerAwaitingDecision("Choose amount of opponent's Force to use", 1, maxForceToUse, maxForceToUse) {
                                        @Override
                                        public void decisionMade(int result) throws DecisionResultInvalidException {
                                            final int validatedResult = Math.min(maxForceToUse, result);
                                            useForceEffect.setAmountForOpponentToUse(useForceEffect.getAmountForOpponentToUse() + validatedResult);
                                            Float forceUsed = self.getWhileInPlayData() != null ? self.getWhileInPlayData().getFloatValue() : null;
                                            if (forceUsed == null)
                                                forceUsed = (float) validatedResult;
                                            else
                                                forceUsed += validatedResult;
                                            self.setWhileInPlayData(new WhileInPlayData(forceUsed));
                                        }
                                    }
                            )
                    );
                    return Collections.singletonList(action);
                }
            }
        }
        return null;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, final SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(playerId);
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_3;

        // Check condition(s)
        if (TriggerConditions.isEndOfEachTurn(game, effectResult)
                && GameConditions.hasReserveDeck(game, opponent)
                && GameConditions.canSpot(game, self, Filters.and(Filters.Selika, Filters.escorting(Filters.Mihoshi),
                Filters.at(Filters.and(Filters.non_Quermia_location, Filters.battleground_site))))) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Shuffle opponent's Reserve Deck");
            // Perform result(s)
            action.appendEffect(
                    new ShuffleReserveDeckEffect(action, opponent));
            return Collections.singletonList(action);
        }
        return null;
    }
}