package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.cards.conditions.OnCloudCityCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromLostPileEffect;
import com.gempukku.swccgo.logic.modifiers.DeploysFreeToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Vezzi Zelarin
 */
public class Card305_156 extends AbstractAlien {
    public Card305_156() {
        super(Side.DARK, 3, 2, 1, 2, 3, "Vezzi Zelarin", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Female Toydarian. Left Seraph for Danktooine after the Civil War. Works as a Meknek. Hopes one day to return to Seraph.");
        setGameText("Deploys free to a Seraph or Danktooine site. Power +2 on Danktooine. During your control phase, may use 3 Force to search your Lost Pile and take one weapon or device into hand.");
        addIcons(Icon.ABT);
        addKeyword(Keyword.FEMALE);
        setSpecies(Species.TOYDARIAN);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeploysFreeToLocationModifier(self, Filters.or(Filters.Seraph_site, Filters.Danktooine_site)));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, new AtCondition(self, Filters.Danktooine_site), 2));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.VEZZI_ZELARIN__UPLOAD_WEAPON_OR_DEVICE_FROM_LOST_PILE;

        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.CONTROL)
                && GameConditions.canTakeCardsIntoHandFromLostPile(game, playerId, self, gameTextActionId)
                && GameConditions.canUseForce(game, playerId, 3)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Take card into hand from Lost Pile");
            action.setActionMsg("Take a weapon or device into hand from Lost Pile");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerId, 3));
            // Perform result(s)
            action.appendEffect(
                    new TakeCardIntoHandFromLostPileEffect(action, playerId, Filters.or(Filters.weapon, Filters.device), false));
            return Collections.singletonList(action);
        }
        return null;
    }
}
