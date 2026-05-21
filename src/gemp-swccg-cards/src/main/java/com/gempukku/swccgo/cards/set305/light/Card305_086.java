package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AllCharactersOnSystemCondition;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.cards.conditions.DuringBattleAtCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerPhaseEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.AndCondition;
import com.gempukku.swccgo.logic.effects.choose.DeployCardFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.AddsBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Vaelor Quis
 */
public class Card305_086 extends AbstractAlien {
    public Card305_086() {
        super(Side.LIGHT, 1, 3, 3, 3, 6, Title.Vaelor_Quis, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Vaelor Quis is a respected leader of his people. He sought out Clan Odan-Urr to establish new trade and culture exchange. Now he looks to them to save his people from the Vauzem.");
        setGameText("While at a Quermia Senate site and all of your characters on Quermia are Quermian: add one battle destiny in battles at Quermia sites and once during your deploy phase may deploy a Quermian or Hidden Corridors from Reserve Deck; reshuffle.");
        addIcons(Icon.ABT);
        addKeywords(Keyword.LEADER);
        setSpecies(Species.QUERMIAN);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        String playerId = self.getOwner();

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsBattleDestinyModifier(self, new AndCondition(new AtCondition(self, Filters.Quermia_Senate_Site),
                new DuringBattleAtCondition(Filters.Quermia_site), new AllCharactersOnSystemCondition(self, playerId, Title.Quermia, Filters.Quermian)),
                1, playerId, true));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.VAELOR_QUIS__DOWNLOAD_QUERMIAN_OR_HIDDEN_CORRIDORS;

        // Check condition(s)
        if (GameConditions.isOnceDuringYourPhase(game, self, playerId, gameTextSourceCardId, gameTextActionId, Phase.DEPLOY)
                && GameConditions.isAtLocation(game, self, Filters.Quermia_Senate_Site)
                && GameConditions.isAllCharactersOnSystem(game, self, playerId, Title.Quermia, Filters.Quermian)
                && GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Deploy card from Reserve Deck");
            action.setActionMsg("Deploy a Quermian or Steady, Steady from Reserve Deck");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerPhaseEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new DeployCardFromReserveDeckEffect(action, Filters.or(Filters.Quermian, Filters.Hidden_Corridors), true));
            return Collections.singletonList(action);
        }
        return null;
    }
}
