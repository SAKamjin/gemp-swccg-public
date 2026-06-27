package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractLostInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.AddBattleDestinyEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardToLocationFromReserveDeckEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromReserveDeckEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Lost
 * Title: The Ghrakhowsk
 */
public class Card305_165 extends AbstractLostInterrupt {
    public Card305_165() {
        super(Side.DARK, 3, Title.The_Ghrakhowsk, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("While neither talk about it, Slyth did swear the Ghrakhowsk to Dag Duh Dug. This 'life debt' is taken very seriously by Slyth despite Dag's continual habit of getting them into bad situations.");
        setGameText("If Dag and Slyth are in a battle together, add two battle destiny. OR Search your Reserve Deck, take Tatorutaimu into hand, and reshuffle. OR If Dag is defending a battle alone, you may 'react' by deploying Slyth there (for free) from your Reserve Deck; reshuffle.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self) {
        List<PlayInterruptAction> actions = new LinkedList<PlayInterruptAction>();

        // Check condition(s)
        if (GameConditions.isDuringBattleWithParticipant(game, Filters.and(Filters.Dag, Filters.canBeTargetedBy(self)))
                && GameConditions.isDuringBattleWithParticipant(game, Filters.and(Filters.Slyth, Filters.canBeTargetedBy(self)))
                && GameConditions.canAddBattleDestinyDraws(game, self)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Add two battle destiny");
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

        GameTextActionId gameTextActionId = GameTextActionId.THE_GHRAKHOWSK__UPLOAD_TATORUTAIMU;

        // Check condition(s)
        if (GameConditions.canTakeCardsIntoHandFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self, gameTextActionId);
            action.setText("Take Tatorutaimu into hand from Reserve Deck");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new TakeCardIntoHandFromReserveDeckEffect(action, playerId, Filters.and(Filters.Tatorutaimu, Filters.canBeTargetedBy(self)), true));
                        }
                    }
            );
            actions.add(action);
        }
        return actions;
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        String opponent = game.getOpponent(playerId);

        GameTextActionId gameTextActionId = GameTextActionId.THE_GHRAKHOWSK__DOWNLOAD_SLYTH_AS_REACT;

        // Check condition(s)
        if (TriggerConditions.battleInitiated(game, effectResult, opponent)
                && GameConditions.canSpot(game, self, Filters.and(Filters.Dag, Filters.defendingBattle, Filters.alone))
                && GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId, true, Persona.SLYTH)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self, gameTextActionId);
            action.setText("Deploy Slyth as 'react' from Reserve Deck");
            // Allow response(s)
            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new DeployCardToLocationFromReserveDeckEffect(action, Filters.Slyth, Filters.battleLocation, true, true, true));
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }
}