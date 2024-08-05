package com.gempukku.swccgo.cards.set303.dark;

import com.gempukku.swccgo.cards.AbstractImmediateEffect;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.InPlayDataSetCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.PlayCardZoneOption;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.SpotOverride;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.WhileInPlayData;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.PlayCardAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.AttachCardFromTableEffect;
import com.gempukku.swccgo.logic.modifiers.EachTrainingDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotAttemptJediTestsModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: Shadow Academy
 * Type: Effect
 * Subtype: Immediate
 * Title: Through Power, I Gain Victory
 */
public class Card303_006 extends AbstractImmediateEffect {
    public Card303_006() {
        super(Side.DARK, 3, PlayCardZoneOption.ATTACHED, Title.Through_Power_I_Gain_Victory, Uniqueness.UNIQUE, ExpansionSet.SA, Rarity.V);
        setLore("'Unless the victory is achieved by demonstrating that your power is superior, it is only an illusion. Temporary at best. We seek more.' - Yuthura Ban");
        setGameText("Deploy on an apprentice at the beginning of your turn. Apprentice may not attempt Sith Tests until end of your next turn. At any time, may relocate Immediate Effect from apprentice to a Sith Test. Apprentice adds 3 to training destiny of this test. (Immune to Control). ");
        addImmuneToCardTitle(Title.Control);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayNotAttemptJediTestsModifier(self, Filters.apprentice, new InPlayDataSetCondition(self)));
        modifiers.add(new EachTrainingDestinyModifier(self, Filters.hasAttached(self),  3));
        return modifiers;
    }

    @Override
    protected List<PlayCardAction> getGameTextOptionalAfterActions(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.isStartOfYourTurn(game, effectResult, self)
            && GameConditions.canSpot(game, self, SpotOverride.INCLUDE_ALL, Filters.apprentice)) {
            PlayCardAction action = getPlayCardAction(playerId, game, self, self, true, 0, null, null, null, null, null, false, 0, Filters.apprentice, null);
            if (action != null) {
                self.setWhileInPlayData(new WhileInPlayData(game.getGameState().getPlayersLatestTurnNumber(playerId)));
                return Collections.singletonList(action);
            }
        }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggersAlwaysWhenInPlay(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();
        // Check condition(s)
        if (GameConditions.cardHasWhileInPlayDataSet(self)
                && TriggerConditions.isEndOfYourTurn(game, effectResult, playerId)
                && GameConditions.isTurnNumber(game, self.getWhileInPlayData().getIntValue() + 1)) {
                self.setWhileInPlayData(null);
            }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        Filter jediTestFilter = Filters.and(Filters.Jedi_Test, Filters.not(Filters.completed_Jedi_Test));
        // Check condition(s)
        if (TriggerConditions.isTableChanged(game,effectResult)
               && !GameConditions.cardHasWhileInPlayDataSet(self)
                && !GameConditions.isAttachedTo(game, self, Filters.Jedi_Test)
                && GameConditions.canSpot(game, self, jediTestFilter)) {
            PhysicalCard jediTest = Filters.findFirstActive(game, self, jediTestFilter);
            RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Relocate to Jedi Test");
            // Perform result(s)
            action.appendEffect(
                    new AttachCardFromTableEffect(action, self, jediTest));
            return Collections.singletonList(action);
        }
        return null;
    }
}