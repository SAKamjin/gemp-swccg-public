package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractNormalEffect;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
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
import com.gempukku.swccgo.game.state.WhileInPlayData;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.ModifyGameTextModifier;
import com.gempukku.swccgo.logic.modifiers.ModifyGameTextType;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Effect
 * Title: Fear Is The Path To The Dark Side
 */
public class Card305_160 extends AbstractNormalEffect {
    public Card305_160() {
        super(Side.DARK, 2, PlayCardZoneOption.ATTACHED, Title.Fear_Is_The_Path_To_The_Dark_Side, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("'Fear is the path to the dark side. Fear leads to anger. Anger leads to hate. Hate leads to suffering.'");
        setGameText("Deploy on character with ability > 3. They are power +2. Opponent loses 2 Force at end of each of opponent's turns in which they did not battle and you had a character of ability > 3 on table. (Immune to Alter.)");
        addKeywords(Keyword.DEPLOYS_ON_CHARACTERS);
        addIcons(Icon.ABT);
        addImmuneToCardTitle(Title.Alter);
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.and(Filters.abilityMoreThan(3), Filters.character);
    }

    @Override
    protected Filter getGameTextValidTargetFilterToRemainAttachedToAfterCrossingOver(final SwccgGame game, final PhysicalCard self, PlayCardOptionId playCardOptionId) {
        return Filters.and(Filters.abilityMoreThan(3), Filters.character);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, self.getAttachedTo(), 2));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(final SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(self.getOwner());
        PhysicalCard character = self.getAttachedTo();

        // Check condition(s)
        if (GameConditions.cardHasWhileInPlayDataSet(self)
                && TriggerConditions.isStartOfOpponentsTurn(game, effectResult, self)
                && !GameConditions.canSpot(game, self, Filters.and(Filters.your(self), Filters.character, Filters.abilityMoreThan(3)))) {
            self.setWhileInPlayData(null);
        }
        else if (!GameConditions.cardHasWhileInPlayDataSet(self)
                && TriggerConditions.isTableChanged(game, effectResult)
                && GameConditions.canSpot(game, self, Filters.and(Filters.your(self), Filters.character, Filters.abilityMoreThan(3)))) {
            self.setWhileInPlayData(new WhileInPlayData());
        }

        // Check condition(s)
        if (GameConditions.cardHasWhileInPlayDataSet(self)
                && TriggerConditions.isEndOfOpponentsTurn(game, effectResult, self)
                && !GameConditions.hasParticipatedInBattleThisTurn(game, character)) {

            RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Make " + opponent + " lose 2 Force");
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, opponent, 2));
            return Collections.singletonList(action);
        }
        return null;
    }
}