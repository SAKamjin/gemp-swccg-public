package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractSite;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.CancelForceDrainEffect;
import com.gempukku.swccgo.cards.effects.usage.OncePerTurnEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Location
 * Subtype: Site
 * Title: Danktooine: North Corridor
 */
public class Card305_144 extends AbstractSite {
    public Card305_144() {
        super(Side.DARK, "Danktooine: North Corridor", Title.Danktooine, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLocationDarkSideGameText("If you control, once per turn may use 2 Force to cancel a Force drain at a Cloud City site.");
        setLocationLightSideGameText("If you control, once per turn may use 1 Force to cancel a Force drain at a Cloud City site.");
        addIcon(Icon.DARK_FORCE, 1);
        addIcon(Icon.LIGHT_FORCE, 2);
        addIcons(Icon.ABT, Icon.INTERIOR_SITE, Icon.SCOMP_LINK);
        addKeywords(Keyword.DANKTOOINE_LOCATION);
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextDarkSideOptionalAfterTriggers(String playerOnDarkSideOfLocation, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;

        // Check condition(s)
        if (TriggerConditions.forceDrainInitiatedAt(game, effectResult, Filters.Danktooine_site)
                && GameConditions.isOncePerTurn(game, self, playerOnDarkSideOfLocation, gameTextSourceCardId, gameTextActionId)
                && GameConditions.canCancelForceDrain(game, playerOnDarkSideOfLocation, self)
                && GameConditions.canUseForce(game, playerOnDarkSideOfLocation, 2)
                && GameConditions.controls(game, playerOnDarkSideOfLocation, self)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, playerOnDarkSideOfLocation, gameTextSourceCardId, gameTextActionId);
            action.setText("Cancel Force drain");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerTurnEffect(action));
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerOnDarkSideOfLocation, 2));
            // Perform result(s)
            action.appendEffect(
                    new CancelForceDrainEffect(action));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextLightSideOptionalAfterTriggers(String playerOnLightSideOfLocation, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_2;

        // Check condition(s)
        if (TriggerConditions.forceDrainInitiatedAt(game, effectResult, Filters.Danktooine_site)
                && GameConditions.isOncePerTurn(game, self, playerOnLightSideOfLocation, gameTextSourceCardId, gameTextActionId)
                && GameConditions.canCancelForceDrain(game, playerOnLightSideOfLocation, self)
                && GameConditions.canUseForce(game, playerOnLightSideOfLocation, 1)
                && GameConditions.controls(game, playerOnLightSideOfLocation, self)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, playerOnLightSideOfLocation, gameTextSourceCardId, gameTextActionId);
            action.setText("Cancel Force drain");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerTurnEffect(action));
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerOnLightSideOfLocation, 1));
            // Perform result(s)
            action.appendEffect(
                    new CancelForceDrainEffect(action));
            return Collections.singletonList(action);
        }
        return null;
    }
}