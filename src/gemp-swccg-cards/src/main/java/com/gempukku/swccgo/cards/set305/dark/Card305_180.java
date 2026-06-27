package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractImmediateEffect;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtSameLocationAsCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.PlayCardOptionId;
import com.gempukku.swccgo.common.PlayCardZoneOption;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayCardAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.modifiers.DoesNotAddToPowerOfAnythingPilotedModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.ResetForfeitModifier;
import com.gempukku.swccgo.logic.modifiers.ResetPowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Effect
 * Subtype: Immediate
 * Title: You Are Your Father's Child
 */
public class Card305_180 extends AbstractImmediateEffect {
    public Card305_180() {
        super(Side.DARK, 6, PlayCardZoneOption.ATTACHED, Title.You_Are_Your_Fathers_Child, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("'There is more of me in you than you choose to believe.'");
        setGameText("If Kamjin just won a battle or duel against a Kai of ability < 6, deploy on Kai. During each of your move phases, opponent loses 2 Force. Also, when at same location as Kamjin, Kai is power and forfeit = 0 and does not add power to any starships he pilots.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<PlayCardAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        Filter kaiOfAbilityLessThanSix = Filters.and(Filters.Kai, Filters.abilityLessThan(6));

        // Check condition(s)
        if (TriggerConditions.wonBattleAgainst(game, effectResult, Filters.Kamjin, kaiOfAbilityLessThanSix)
                || TriggerConditions.wonDuelAgainst(game, effectResult, Filters.Kamjin, kaiOfAbilityLessThanSix)) {

            PlayCardAction action = getPlayCardAction(playerId, game, self, self, false, 0, null, null, null, null, null, false, 0, Filters.Kai, null);
            if (action != null) {
                return Collections.singletonList(action);
            }
        }
        return null;
    }

    @Override
    protected Filter getGameTextValidTargetFilterToRemainAttachedToAfterCrossingOver(final SwccgGame game, final PhysicalCard self, PlayCardOptionId playCardOptionId) {
        return Filters.Kai;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(playerId);

        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, Phase.MOVE)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId);
            action.setText("Make opponent lose 2 Force");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, opponent, 2));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();
        String opponent = game.getOpponent(playerId);

        // Check condition(s)
        // Check if reached end of control phase and action was not performed yet.
        if (TriggerConditions.isEndOfYourPhase(game, self, effectResult, Phase.MOVE)
                && GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, Phase.MOVE)) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Make opponent lose 2 Force");
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, opponent, 2));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition atSameLocationAsKamjin = new AtSameLocationAsCondition(self, Filters.Kamjin);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ResetPowerModifier(self, Filters.Kai, atSameLocationAsKamjin, 0));
        modifiers.add(new ResetForfeitModifier(self, Filters.Kai, atSameLocationAsKamjin, 0));
        modifiers.add(new DoesNotAddToPowerOfAnythingPilotedModifier(self, Filters.Kai, atSameLocationAsKamjin));
        return modifiers;
    }
}