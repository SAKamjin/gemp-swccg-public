package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractNormalEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
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
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.ActivateForceEffect;
import com.gempukku.swccgo.logic.effects.ChooseEffectEffect;
import com.gempukku.swccgo.logic.effects.RetrieveForceEffect;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.StandardEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Effect
 * Title: Restart The Droid Factory
 */
public class Card305_108 extends AbstractNormalEffect {
    public Card305_108() {
        super(Side.DARK, 4, PlayCardZoneOption.ATTACHED, Title.Restart_The_Droid_Factory, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLore("It was by luck the Vauzem Dominion was able to locate the top-secret frequencies to control the droids after they restarted the old CIS Droid Factory.");
        setGameText("Deploy on your Echo of Vauzem (may not deploy on a site). Whenever you initiate a battle at a related planet site where you have a battle droid, you may activate 2 Force or retrieve 1 Force.");
        addIcons(Icon.ABT);
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.and(Filters.your(self), Filters.ECHO_OF_VAUZEM);
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(final SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();
        Filter filter = Filters.and(Filters.planet_site, Filters.relatedSite(self), Filters.sameSiteAs(self, Filters.and(Filters.your(self), Filters.battle_droid)));

        // Check condition(s)
        if (TriggerConditions.battleInitiatedAt(game, effectResult, playerId, filter)) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Activate 2 Force or retrieve 1 Force");
            // Perform result(s)
            List<StandardEffect> effectChoices = new ArrayList<StandardEffect>();
            effectChoices.add(new ActivateForceEffect(action, playerId, 2));
            effectChoices.add(new RetrieveForceEffect(action, playerId, 1) {
                @Override
                public Collection<PhysicalCard> getAdditionalCardsInvolvedInForceRetrieval() {
                    return Filters.filterActive(game, null, Filters.and(Filters.your(self), Filters.participatingInBattle));
                }
            });
            action.appendEffect(
                    new ChooseEffectEffect(action, playerId, effectChoices));
            return Collections.singletonList(action);
        }
        return null;
    }
}