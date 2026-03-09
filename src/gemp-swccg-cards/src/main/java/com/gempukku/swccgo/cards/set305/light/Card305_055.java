package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtSameSiteAsCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.effects.PlaceCardOutOfPlayFromTableEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardToLocationFromLostPileEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardToLocationFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Miyori Theed
 */
public class Card305_055 extends AbstractAlien {
    public Card305_055() {
        super(Side.LIGHT, 3, 2, 1, 2, 2, "Miyori Theed", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("A Nihilgenia clone serving the Keibatsu family. Miyori first served in Theed where she infiltrated and spied on the restored Nabooian royal palace following the fall of the Empire.");
        setGameText("While at same site as Mihoshi, Miyori is power +2 and Mihoshi is immune to attrition. When at a site, during your deploy phase may place Miyori out of play to deploy Mihoshi to that site (for free) from your Reserve Deck (reshuffle) or Lost Pile.");
        addIcons(Icon.ABT, Icon.COU);
        addKeywords(Keyword.FEMALE, Keyword.NIHILGENIA);
        addPersona(Persona.MIYORI);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition atSameSiteAsMihoshi = new AtSameSiteAsCondition(self, Filters.Mihoshi);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, Filters.MIYORI, atSameSiteAsMihoshi, 2));
        modifiers.add(new ImmuneToAttritionModifier(self, Filters.Mihoshi, atSameSiteAsMihoshi));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        List<TopLevelGameTextAction> actions = new LinkedList<TopLevelGameTextAction>();
        GameTextActionId gameTextActionId = GameTextActionId.MIYORI__DOWNLOAD_MIHOSHI;

        // Check condition(s)
        if (GameConditions.isDuringYourPhase(game, self, Phase.DEPLOY)) {
            PhysicalCard location = game.getModifiersQuerying().getLocationThatCardIsAt(game.getGameState(), self);
            if (Filters.site.accepts(game, location)) {
                if (GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId, Persona.MIHOSHI)) {

                    final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
                    action.setText("Deploy Mihoshi from Reserve Deck");
                    // Pay cost(s)
                    action.appendCost(
                            new PlaceCardOutOfPlayFromTableEffect(action, self));
                    // Perform result(s)
                    action.appendEffect(
                            new DeployCardToLocationFromReserveDeckEffect(action, Filters.Mihoshi, Filters.sameCardId(location), true, true));
                    actions.add(action);
                }

                if (GameConditions.canDeployCardFromLostPile(game, playerId, self, gameTextActionId, Persona.MIHOSHI)) {

                    final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
                    action.setText("Deploy Mihoshi from Lost Pile");
                    // Pay cost(s)
                    action.appendCost(
                            new PlaceCardOutOfPlayFromTableEffect(action, self));
                    // Perform result(s)
                    action.appendEffect(
                            new DeployCardToLocationFromLostPileEffect(action, Filters.Mihoshi, Filters.sameCardId(location), true, false));
                    actions.add(action);
                }
            }
        }
        return actions;
    }
}
