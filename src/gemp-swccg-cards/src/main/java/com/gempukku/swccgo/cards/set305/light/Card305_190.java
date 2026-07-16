package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.cards.conditions.OnCondition;
import com.gempukku.swccgo.cards.conditions.PilotingCondition;
import com.gempukku.swccgo.cards.conditions.PresentAtScompLink;
import com.gempukku.swccgo.cards.effects.RevealTopCardOfReserveDeckEffect;
import com.gempukku.swccgo.cards.effects.usage.OncePerTurnEffect;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.OrCondition;
import com.gempukku.swccgo.logic.effects.ChooseEffectOrderEffect;
import com.gempukku.swccgo.logic.modifiers.AddsDestinyToPowerModifier;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.ForceDrainModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.StandardEffect;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Eeno, Jedi Knight
 */
public class Card305_190 extends AbstractAlien {
    public Card305_190() {
        super(Side.LIGHT, 2, 4, 4, 5, 5, "Eeno, Jedi Knight", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Eliwufe’en’ofmo is a lone surviving Jedi Knight from a forgotten age. As a Chiss, he often feels out of place in the wider galaxy.");
        setGameText("[Pilot] 2. Once per turn, if on Quermia (or present with a Scomp link), may reveal the top card of each player's Reserve Deck.");
        addPersona(Persona.EENO);
        addIcons(Icon.ABT, Icon.WARRIOR, Icon.PILOT, Icon.COU);
        setSpecies(Species.CHISS);
        setMatchingStarshipFilter(Filters.Argentum_Baet);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(String playerId, SwccgGame game, PhysicalCard self, int gameTextSourceCardId) {
        List<TopLevelGameTextAction> actions = new LinkedList<>();
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;
        String opponent = game.getOpponent(playerId);

        Condition onQuermiaOrPresentWithScompLink = new OrCondition(
                new OnCondition(self, Title.Quermia),
                new PresentAtScompLink(self));

        if (GameConditions.isOncePerTurn(game, self, playerId, gameTextSourceCardId, gameTextActionId)
                && onQuermiaOrPresentWithScompLink.isFulfilled(game.getGameState(), game.getModifiersQuerying())
                && GameConditions.hasReserveDeck(game, playerId)
                && GameConditions.hasReserveDeck(game, opponent)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, playerId, gameTextSourceCardId, gameTextActionId);
            action.setText("Reveal top card of Reserve Decks");
            action.setActionMsg("Reveal top card of each player's Reserve Deck");

            action.appendUsage(
                    new OncePerTurnEffect(action));

            List<StandardEffect> effects = new LinkedList<>();
            effects.add(
                    new RevealTopCardOfReserveDeckEffect(action, playerId, playerId));
            effects.add(
                    new RevealTopCardOfReserveDeckEffect(action, playerId, opponent));

            action.appendEffect(
                    new ChooseEffectOrderEffect(action, effects));

            actions.add(action);
        }

        return actions;
    }
}
