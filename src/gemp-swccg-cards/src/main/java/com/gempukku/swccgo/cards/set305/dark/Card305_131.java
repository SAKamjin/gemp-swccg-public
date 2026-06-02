package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractImperial;
import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.usage.OncePerGameEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.PlayCardOptionId;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.AddUntilEndOfGameModifierEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardToTargetFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Imperial
 * Title: Son Of Lap'lamiz
 */
public class Card305_131 extends AbstractImperial {
    public Card305_131() {
        super(Side.DARK, 1, 5, 4, 5, 8, "Son Of Lap'lamiz", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Rohan Lap'lamiz. Son of Kamjin. Seeking his father's approval, his brothers' acceptance, and his sister's praise. His attachments will be his undoing.");
        setGameText("Deploys only on Arx. Adds 2 to power of anything he pilots. Once per game, during your deploy phase, a lightsaber may deploy on Rohan (for free) from Reserve Deck; reshuffle. May be deployed instead of Komilia by I Have Neglected Your Training (that card then targets Rohan instead of Komilia for remainder of game). Immune to attrition < 4.");
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR, Icon.CSP);
        addPersona(Persona.ROHAN);
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.Deploys_at_Arx;
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayDeployToDagobahLocationModifier(self));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, 4));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.SON_OF_LAPLAMIZ__DOWNLOAD_LIGHTSABER;

        // Check condition(s)
        if (GameConditions.isOncePerGame(game, self, gameTextActionId)
                && GameConditions.isDuringYourPhase(game, self, Phase.DEPLOY)
                && GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Deploy lightsaber from Reserve Deck");
            action.setActionMsg("Deploy a lightsaber on " + GameUtils.getCardLink(self) + " from Reserve Deck");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerGameEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new DeployCardToTargetFromReserveDeckEffect(action, Filters.lightsaber, Filters.sameCardId(self), true, true));
            return Collections.singletonList(action);
        }

        // Check condition(s)
        if (GameConditions.canSpot(game, self, Filters.and(Filters.I_Have_Neglected_Your_Training, Filters.not(Filters.hasGameTextModification(ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA))))) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId);
            action.setText("Make I Have Neglected Your Training target Rohan");
            action.setActionMsg("Make I Have Neglected Your Training / Your Training Is Now Complete target Rohan instead of Komilia for remainder of game");
            // Perform result(s)
            action.appendEffect(
                    new AddUntilEndOfGameModifierEffect(action, new ModifyGameTextModifier(self,
                            Filters.or(Filters.I_Have_Neglected_Your_Training, Filters.Your_Training_Is_Now_Complete), ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA),
                            "Makes I Have Neglected Your Training / Your Training Is Now Complete target Rohan instead of Komilia for remainder of game"));
            return Collections.singletonList(action);
        }
        return null;
    }
}
