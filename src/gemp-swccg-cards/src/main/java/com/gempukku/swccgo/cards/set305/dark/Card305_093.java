package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractSite;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.actions.MoveUsingLocationTextAction;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.modifiers.EachWeaponDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Location
 * Subtype: Site
 * Title: Quermia: Senate Courtyard
 */
public class Card305_093 extends AbstractSite {
    public Card305_093() {
        super(Side.DARK, Title.Senate_Courtyard, Title.Quermia, Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLocationDarkSideGameText("During your move phase, may move your characters from here to any interior Quermia site.");
        setLocationLightSideGameText("Add 1 to each of your weapon destiny draws here.");
        addIcon(Icon.DARK_FORCE, 2);
        addIcon(Icon.LIGHT_FORCE, 1);
        addIcons(Icon.ABT, Icon.EXTERIOR_SITE, Icon.PLANET);
        addKeywords(Keyword.QUERMIA_SENATE_SITE);
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextDarkSideTopLevelActions(String playerOnDarkSideOfLocation, SwccgGame game, PhysicalCard self, int gameTextSourceCardId) {
        List<TopLevelGameTextAction> actions = new LinkedList<TopLevelGameTextAction>();

        // Check condition(s)
        if (GameConditions.isDuringYourPhase(game, playerOnDarkSideOfLocation, Phase.MOVE)
                && GameConditions.canSpotLocation(game, Filters.interior_Quermia_site)) {
            if (GameConditions.canPerformMovementUsingLocationText(playerOnDarkSideOfLocation, game, Filters.character, self, Filters.interior_Quermia_site, false)) {

                MoveUsingLocationTextAction action = new MoveUsingLocationTextAction(playerOnDarkSideOfLocation, game, self, gameTextSourceCardId, Filters.character, self, Filters.interior_Quermia_site, false);
                action.setText("Move from here to interior Quermia site");
                actions.add(action);
            }
        }
        return actions;
    }

    @Override
    protected List<Modifier> getGameTextLightSideWhileActiveModifiers(String playerOnLightSideOfLocation, SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new EachWeaponDestinyModifier(self, Filters.and(Filters.your(playerOnLightSideOfLocation), Filters.weapon, Filters.here(self)), 1));
        return modifiers;
    }
}