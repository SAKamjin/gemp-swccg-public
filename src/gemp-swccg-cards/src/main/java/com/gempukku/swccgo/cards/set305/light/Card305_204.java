package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractUsedInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.CancelBattleEffect;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.MayNotBattleUntilEndOfTurnEffect;
import com.gempukku.swccgo.logic.effects.MayNotMoveOrBattleUntilEndOfTurnEffect;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.results.PlayCardResult;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Used
 * Title: Turel's Soapbox
 */
public class Card305_204 extends AbstractUsedInterrupt {
    public Card305_204() {
        super(Side.LIGHT, 4, "Turel's Soapbox", Uniqueness.UNRESTRICTED, ExpansionSet.ABT, Rarity.C);
        setLore("Many a leader and Councilor knows that when Turel gets on his soapbox there's nothing to do but ride out the meeting.");
        setGameText("If opponent just deployed a leader or Councilor prevent that leader or Councilor from moving or battling this turn.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(
            final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        String opponent = game.getOpponent(playerId);

        if (TriggerConditions.justDeployed(game, effectResult, opponent,
                Filters.and(Filters.character, Filters.or(Filters.leader, Filters.Dark_Councilor)))) {

            final PhysicalCard deployedCard = ((PlayCardResult) effectResult).getPlayedCard();

            final PlayInterruptAction action = new PlayInterruptAction(game, self, CardSubtype.USED);
            action.setText("Prevent " + GameUtils.getFullName(deployedCard) + " from moving or battling");
            action.addAnimationGroup(deployedCard);

            action.allowResponses(
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            action.appendEffect(
                                    new MayNotMoveOrBattleUntilEndOfTurnEffect(action, deployedCard));
                        }
                    }
            );

            return Collections.singletonList(action);
        }

        return null;
    }
}