package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractObjective;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.actions.ObjectiveDeployedTriggerAction;
import com.gempukku.swccgo.cards.conditions.GameTextModificationCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.DeploymentRestrictionsOption;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.NotCondition;
import com.gempukku.swccgo.logic.effects.AddUntilEndOfGameModifierEffect;
import com.gempukku.swccgo.logic.effects.FlipCardEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardFromReserveDeckEffect;
import com.gempukku.swccgo.logic.effects.choose.DeployCardToSystemFromReserveDeckEffect;
import com.gempukku.swccgo.logic.modifiers.DestinyDrawForActionSourceModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotBeConvertedModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotContributeToForceRetrievalModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotForceDrainAtLocationModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.ModifierFlag;
import com.gempukku.swccgo.logic.modifiers.ModifyGameTextModifier;
import com.gempukku.swccgo.logic.modifiers.ModifyGameTextType;
import com.gempukku.swccgo.logic.modifiers.PlaceJediTestOnTableWhenCompletedModifier;
import com.gempukku.swccgo.logic.modifiers.PlayersCardsAtLocationMayNotContributeToForceRetrievalModifier;
import com.gempukku.swccgo.logic.modifiers.SpecialFlagModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Set: A Better Tomorrow
 * Type: Objective
 * Title: I Have Neglected Your Training / Your Training Is Now Complete
 */
public class Card305_130 extends AbstractObjective {
    public Card305_130() {
        super(Side.DARK, 0, Title.I_Have_Neglected_Your_Training, ExpansionSet.ABT, Rarity.R);
        setFrontOfDoubleSidedCard(true);
        setGameText("Deploy The Shadow Academy Training (may not be converted). While this side up, during your deploy phase, may deploy Komilia, a Headmaster/Headmistress or Instructor (deploy -2), Through Power I Gain Victory, Through Passion I Gain Strength, Impatient Apprentice, and/or Shadow Academy Holocron to The Shadow Academy from Reserve Deck; reshuffle. Whenever you draw training destiny, draw two and choose one. Place Komilia's completed Sith Tests on table. Your cards at a Shadow Academy site may not Force drain or contribute to Force retrieval. Add 4 to each player's destiny draw for Sense and Alter. Flip this card when Komilia completes Sith Test #5.");
        addIcons(Icon.ABT);
    }

    @Override
    protected ObjectiveDeployedTriggerAction getGameTextWhenDeployedAction(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        ObjectiveDeployedTriggerAction action = new ObjectiveDeployedTriggerAction(self);
        action.appendRequiredEffect(
                new DeployCardFromReserveDeckEffect(action, Filters.Shadow_Academy_Training_Grounds, true, false) {
                    @Override
                    public String getChoiceText() {
                        return "Deploy Shadow Academy: Training Grounds";
                    }
                });
        return action;
    }

    @Override
    protected RequiredGameTextTriggerAction getGameTextAfterDeploymentCompletedAction(String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
        action.appendEffect(
                new AddUntilEndOfGameModifierEffect(action,
                        new MayNotBeConvertedModifier(self, Filters.Shadow_Academy_Training_Grounds), null));
        return action;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.I_HAVE_NEGLECTED_YOUR_TRAINING__DOWNLOAD_CARD_TO_SHADOW_ACADEMY;

        // Check condition(s)
        if (GameConditions.isDuringYourPhase(game, self, Phase.DEPLOY)) {
            boolean targetsRohanInsteadOfKomilia = GameConditions.hasGameTextModification(game, self, ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA);
            Set<Persona> personas = targetsRohanInsteadOfKomilia ? new HashSet<Persona>(Arrays.asList(Persona.ROHAN, Persona.HEADMASTER, Persona.INSTRUCTOR)) : new HashSet<Persona>(Arrays.asList(Persona.KOMILIA, Persona.HEADMASTER, Persona.INSTRUCTOR));
            if (GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId, personas, Arrays.asList(Title.Through_Power_I_Gain_Victory, Title.Through_Passion_I_Gain_Strength, Title.Shadow_Academy_Holocron, Title.Impatient_Apprentice))) {
                if (targetsRohanInsteadOfKomilia) {

                    final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
                    action.setText("Deploy card to Shadow Academy from Reserve Deck");
                    action.setActionMsg("Deploy Rohan, Headmaster/Headmistress or Instructor, Through Power I Gain Victory, Through Passion I Gain Strength, Impatient Apprentice, or Shadow Academy Holocron to Shadow Academy from Reserve Deck");
                    // Perform result(s)
                    action.appendEffect(
                            new DeployCardToSystemFromReserveDeckEffect(action, Filters.or(Filters.Rohan, Filters.SA_Instructor, Filters.Through_Power_I_Gain_Victory, Filters.Through_Passion_I_Gain_Strength, Filters.Shadow_Academy_Holocron, Filters.Impatient_Apprentice), Title.Shadow_Academy_Training_Grounds, -2, Filters.SA_Instructor, DeploymentRestrictionsOption.ignoreLocationDeploymentRestrictions(), true));
                    return Collections.singletonList(action);
                }
                else {

                    final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId, gameTextActionId);
                    action.setText("Deploy card to Shadow Academy from Reserve Deck");
                    action.setActionMsg("Deploy Rohan, Headmaster/Headmistress or Instructor, Through Power I Gain Victory, Through Passion I Gain Strength, Impatient Apprentice, or Shadow Academy Holocron to Shadow Academy from Reserve Deck");
                    // Perform result(s)
                    action.appendEffect(
                            new DeployCardToSystemFromReserveDeckEffect(action, Filters.or(Filters.Komilia, Filters.SA_Instructor, Filters.Through_Power_I_Gain_Victory, Filters.Through_Passion_I_Gain_Strength, Filters.Shadow_Academy_Holocron, Filters.Impatient_Apprentice), Title.Shadow_Academy_Training_Grounds, -2, Filters.SA_Instructor, DeploymentRestrictionsOption.ignoreLocationDeploymentRestrictions(), true) {
                                @Override
                                protected void cardDeployed(PhysicalCard card) {
                                    if (Filters.Son_Of_Laplamiz.accepts(game, card)) {
                                        game.getGameState().sendMessage(playerId + " makes Peace Is A Lie, There Is Only Passion / The Force Shall Free Me target Rohan instead of Komilia for remainder of game using " + GameUtils.getCardLink(card));
                                        game.getModifiersEnvironment().addUntilEndOfGameModifier(new ModifyGameTextModifier(card, Filters.or(Filters.Peace_Is_A_Lie_There_Is_Only_Passion, Filters.The_Force_Shall_Free_Me), ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA));
                                    }
                                }
                            });
                    return Collections.singletonList(action);
                }
            }
        }
        return null;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, PhysicalCard self) {
        String playerId = self.getOwner();
        Condition targetsRohanInsteadOfKomilia = new GameTextModificationCondition(self, ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new SpecialFlagModifier(self, ModifierFlag.DRAW_TWO_AND_CHOOSE_ONE_FOR_TRAINING_DESTINY, playerId));
        modifiers.add(new ModifyGameTextModifier(self, Filters.and(Filters.SITH_TEST_5, Filters.completed_Jedi_Test, Filters.jediTestTargetingApprentice(Filters.Komilia)), new NotCondition(targetsRohanInsteadOfKomilia), ModifyGameTextType.IT_IS_THE_FUTURE_YOU_SEE__STACK_DESTINY_CARD_ON_JEDI_TEST_5));
        modifiers.add(new ModifyGameTextModifier(self, Filters.and(Filters.SITH_TEST_5, Filters.completed_Jedi_Test, Filters.jediTestTargetingApprentice(Filters.Rohan)), targetsRohanInsteadOfKomilia, ModifyGameTextType.IT_IS_THE_FUTURE_YOU_SEE__STACK_DESTINY_CARD_ON_JEDI_TEST_5));
        modifiers.add(new PlaceJediTestOnTableWhenCompletedModifier(self, Filters.jediTestTargetingApprentice(Filters.Komilia), new NotCondition(targetsRohanInsteadOfKomilia)));
        modifiers.add(new PlaceJediTestOnTableWhenCompletedModifier(self, Filters.jediTestTargetingApprentice(Filters.Rohan), targetsRohanInsteadOfKomilia));
        modifiers.add(new MayNotForceDrainAtLocationModifier(self, Filters.Shadow_Academy_location, playerId));
        modifiers.add(new MayNotContributeToForceRetrievalModifier(self, Filters.and(Filters.your(self), Filters.or(Filters.Shadow_Academy_location, Filters.at(Title.Shadow_Academy)))));
        modifiers.add(new PlayersCardsAtLocationMayNotContributeToForceRetrievalModifier(self, Filters.Shadow_Academy_location, playerId));
        modifiers.add(new DestinyDrawForActionSourceModifier(self, Filters.and(Filters.or(Filters.Sense, Filters.Alter), Filters.canBeTargetedBy(self)), 4));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.jediTestCompletedBy(game, effectResult, Filters.SITH_TEST_5, GameConditions.hasGameTextModification(game, self, ModifyGameTextType.I_HAVE_NEGLECTED_YOUR_TRAINING_YOUR_TRAINING_IS_NOW_COMPLETE__TARGETS_ROHAN_INSTEAD_OF_KOMILIA) ? Filters.Rohan : Filters.Komilia)
                && GameConditions.canBeFlipped(game, self)) {

            RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
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