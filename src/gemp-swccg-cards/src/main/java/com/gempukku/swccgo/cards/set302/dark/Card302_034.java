package com.gempukku.swccgo.cards.set302.dark;

import com.gempukku.swccgo.cards.AbstractNormalEffect;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.AddToForceDrainEffect;
import com.gempukku.swccgo.cards.effects.usage.NumTimesPerTurnEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.PlayCardOptionId;
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
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.CancelCardOnTableEffect;
import com.gempukku.swccgo.logic.modifiers.DefinedByGameTextDeployCostModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: Dark Jedi Brotherhood Core
 * Type: Effect
 * Title: Arx Blockade
 */
public class Card302_034 extends AbstractNormalEffect {
    public Card302_034() {
        super(Side.DARK, 5, PlayCardZoneOption.ATTACHED, Title.Arx_Blockade, Uniqueness.UNIQUE, ExpansionSet.DJB_CORE, Rarity.V);
        setLore("Fearing invasion from outside choatic elements the Brotherhood can quickly blockade their own system.");
        setGameText("Use 2 Force to deploy on Arx system if opponent controls no Arx sites. Your Force drains are +2 at up to X other battleground systems, where X = number of Arx sites you occupy. Effect canceled if opponent controls three Arx sites. Immune to Alter.");
        addKeywords(Keyword.DEPLOYS_ON_LOCATION);
        addImmuneToCardTitle(Title.Alter);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DefinedByGameTextDeployCostModifier(self, 2));
        return modifiers;
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.Arx_system;
    }

    @Override
    protected boolean checkGameTextDeployRequirements(String playerId, SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return !GameConditions.controls(game, game.getOpponent(playerId), Filters.Arx_site);
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;

        // Check condition(s)
        if (TriggerConditions.forceDrainInitiatedBy(game, effectResult, playerId, Filters.and(Filters.battleground_system, Filters.not(Filters.Arx_system)))) {
            int numSites = Filters.countTopLocationsOnTable(game, Filters.and(Filters.Arx_site, Filters.occupies(playerId)));
            if (GameConditions.isNumTimesPerTurn(game, self, playerId, numSites, gameTextSourceCardId, gameTextActionId)) {

                final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
                action.setText("Add 2 to Force drain");
                // Update usage limit(s)
                action.appendUsage(
                        new NumTimesPerTurnEffect(action, numSites));
                // Perform result(s)
                action.appendEffect(
                        new AddToForceDrainEffect(action, 2));
                return Collections.singletonList(action);
            }
        }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(final SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String opponent = game.getOpponent(self.getOwner());

        // Check condition(s)
        if (TriggerConditions.isTableChanged(game, effectResult)
                && GameConditions.canBeCanceled(game, self)
                && GameConditions.controls(game, opponent, 3, SpotOverride.INCLUDE_EXCLUDED_FROM_BATTLE, Filters.Arx_site)) {

            RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setSingletonTrigger(true);
            action.setText("Cancel");
            action.setActionMsg("Cancel " + GameUtils.getCardLink(self));
            // Perform result(s)
            action.appendEffect(
                    new CancelCardOnTableEffect(action, self));
            return Collections.singletonList(action);
        }
        return null;
    }
}