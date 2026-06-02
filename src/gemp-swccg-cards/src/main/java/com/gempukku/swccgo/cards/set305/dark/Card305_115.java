package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractDroid;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.effects.choose.PlaceCardOutOfPlayFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Droid
 * Title: VDB2-8PE
 */
public class Card305_115 extends AbstractDroid {
    public Card305_115() {
        super(Side.DARK, 2, 4, 4, 3, "VDB2-8PE", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setArmor(5);
        setLore("B2 battle droids have been deployed to areas of increased security concern. All individuals must subject themselves to random searches by these Vauzem Dominion patrols.");
        setGameText("While with another battle droid, power +2. If with a battle droid on Quermia and you just verified opponent's Reserve Deck, may lose 2 Force to search that Reserve Deck and place one card you find there out of play.");
        addIcons(Icon.ABT, Icon.PRESENCE);
        addKeywords(Keyword.SECURITY_BATTLE_DROID);
        addModelType(ModelType.BATTLE);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, new WithCondition(self, Filters.battle_droid), 2));
        return modifiers;
    }

    @Override
    public List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, final EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(playerId);
        GameTextActionId gameTextActionId = GameTextActionId.VDB28PE__SEARCH_OPPONENT_RESERVE_DECK;

        // Check condition(s)
        if (TriggerConditions.justVerifiedOpponentsReserveDeck(game, effectResult, playerId)
                && GameConditions.isWith(game, self, Filters.battle_droid)
                && GameConditions.isOnSystem(game, self, Title.Quermia)
                && GameConditions.canSearchOpponentsReserveDeck(game, playerId, self, gameTextActionId)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Place a card from Reserve Deck out of play");
            action.setActionMsg("Search opponent's Reserve Deck and place a card found there out of play");
            // Pay cost(s)
            action.appendCost(
                    new LoseForceEffect(action, playerId, 2, true));
            // Perform result(s)
            action.appendEffect(
                    new PlaceCardOutOfPlayFromReserveDeckEffect(action, playerId, opponent, false));
            return Collections.singletonList(action);
        }
        return null;
    }
}
