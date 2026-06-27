package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.AbstractImperial;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.RevealOpponentsHandEffect;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.decisions.CardTitleAwaitingDecision;
import com.gempukku.swccgo.logic.decisions.MultipleChoiceAwaitingDecision;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.effects.PlayoutDecisionEffect;
import com.gempukku.swccgo.logic.effects.PutCardsFromHandOnUsedPileEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;


import java.util.Collection;
import java.util.Collections;

import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Alethia Archenksova
 */
public class Card305_186 extends AbstractAlien {
    public Card305_186() {
        super(Side.DARK, 1, 5, 3, 4, 6, "Alethia Archenksova", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("");
        setGameText("Once during your control phase, may use 3 Force to name a card. Reveal opponent's hand. If named card is present in opponent's hand opponent must place each copy in Used Pile or lose 2 Force per copy of the named card.");
        addPersonas(Persona.ALETHIA);
		addIcons(Icon.WARRIOR);
        addKeywords(Keyword.LEADER, Keyword.SPY, Keyword.ISB_AGENT);
    }
    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        final String opponent = game.getOpponent(playerId);

        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, Phase.CONTROL)
                && GameConditions.hasHand(game, opponent)
                && GameConditions.canUseForce(game, playerId, 3)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId);
            action.setText("Name a card");

            action.appendUsage(
                    new OncePerPhaseEffect(action));

            action.appendCost(
                    new UseForceEffect(action, playerId, 3));

            action.appendTargeting(
                    new PlayoutDecisionEffect(action, playerId,
                            new CardTitleAwaitingDecision(game, "Choose a card title", null) {
                                @Override
                                protected void cardTitleChosen(final String cardTitle) {
                                    action.appendEffect(
                                            new RevealOpponentsHandEffect(action, playerId) {
                                                @Override
                                                protected void cardsRevealed(final List<PhysicalCard> revealedCards) {
                                                    final Collection<PhysicalCard> matchingCards =
                                                            Filters.filter(revealedCards, game, Filters.title(cardTitle));

                                                    if (!matchingCards.isEmpty()) {
                                                        final int numCopies = matchingCards.size();

                                                        if (game.getModifiersQuerying().mayNotRemoveCardsFromOpponentsHand(game.getGameState(), self, playerId)) {
                                                            game.getGameState().sendMessage(opponent + " may not place cards from hand in Used Pile");
                                                            action.appendEffect(
                                                                    new LoseForceEffect(action, opponent, 2 * numCopies));
                                                        }
                                                        else {
                                                            action.appendEffect(
                                                                    new PlayoutDecisionEffect(action, opponent,
                                                                            new MultipleChoiceAwaitingDecision("Choose effect", new String[]{
                                                                                    "Place each copy in Used Pile",
                                                                                    "Lose " + (2 * numCopies) + " Force"
                                                                            }) {
                                                                                @Override
                                                                                protected void validDecisionMade(int index, String result) {
                                                                                    if (index == 0) {
                                                                                        action.appendEffect(
                                                                                                new PutCardsFromHandOnUsedPileEffect(action, opponent, opponent, matchingCards, false));
                                                                                    }
                                                                                    else {
                                                                                        action.appendEffect(
                                                                                                new LoseForceEffect(action, opponent, 2 * numCopies));
                                                                                    }
                                                                                }
                                                                            }));
                                                        }
                                                    }
                                                    else {
                                                        game.getGameState().sendMessage("No card with title of " + cardTitle + " found");
                                                    }
                                                }
                                            });
                                }
                            }));

            return Collections.singletonList(action);
        }
        return null;
    }

}
