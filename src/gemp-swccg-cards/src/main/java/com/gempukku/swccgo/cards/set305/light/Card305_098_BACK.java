package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractObjective;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.SpotOverride;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.CancelDestinyEffect;
import com.gempukku.swccgo.logic.effects.FlipCardEffect;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.modifiers.CancelImmunityToAttritionModifier;
import com.gempukku.swccgo.logic.modifiers.LimitForceLossFromForceDrainModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: Theed Palace
 * Type: Objective
 * Title: A Senate Motion / Speaker We Have The Floor
 */
public class Card305_098_BACK extends AbstractObjective {
    public Card305_098_BACK() {
        super(Side.LIGHT, 7, Title.Speaker_We_Have_The_Floor, ExpansionSet.ABT, Rarity.U);
        setGameText("While this side up, you lose no more than 2 Force to any Force drain. Your [COU] starships are power +2 and immunity to attrition of [Vauzem Dominion] starships is canceled. If opponent just drew a battle destiny greater than three, you may use 1 Force to cancel that battle destiny. During your control phase, opponent loses 2 Force for each battleground site you control with Mihoshi or Jon Silvon. Flip this card if opponent controls Senate Council Chambers.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, PhysicalCard self) {
        String playerId = self.getOwner();

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new LimitForceLossFromForceDrainModifier(self, 2, playerId));
        modifiers.add(new PowerModifier(self, Filters.and(Filters.your(self), Icon.COU, Filters.starship), 2));
        modifiers.add(new CancelImmunityToAttritionModifier(self, Filters.and(Icon.VAUZEM, Filters.starship)));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, final EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(playerId);

        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_2;

        // Check condition(s)
        if (TriggerConditions.isBattleDestinyJustDrawnBy(game, effectResult, opponent)
                && GameConditions.isDestinyValueGreaterThan(game, 3)
                && GameConditions.canCancelDestiny(game, playerId)
                && GameConditions.canUseForce(game, playerId, 1)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Cancel battle destiny");
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerId, 1));
            // Perform result(s)
            action.appendEffect(
                    new CancelDestinyEffect(action));
            return Collections.singletonList(action);
        }

        return null;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(playerId);

        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_3;

        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.CONTROL)) {
            int amountToLose = 2 * Filters.countTopLocationsOnTable(game, Filters.and(Filters.battleground_site, Filters.controlsWith(playerId, self, Filters.or(Filters.Mihoshi, Filters.JONSILVON))));
            if (amountToLose > 0) {

                final TopLevelGameTextAction action = new TopLevelGameTextAction(self, playerId, gameTextSourceCardId, gameTextActionId);
                action.setText("Make " + opponent + " lose " + amountToLose + " Force");
                // Update usage limit(s)
                action.appendUsage(
                        new OncePerPhaseEffect(action));
                // Perform result(s)
                action.appendEffect(
                        new LoseForceEffect(action, opponent, amountToLose));
                return Collections.singletonList(action);
            }
        }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        List<RequiredGameTextTriggerAction> actions = new LinkedList<RequiredGameTextTriggerAction>();
        String playerId = self.getOwner();
        String opponent = game.getOpponent(playerId);

        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_3;

        // Check condition(s)
        if (TriggerConditions.isEndOfYourPhase(game, effectResult, Phase.CONTROL, playerId)
                && GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.CONTROL)) {
            int amountToLose = 2 * Filters.countTopLocationsOnTable(game, Filters.and(Filters.battleground_site, Filters.controlsWith(playerId, self, Filters.or(Filters.Mihoshi, Filters.JONSILVON))));
            if (amountToLose > 0) {

                final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
                action.setText("Make " + opponent + " lose " + amountToLose + " Force");
                // Update usage limit(s)
                action.appendUsage(
                        new OncePerPhaseEffect(action));
                // Perform result(s)
                action.appendEffect(
                        new LoseForceEffect(action, opponent, amountToLose));
                actions.add(action);
            }
        }

        // Check condition(s)
        if (TriggerConditions.isTableChanged(game, effectResult)
                && GameConditions.canBeFlipped(game, self)
                && GameConditions.controls(game, opponent, SpotOverride.INCLUDE_EXCLUDED_FROM_BATTLE, Filters.Senate_Council_Chambers)) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setSingletonTrigger(true);
            action.setText("Flip");
            action.setActionMsg(null);
            // Perform result(s)
            action.appendEffect(
                    new FlipCardEffect(action, self));
            actions.add(action);
        }
        return actions;
    }
}