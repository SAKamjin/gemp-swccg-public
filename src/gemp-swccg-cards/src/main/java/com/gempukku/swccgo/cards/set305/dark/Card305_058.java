package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractImperial;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.SatisfyAllAttritionEffect;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.ForfeitCardFromTableEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromReserveDeckEffect;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Imperial
 * Title: Jeff Fausell
 */
public class Card305_058 extends AbstractImperial {
    public Card305_058() {
        super(Side.DARK, 2, 2, 1, 2, 2, "Jeff Fausell", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLore("A Lt. Commander within the Imperial Scholae Navy. Served as the Sensor Officer aboard the ISN Charybdis. Claimed Komilia Lap'lamiz was the best pilot he'd ever seen.");
        setGameText("Once during each of your control phases, you may use 1 Force to take one Scanning Crew into hand from Reserve Deck; reshuffle. If in a battle with Vincent Brujah may be sacrificed to satisify all attrition.");
        addIcons(Icon.CSP, Icon.ABT);
		addKeywords(Keyword.MALE);
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.JEFF_FAUSELL__UPLOAD_SCANNING_CREW;

        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.CONTROL)
                && GameConditions.canUseForce(game, playerId, 2)
                && GameConditions.canTakeCardsIntoHandFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Take card into hand from Reserve Deck");
            action.setActionMsg("Take a Scanning Crew into hand from Reserve Deck");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerId, 1));
            // Perform result(s)
            action.appendEffect(
                    new TakeCardIntoHandFromReserveDeckEffect(action, playerId, Filters.Scanning_Crew, true));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, final EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.isResolvingBattleDamageAndAttrition(game, effectResult, playerId)
                && GameConditions.canForfeitToSatisfyAttrition(game, playerId, self)
                && GameConditions.isAtLocation(game, self, Filters.sameSiteAs(self, Filters.and(Filters.your(self), Filters.Brujah)))) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Forfeit to satisfy all attrition");
            // Pay cost(s)
            action.appendCost(
                    new ForfeitCardFromTableEffect(action, self));
            // Perform result(s)
            action.appendEffect(
                    new SatisfyAllAttritionEffect(action, playerId));
            return Collections.singletonList(action);
        }
        return null;
    }
}
