package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.SetForRemainderOfGameDataEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.SpotOverride;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.DeployAsCaptiveOption;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.ForRemainderOfGameData;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayCardAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.FlipCardEffect;
import com.gempukku.swccgo.logic.effects.PlaceCardOutOfPlayFromOffTableEffect;
import com.gempukku.swccgo.logic.effects.PlaceCardOutOfPlayFromTableEffect;
import com.gempukku.swccgo.logic.effects.SendMessageEffect;
import com.gempukku.swccgo.logic.effects.ShowCardOnScreenEffect;
import com.gempukku.swccgo.logic.effects.StackActionEffect;
import com.gempukku.swccgo.logic.modifiers.MayNotBeFlippedModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotBeTransferredModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.Effect;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.PassthruEffect;
import com.gempukku.swccgo.logic.timing.results.AboutToLeaveTableResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Jloc'itaome'faottas, Kamjin's Prize
 */
public class Card305_181 extends AbstractAlien {
    public Card305_181() {
        super(Side.DARK, 0, 0, 0, 0, 0, "Jloc'itaome'faottas, Kamjin's Prize", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.UR);
        setFrontOfDoubleSidedCard(true);
        setGameText("Jloc'itaome'faottas, Kamjin's Prize is a Dark Side card and does not count toward your deck limit. Reveal to opponent when deploying your Starting Effect. Deploys to Seraph: Monolith Throne Room only at start of game as a frozen captive if Hostile Takeover on table and Complications is suspended; otherwise place out of play. For remainder of game, may not be placed in Reserve Deck. Jloc'itaome'faottas, Kamjin's Prize is a persona of Locita only while on table. While this side up, Hostile Takeover may not flip. May not be escorted. Flip this card if Kamjin present and not escorting a captive. Place out of play if released or about to leave table.");
        setDoesNotCountTowardDeckLimit(true);
        addPersona(Persona.LOCITA);
        setCharacterPersonaOnlyWhileOnTable(true);
        addIcons(Icon.ABT);
        setMayNotBePlacedInReserveDeck(true);
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredOutsideOfDeckBeforeTriggers(SwccgGame game, Effect effect, PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();

        // Check condition(s)
        if (TriggerConditions.isPlayingCard(game, effect, playerId, Filters.Starting_Effect)) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Reveal");
            action.skipInitialMessageAndAnimation();
            action.setActionMsg(null);
            // Perform result(s)
            action.appendEffect(
                    new SetForRemainderOfGameDataEffect(action, self, new ForRemainderOfGameData()));
            action.appendEffect(
                    new SendMessageEffect(action, playerId + " reveals " + GameUtils.getCardLink(self)));
            action.appendEffect(
                    new ShowCardOnScreenEffect(action, self));
            return Collections.singletonList(action);
        }

        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredOutsideOfDeckAfterTriggers(SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        final String playerId = self.getOwner();

        // Check condition(s)
        if (GameConditions.cardHasAnyForRemainderOfGameDataSet(self)) {
            if (TriggerConditions.isStartingLocationsAndObjectivesCompletedStep(game, effectResult)) {

                final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
                action.setText("Place out of play");
                action.setActionMsg("Place " + GameUtils.getCardLink(self) + " out of play");
                // Perform result(s)
                action.appendEffect(
                        new PlaceCardOutOfPlayFromOffTableEffect(action, self));
                return Collections.singletonList(action);
            }

            if (TriggerConditions.isTableChanged(game, effectResult)) {
                final PhysicalCard monolithThroneRoom = Filters.findFirstFromTopLocationsOnTable(game, Filters.Monolith_Throne_Room);
                if (monolithThroneRoom != null
                        && GameConditions.canSpot(game, self, Filters.Hostile_Takeover)
                        && GameConditions.canSpot(game, self, SpotOverride.INCLUDE_SUSPENDED, Filters.and(Filters.Complications, Filters.suspended))) {

                    final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
                    action.setText("Deploy");
                    // Perform result(s)
                    action.appendEffect(
                            new PassthruEffect(action) {
                                @Override
                                protected void doPlayEffect(SwccgGame game) {
                                    PlayCardAction deployAction = self.getBlueprint().getPlayCardAction(playerId, game, self, self, true, 0, null, null,
                                            DeployAsCaptiveOption.deployAsUnattendedFrozenCaptive(), null, null, false, 0, Filters.sameLocationId(monolithThroneRoom), null);
                                    action.appendEffect(
                                            new StackActionEffect(action, deployAction));
                                }
                            });
                    return Collections.singletonList(action);
                }
            }
        }

        return null;
    }

    @Override
    protected List<Modifier> getGameTextWhileInactiveInPlayModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayNotBeFlippedModifier(self, Filters.Hostile_Takeover));
        modifiers.add(new MayNotBeTransferredModifier(self));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggersAlwaysWhenInPlay(SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.isAboutToLeaveTable(game, effectResult, self)
                && !TriggerConditions.isAboutToBePlacedOutOfPlayFromTable(game, effectResult, self)) {
            final AboutToLeaveTableResult result = (AboutToLeaveTableResult) effectResult;

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Place out of play");
            action.setActionMsg("Place " + GameUtils.getCardLink(self) + " out of play");
            // Perform result(s)
            action.appendEffect(
                    new PassthruEffect(action) {
                        @Override
                        protected void doPlayEffect(SwccgGame game) {
                            result.getPreventableCardEffect().preventEffectOnCard(self);
                            for (PhysicalCard attachedCards : game.getGameState().getAllAttachedRecursively(self)) {
                                result.getPreventableCardEffect().preventEffectOnCard(attachedCards);
                            }
                        }
                    });
            action.appendEffect(
                    new PlaceCardOutOfPlayFromTableEffect(action, self));
            return Collections.singletonList(action);
        }

        // Check condition(s)
        if (TriggerConditions.released(game, effectResult, self)) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Place out of play");
            action.setActionMsg("Place " + GameUtils.getCardLink(self) + " out of play");
            // Perform result(s)
            action.appendEffect(
                    new PlaceCardOutOfPlayFromTableEffect(action, self));
            return Collections.singletonList(action);
        }

        // Check condition(s)
        if (TriggerConditions.isTableChanged(game, effectResult)
                && GameConditions.canSpot(game, self, Filters.and(Filters.Kamjin, Filters.present(self), Filters.not(Filters.escort)))) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setSingletonTrigger(true);
            action.setText("Flip");
            action.setActionMsg(null);
            // Perform result(s)
            action.appendEffect(
                    new FlipCardEffect(action, self));
            return Collections.singletonList(action);
        }

        return null;
    }
}
