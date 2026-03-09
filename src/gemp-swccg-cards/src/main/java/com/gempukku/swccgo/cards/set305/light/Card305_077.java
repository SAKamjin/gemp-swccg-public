package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractPermanentAboard;
import com.gempukku.swccgo.cards.AbstractPermanentPilot;
import com.gempukku.swccgo.cards.AbstractStarfighter;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.GameState;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.BlowAwayEffect;
import com.gempukku.swccgo.logic.effects.DrawDestinyEffect;
import com.gempukku.swccgo.logic.effects.PlaceCardInUsedPileFromTableEffect;
import com.gempukku.swccgo.logic.timing.GuiUtils;

import java.util.Collections;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: Sapphire Fighter
 */
public class Card305_077 extends AbstractStarfighter {
    public Card305_077() {
        super(Side.LIGHT, 3, 2, 3, null, 5, 4, 3, Title.Sapphire_Fighter, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Flown by an unknown pilot who volunteered to join the assault on the Droid Control ship above Quermia. This brave pilot was the deciding factor between victory and defeat.");
        setGameText("Permanent pilot provides ability of 3. During your control phase, if landed aboard Echo of Vauzem, may draw one destiny. If destiny > 6, place Sapphire Fighter in your Used Pile; Echo of Vauzem 'blown away.'");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT, Icon.NAV_COMPUTER);
        addKeywords(Keyword.SAPPHIRE_SQUADRON);
        addModelType(ModelType.A_WING);
    }

    @Override
    protected List<? extends AbstractPermanentAboard> getGameTextPermanentsAboard() {
        return Collections.singletonList(new AbstractPermanentPilot(3) {});
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActionsEvenIfUnpiloted(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, Phase.CONTROL)
                && GameConditions.canDrawDestiny(game, playerId)
                && GameConditions.isLandedAboard(game, self, Persona.ECHO_OF_VAUZEM)) {
            final PhysicalCard flagship = Filters.findFirstActive(game, self, Filters.ECHO_OF_VAUZEM);
            if (flagship != null) {

                final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId);
                action.setText("Attempt to 'blow away' " + GameUtils.getFullName(flagship));
                action.setActionMsg("Attempt to 'blow away' " + GameUtils.getCardLink(flagship));
                // Update usage limit(s)
                action.appendUsage(
                        new OncePerPhaseEffect(action));
                // Perform result(s)
                action.appendEffect(
                        new DrawDestinyEffect(action, playerId, 1) {
                            @Override
                            protected void destinyDraws(SwccgGame game, List<PhysicalCard> destinyCardDraws, List<Float> destinyDrawValues, Float totalDestiny) {
                                GameState gameState = game.getGameState();
                                if (totalDestiny == null) {
                                    gameState.sendMessage("Result: No result due to failed destiny draw");
                                    return;
                                }
                                gameState.sendMessage("Destiny: " + GuiUtils.formatAsString(totalDestiny));
                                float attemptTotal = game.getModifiersQuerying().getBlowAwayBlockadeFlagshipAttemptTotal(gameState, totalDestiny);
                                gameState.sendMessage("Attempt total: " + GuiUtils.formatAsString(attemptTotal));

                                if (attemptTotal > 6) {
                                    gameState.sendMessage("Result: Succeeded");
                                    action.appendEffect(
                                            new PlaceCardInUsedPileFromTableEffect(action, self));
                                    action.appendEffect(
                                            new BlowAwayEffect(action, flagship));
                                }
                                else {
                                    gameState.sendMessage("Result: Failed");
                                }
                            }
                        });
                return Collections.singletonList(action);
            }
        }
        return null;
    }
}
