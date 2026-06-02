package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AboardStarshipOrVehicleOfPersonaCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerGameEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.ImmuneToTitleModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Morvayne Korr, Engineer
 */
public class Card305_110 extends AbstractAlien {
    public Card305_110() {
        super(Side.DARK, 1, 3, 3, 3, 5, "Morvayne Korr, Engineer", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Lady Morvayne, began her life as an engineer. Thanks to her skills the droid factories were restarted and the Vauzem Dominion was able to invade Quermia.");
        setGameText("Adds 3 to power of any capital starship she pilots. Once per game may take Restart The Droid Factory into hand from Reserve Deck; reshuffle. While aboard Echo of Vauzem, it is immune to attrition < 5 and Restart The Droid Factory is immune to Alter.");
        addPersona(Persona.MORVAYNE);
        addIcons(Icon.ABT, Icon.PILOT);
        setSpecies(Species.SEPHI);
        setMatchingStarshipFilter(Filters.ECHO_OF_VAUZEM);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition aboardEchoofVauzem = new AboardStarshipOrVehicleOfPersonaCondition(self, Persona.ECHO_OF_VAUZEM);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 3, Filters.capital_starship));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, Filters.ECHO_OF_VAUZEM, aboardEchoofVauzem, 5));
        modifiers.add(new ImmuneToTitleModifier(self, Filters.Restart_The_Droid_Factory, aboardEchoofVauzem, Title.Alter));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.MORVAYNE_KORR_ENGINEER__UPLOAD_RESTART_THE_DROID_FACTORY;

        // Check condition(s)
        if (GameConditions.isOncePerGame(game, self, gameTextActionId)
                && GameConditions.canTakeCardsIntoHandFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Take card into hand from Reserve Deck");
            action.setActionMsg("Take Restart The Droid Factory into hand from Reserve Deck");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerGameEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new TakeCardIntoHandFromReserveDeckEffect(action, playerId, Filters.Restart_The_Droid_Factory, true));
            return Collections.singletonList(action);
        }
        return null;
    }
}
