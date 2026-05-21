package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractCapitalStarship;
import com.gempukku.swccgo.cards.AbstractPermanentAboard;
import com.gempukku.swccgo.cards.AbstractPermanentPilot;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.usage.OncePerGameEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.choose.DeployCardToTargetFromReserveDeckEffect;

import java.util.Collections;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Capital
 * Title: Echo of Vauzem
 */
public class Card305_048 extends AbstractCapitalStarship {
    public Card305_048() {
        super(Side.DARK, 1, 7, 6, 7, null, 3, 7, "Echo of Vauzem", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("The Vauzem Dominion's premiere blockade ship was 'donated' by a mysterious backer with the intent of allowing the Dominion to take control of the droid factories on Quermia.");
        setGameText("May add 4 pilots, 4 passengers, 2 vehicles and 4 droid starfighters. Permanent pilot provides ability of 2. Once per game, may deploy a Sephi pilot aboard from Reserve Deck; reshuffle.");
        addPersona(Persona.ECHO_OF_VAUZEM);
        addIcons(Icon.ABT, Icon.PILOT, Icon.NAV_COMPUTER, Icon.VAUZEM);
        addKeywords(Keyword.DROID_CONTROL_SHIP);
        addModelType(ModelType.TRADE_FEDERATION_BATTLESHIP);
        setPilotCapacity(4);
        setPassengerCapacity(4);
        setVehicleCapacity(2);
        setStarfighterCapacity(4, Filters.droid_starfighter);
    }

    @Override
    protected List<? extends AbstractPermanentAboard> getGameTextPermanentsAboard() {
        return Collections.singletonList(new AbstractPermanentPilot(2) {});
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.ECHO_OF_VAUZEM__DOWNLOAD_SEPHI;

        // Check condition(s)
        if (GameConditions.isOncePerGame(game, self, gameTextActionId)
                && GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Deploy card from Reserve Deck");
            action.setActionMsg("Deploy a Neimoidian pilot aboard " + GameUtils.getCardLink(self) + " from Reserve Deck");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerGameEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new DeployCardToTargetFromReserveDeckEffect(action, Filters.and(Filters.Sephi, Filters.pilot), Filters.Deploys_aboard_Echo_of_Vauzem, true));
            return Collections.singletonList(action);
        }
        return null;
    }
}
