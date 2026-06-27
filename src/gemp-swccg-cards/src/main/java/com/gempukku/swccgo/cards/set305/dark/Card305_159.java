package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractImmediateEffect;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.SetWhileInPlayDataEffect;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.PlayCardZoneOption;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.WhileInPlayData;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayCardAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.results.LostFromTableResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Effect
 * Subtype: Immediate
 * Title: Unfair Advantage
 */
public class Card305_159 extends AbstractImmediateEffect {
    public Card305_159() {
        super(Side.DARK, 4, PlayCardZoneOption.ATTACHED, "Unfair Advantage", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("A Sith fights to win. They will use any advantage they can find and there is no greater honor than victory.");
        setGameText("If an opponent's character of ability > 3 was just lost in a battle or duel you won, deploy on one of your warriors involved. Warrior is power +2. During each of opponent's move phases, opponent loses 1 Force (2 if character was a Jedi). (Immune to Control.)");
        addIcons(Icon.ABT);
        addImmuneToCardTitle(Title.Control);
    }

    @Override
    protected List<PlayCardAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        Filter opponentsCharacterFilter = Filters.and(Filters.opponents(self), Filters.character, Filters.abilityMoreThan(3));

        // Check condition(s)
        if (TriggerConditions.justLost(game, effectResult, opponentsCharacterFilter)
                && (GameConditions.isDuringBattleWonBy(game, playerId)
                || GameConditions.isDuringDuelWonBy(game, playerId))) {
            Filter filter = Filters.none;
            if (GameConditions.isDuringDuelWonBy(game, playerId)) {
                filter = Filters.or(filter, Filters.participatingInDuel);
            }
            if (GameConditions.isDuringBattleWonBy(game, playerId)) {
                filter = Filters.or(filter, Filters.participatingInBattle);
            }
            PhysicalCard cardLost = ((LostFromTableResult) effectResult).getCard();

            PlayCardAction action = getPlayCardAction(playerId, game, self, self, false, 0, null, null, null, null, null, false, 0, Filters.and(Filters.your(self),
                    Filters.warrior, filter), null);
            if (action != null) {
                action.setText("Deploy due to lost " + GameUtils.getFullName(cardLost));
                // Remember the character lost was a Jedi
                action.appendBeforeCost(
                        new SetWhileInPlayDataEffect(action, self, new WhileInPlayData(Filters.Jedi.accepts(game, cardLost))));
                return Collections.singletonList(action);
            }
        }
        return null;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, Filters.hasAttached(self), 2));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(playerId);

        // Check condition(s)
        if (GameConditions.isOnceDuringOpponentsPhase(game, self, playerId, gameTextSourceCardId, Phase.MOVE)) {
            int forceToLose = GameConditions.cardHasWhileInPlayDataEquals(self, true) ? 2 : 1;

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId);
            action.setText("Make " + opponent + " lose " + forceToLose + " Force");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, opponent, forceToLose));
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
        if (TriggerConditions.isEndOfOpponentsPhase(game, self, effectResult, Phase.MOVE)
                && GameConditions.isOnceDuringOpponentsPhase(game, self, playerId, gameTextSourceCardId, Phase.MOVE)) {
            int forceToLose = GameConditions.cardHasWhileInPlayDataEquals(self, true) ? 2 : 1;

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Make " + opponent + " lose " + forceToLose + " Force");
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, opponent, forceToLose));
            return Collections.singletonList(action);
        }
        return null;
    }
}