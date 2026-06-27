package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractUsedInterrupt;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayInterruptAction;
import com.gempukku.swccgo.logic.effects.RespondablePlayCardEffect;
import com.gempukku.swccgo.logic.effects.TargetCardOnTableEffect;
import com.gempukku.swccgo.logic.effects.choose.MoveCardsAwayEffect;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Interrupt
 * Subtype: Used
 * Title: Face In The Crowd
 */
public class Card305_171 extends AbstractUsedInterrupt {
    public Card305_171() {
        super(Side.DARK, 5, Title.Face_In_The_Crowd, Uniqueness.UNRESTRICTED, ExpansionSet.ABT, Rarity.C);
        setLore("During the reign of the Empire non-humanoids learned how to disappear into a crowd whenever a conflict arose. These skills work just as well under the New Republic.");
        setGameText("If opponent just initiated battle at a site where you have an Alien of ability > 2 present, move all of your cards with ability there away (using their landspeed).");
    }

    @Override
    protected List<PlayInterruptAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self) {
        String opponent = game.getOpponent(playerId);

        // Check condition(s)
        if (TriggerConditions.battleInitiatedAt(game, effectResult, opponent, Filters.and(Filters.site,
                Filters.wherePresent(self, Filters.and(Filters.your(self), Filters.alien, Filters.abilityMoreThan(2),
                        Filters.presentInBattle, Filters.canBeTargetedBy(self))), Filters.canBeTargetedBy(self)))
                && GameConditions.canSpotLocation(game, Filters.adjacentSite(game.getGameState().getBattleLocation()))) {

            final PlayInterruptAction action = new PlayInterruptAction(game, self);
            action.setText("Move cards with ability away");
            // Choose target(s)
            action.appendTargeting(
                    new TargetCardOnTableEffect(action, playerId, "Choose an Alien of ability > 2", Filters.and(Filters.your(self), Filters.alien, Filters.abilityMoreThan(2), Filters.presentInBattle)) {
                        @Override
                        protected boolean getUseShortcut() {
                            return true;
                        }
                        @Override
                        protected void cardTargeted(final int targetGroupId1, PhysicalCard targetedRebel) {
                            action.addAnimationGroup(targetedRebel);
                            // Allow response(s)
                            action.allowResponses("Move cards with ability away by targeting " + GameUtils.getCardLink(targetedRebel),
                                    new RespondablePlayCardEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Perform result(s)
                                            action.appendEffect(
                                                    new MoveCardsAwayEffect(action, playerId, Filters.and(Filters.your(self),
                                                            Filters.hasAbility, Filters.participatingInBattle, Filters.canBeTargetedBy(self))));
                                        }
                                    }
                            );
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }
}