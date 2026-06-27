package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.SetWhileInPlayDataEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.WhileInPlayData;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.PlaceCardOutOfPlayFromTableEffect;
import com.gempukku.swccgo.logic.effects.TakeUnattendedFrozenCaptiveIntoCustodyEffect;
import com.gempukku.swccgo.logic.modifiers.CrossOverAttemptTotalModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
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
public class Card305_181_BACK extends AbstractAlien {
    public Card305_181_BACK() {
        super(Side.DARK, 7, 0, 2, 3, 0, "Jloc'itaome'faottas, Kamjin's Prize", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.UR);
        setLore("Kamjin thought that capturing Locita would be the end of his troubles. However, another had greater plans in motion.");
        setGameText("Immediately 'thaw' Locita (Locita is non-frozen, captured, and seized by Kamjin). While this side up, subtracts 6 from attempts to cross Locita over (even while a captive). Place out of play if released or about to leave table.");
        setDoesNotCountTowardDeckLimit(true);
        addPersona(Persona.LOCITA);
        setCharacterPersonaOnlyWhileOnTable(true);
        addIcons(Icon.WARRIOR, Icon.ABT);
        setMayNotBePlacedInReserveDeck(true);
    }

    @Override
    protected List<Modifier> getGameTextWhileInactiveInPlayModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new CrossOverAttemptTotalModifier(self, -6));
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
        if (!GameConditions.cardHasWhileInPlayDataSet(self)
                && TriggerConditions.isTableChanged(game, effectResult)) {
            final PhysicalCard kamjin = Filters.findFirstActive(game, self, Filters.and(Filters.Kamjin, Filters.not(Filters.escorting(self))));
            if (kamjin != null) {

                final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
                action.setSingletonTrigger(true);
                action.setText("'Thaw'");
                action.setActionMsg("'Thaw' " + GameUtils.getCardLink(self));
                // Perform result(s)
                action.appendEffect(
                        new SetWhileInPlayDataEffect(action, self, new WhileInPlayData()));
                action.appendEffect(
                        new TakeUnattendedFrozenCaptiveIntoCustodyEffect(action, kamjin, self, true));
                return Collections.singletonList(action);
            }
        }
        return null;
    }
}
