package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractUsedInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.RelocateFromLostPileToOreChuteAirlock;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromReserveDeckEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.results.LostFromTableResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Used
 * Title: Down The Ore Chute
 */
public class Card305_172 extends AbstractUsedInterrupt {
    public Card305_172() {
        super(Side.DARK, 6, "Down The Ore Chute", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Blaster fights are common place on Danktooine. Gangsters have mapped out the asteroid and created short cuts to the various airlocks if they need to flee the station.");
        setGameText("If you just lost a character during a battle or duel at a Danktooine site, use 2 Force to relocate that character to Ore Chute Airlock instead of your Lost Pile. OR Search your Reserve Deck, take one Ore Chute Airlock into hand and reshuffle.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        // Check condition(s)
        if (TriggerConditions.justLost(game, effectResult, playerId, Filters.character)
                && (GameConditions.isDuringBattleAt(game, Filters.Danktooine_site) || GameConditions.isDuringDuelAt(game, Filters.Danktooine_site))
                && GameConditions.canUseForceToPlayInterrupt(game, playerId, self, 2)
                && GameConditions.canSpot(game, self, Filters.Ore_Chute_Airlock)) {
            final PhysicalCard justLostCard = ((LostFromTableResult) effectResult).getCard();

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Relocate " + GameUtils.getFullName(justLostCard) + " to Ore Chute Airlock");
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerId, 2));
            // Allow response(s)
            action.allowResponses("Relocate " + GameUtils.getCardLink(justLostCard) + " to Ore Chute Airlock",
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new RelocateFromLostPileToOreChuteAirlock(action, justLostCard));
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<PlayInterruptAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self) {
        GameTextActionId gameTextActionId = GameTextActionId.DOWN_THE_ORE_CHUTE__UPLOAD_ORE_CHUTE_AIRLOCK;

        // Check condition(s)
        if (GameConditions.canTakeCardsIntoHandFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self, gameTextActionId);
            action.setText("Take card into hand from Reserve Deck");
            // Allow response(s)
            action.allowResponses("Take a Ore Chute Airlock into hand from Reserve Deck",
                    new RespondablePlayCardEffect(action) {
                        @Override
                        protected void performActionResults(Action targetingAction) {
                            // Perform result(s)
                            action.appendEffect(
                                    new TakeCardIntoHandFromReserveDeckEffect(action, playerId, Filters.Ore_Chute_Airlock, true));
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }
}