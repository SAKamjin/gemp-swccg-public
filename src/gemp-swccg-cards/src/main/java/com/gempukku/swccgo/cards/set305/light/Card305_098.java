package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractObjective;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.actions.ObjectiveDeployedTriggerAction;
import com.gempukku.swccgo.cards.effects.usage.OncePerTurnEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.SpotOverride;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.ActivateForceEffect;
import com.gempukku.swccgo.logic.effects.AddUntilEndOfGameModifierEffect;
import com.gempukku.swccgo.logic.effects.FlipCardEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.MayNotDeployModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotDeployToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotDeployUsingDejarikRulesModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.NoForceLossFromCardModifier;
import com.gempukku.swccgo.logic.modifiers.SuspendsCardModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Objective
 * Title: A Senate Motion / Speaker We Have The Floor
 */
public class Card305_098 extends AbstractObjective {
    public Card305_098() {
        super(Side.LIGHT, 0, Title.A_Senate_Motion, ExpansionSet.ABT, Rarity.U);
        setFrontOfDoubleSidedCard(true);
        setGameText("Deploy Senate Council Chambers, Senate Hallway, and Senate Courtyard. For remainder of game, you may not deploy cards with ability except [COU] Jedi, aliens, [COU] vehicles, [COU] characters and [COU] starships. Your Destiny and Complications are suspended. You lose no Force to Vengeance Of The Dark Prince. While this side up, you may not deploy characters to interior Quermia sites. Once per turn, may activate 1 Force. Flip this card if you control Senate Council Chambers (with Mihoshi there).");
        addIcons(Icon.ABT);
    }

    @Override
    protected ObjectiveDeployedTriggerAction getGameTextWhenDeployedAction(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        ObjectiveDeployedTriggerAction action = new ObjectiveDeployedTriggerAction(self);
        action.appendRequiredEffect(
                new DeployCardFromReserveDeckEffect(action, Filters.Senate_Council_Chambers, true, false) {
                    @Override
                    public String getChoiceText() {
                        return "Choose Senate Council Chambers to deploy";
                    }
                });
        action.appendRequiredEffect(
                new DeployCardFromReserveDeckEffect(action, Filters.Senate_Hallway, true, false) {
                    @Override
                    public String getChoiceText() {
                        return "Choose Senate Hallway to deploy";
                    }
                });
        action.appendRequiredEffect(
                new DeployCardFromReserveDeckEffect(action, Filters.Senate_Courtyard, true, false) {
                    @Override
                    public String getChoiceText() {
                        return "Choose Senate Courtyard to deploy";
                    }
                });
        return action;
    }

    @Override
    protected RequiredGameTextTriggerAction getGameTextAfterDeploymentCompletedAction(String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
        action.appendEffect(
                new AddUntilEndOfGameModifierEffect(action,
                        new MayNotDeployModifier(self, Filters.and(Filters.hasAbilityOrHasPermanentPilotWithAbility,
                                Filters.not(Filters.or(Filters.and(Icon.COU, Filters.Jedi), Filters.alien, Filters.and(Icon.COU, Filters.vehicle),
                                        Filters.COU_character, Filters.and(Icon.COU, Filters.starship)))), playerId), null));
        action.appendEffect(
                new AddUntilEndOfGameModifierEffect(action,
                        new MayNotDeployUsingDejarikRulesModifier(self, Filters.hasAbilityWhenUsingDejarikRules, playerId), null));
        action.appendEffect(
                new AddUntilEndOfGameModifierEffect(action,
                        new SuspendsCardModifier(self, Filters.and(Filters.Your_Destiny, Filters.Complications)), null));
        action.appendEffect(
                new AddUntilEndOfGameModifierEffect(action,
                        new NoForceLossFromCardModifier(self, Filters.Vengeance_Of_The_Dark_Prince, playerId), null));
        return action;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayNotDeployToLocationModifier(self, Filters.and(Filters.your(self), Filters.character), Filters.interior_Quermia_site));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;

        // Check condition(s)
        if (GameConditions.isOncePerTurn(game, self, playerId, gameTextSourceCardId, gameTextActionId)
                && GameConditions.canActivateForce(game, playerId)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Activate 1 Force");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerTurnEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new ActivateForceEffect(action, playerId, 1));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();

        // Check condition(s)
        if (TriggerConditions.isTableChanged(game, effectResult)
                && GameConditions.canBeFlipped(game, self)
                && GameConditions.controlsWith(game, self, playerId, Filters.Senate_Council_Chambers, SpotOverride.INCLUDE_EXCLUDED_FROM_BATTLE, Filters.Mihoshi)) {

            RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setSingletonTrigger(true);
            action.setText("Flip");
            action.setActionMsg(null);
            // Perform result(s)
            action.appendEffect(
                    new FlipCardEffect(action, self));
            return Collections.singletonList(action);
        }
        return null;
    }
}