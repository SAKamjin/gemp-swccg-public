package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractDroid;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.PayRelocateBetweenLocationsCostEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.GameTextActionId;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Phase;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.SpotOverride;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.LoseCardFromTableEffect;
import com.gempukku.swccgo.logic.effects.RelocateBetweenLocationsEffect;
import com.gempukku.swccgo.logic.effects.RetrieveForceEffect;
import com.gempukku.swccgo.logic.effects.choose.ChooseCardEffect;
import com.gempukku.swccgo.logic.effects.choose.ChooseCardToLoseFromTableEffect;
import com.gempukku.swccgo.logic.modifiers.MayNotDeployOrMovePlagueisProbeDroidToLocationsModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.PassthruEffect;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Droid
 * Title: Plagueis Probe Droid
 */
public class Card305_116 extends AbstractDroid {
    public Card305_116() {
        super(Side.DARK, 3, 1, 1, 2, Title.Plagueis_Probe_Droid, Uniqueness.UNRESTRICTED, ExpansionSet.ABT, Rarity.R);
        setManeuver(3);
        setLore("Patrol droids utilized by the Sith in Clan Plagueis. Each droid has several multispectral imaging devices and a communications package. Used by Selika to track down Mihoshi.");
        setGameText("When deployed, immediately retrieve 1 Force. Limit 1 Plagueis Probe Droid per location. If present with Mihoshi during your control phase may use 3 Force to relocate this Plagueis Probe Droid to Selika's site, and relocate Selika to same site as Mihoshi.");
        addIcons(Icon.ABT);
        addModelTypes(ModelType.PROBE, ModelType.RECON);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, final PhysicalCard self) {
        //this needs to work while it's in hand, but it shouldn't work while its game text is canceled while on table
        if(self.isGameTextCanceled())
            return null;
        List<Modifier> modifiers = new LinkedList<Modifier>();
        //limit 1 Plagueis Probe Droid per location
        modifiers.add(new MayNotDeployOrMovePlagueisProbeDroidToLocationsModifier(self));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileInactiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        if(self.isGameTextCanceled())
            return null;
        List<Modifier> modifiers = new LinkedList<Modifier>();
        //limit 1 Plagueis Probe Droid per location
        //when this is only in getGameTextAlwaysOnModifiers it doesn't seem work when Plagueis Probe Droid (V) is being deployed
        modifiers.add(new MayNotDeployOrMovePlagueisProbeDroidToLocationsModifier(self));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        //limit 1 Plagueis Probe Droid per location
        //when this is only in getGameTextAlwaysOnModifiers it doesn't seem work when Plagueis Probe Droid (V) is being deployed
        modifiers.add(new MayNotDeployOrMovePlagueisProbeDroidToLocationsModifier(self));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_1;

        //when deployed, immediately retrieve 1 Force
        if (TriggerConditions.justDeployed(game, effectResult, self)) {
            String playerId = self.getOwner();
            final RequiredGameTextTriggerAction action;
            action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setSingletonTrigger(true);
            action.setText("Retrieve 1 Force");
            // Perform result(s)
            action.appendEffect(new RetrieveForceEffect(action, playerId, 1));
            return Collections.singletonList(action);
        }

        return null;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggersAlwaysWhenInPlay(SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        //needs to work while inactive, but not while gametext is canceled
        if(self.isGameTextCanceled())
            return null;

        GameTextActionId gameTextActionId = GameTextActionId.OTHER_CARD_ACTION_2;

        //limit one Plagueis Probe Droid per location
        if(TriggerConditions.isTableChanged(game, effectResult)) {
            //check if there are multiple Plagueis Probe Droids at any locations

            Collection<PhysicalCard> plagueisProbeDroids = Filters.filterAllOnTable(game, Filters.and(Filters.Plagueis_Probe_Droid,Filters.with(null,SpotOverride.INCLUDE_ALL,Filters.Plagueis_Probe_Droid)));
            if(!plagueisProbeDroids.isEmpty()) {
                //find all of the locations that have multiple plagueis probe droids
                Collection<PhysicalCard> locationsToEnforceLimit = new HashSet<PhysicalCard>();
                for(PhysicalCard spd:plagueisProbeDroids) {
                    locationsToEnforceLimit.addAll(Filters.filterTopLocationsOnTable(game, Filters.sameLocation(spd)));
                }

                final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);

                action.setText("Enforce limit of 1 Plagueis Probe Droid per location");

                final SwccgGame gm = game;

                //the player whose turn it is chooses a location to enforce the limit
                String currentPlayerId = gm.getGameState().getCurrentPlayerId();

                action.insertEffect(new ChooseCardEffect (action,currentPlayerId,"Choose a location to enforce limit of 1 Plagueis Probe Droid",
                        locationsToEnforceLimit) {

                    protected void cardSelected(PhysicalCard selectedCard) {
                        if(selectedCard!=null) {
                            gm.getGameState().sendMessage("Enforcing limit of 1 Plagueis Probe Droid at "+GameUtils.getCardLink(selectedCard));

                            String playerOne = self.getOwner();
                            String playerTwo = gm.getOpponent(playerOne);

                            Filter plagueisProbeDroidHere = Filters.and(Filters.Plagueis_Probe_Droid,Filters.at(selectedCard));

                            //check if both players have Plagueis Probe Droids at the selected location
                            Collection<PhysicalCard> playerOneDroids = Filters.filterActive(gm, null, SpotOverride.INCLUDE_ALL, Filters.and(plagueisProbeDroidHere,Filters.owner(playerOne)));
                            Collection<PhysicalCard> playerTwoDroids = Filters.filterActive(gm, null, SpotOverride.INCLUDE_ALL, Filters.and(plagueisProbeDroidHere,Filters.owner(playerTwo)));

                            if(playerOneDroids.size()>0&&playerTwoDroids.size()>0) {
                                LinkedList<PhysicalCard> allDroidsHere = new LinkedList<PhysicalCard>();
                                allDroidsHere.addAll(playerOneDroids);
                                allDroidsHere.addAll(playerTwoDroids);
                                Collections.shuffle(allDroidsHere);

                                PhysicalCard toLose = allDroidsHere.getFirst();
                                gm.getGameState().sendMessage("Randomly selected to lose " + toLose.getOwner() + "'s "+GameUtils.getCardLink(toLose));
                                action.insertEffect(new LoseCardFromTableEffect(action, allDroidsHere.getFirst(), true));
                            } else if(playerOneDroids.size()>1) {
                                action.setText("Have " + playerOne + " choose a Plagueis Probe Droid to be lost");
                                action.insertEffect(new ChooseCardToLoseFromTableEffect(action, playerOne, null,true, Filters.in(playerOneDroids), SpotOverride.INCLUDE_ALL));
                            } else if(playerTwoDroids.size()>1) {
                                action.setText("Have " + playerTwo + " choose a Plagueis Probe Droid to be lost");
                                action.insertEffect(new ChooseCardToLoseFromTableEffect(action, playerTwo, null,true, Filters.in(playerTwoDroids), SpotOverride.INCLUDE_ALL));
                            }

                        }
                    }
                });

                return Collections.singletonList(action);
            }
        }

        return null;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        final PhysicalCard selika = Filters.findFirstActive(game, self, Filters.Selika);
        final PhysicalCard mihoshi = Filters.findFirstActive(game, self, Filters.Mihoshi);

        if (selika!=null&&mihoshi!=null
                && GameConditions.isDuringYourPhase(game, self, Phase.CONTROL)
                && GameConditions.isPresentWith(game, self, mihoshi)) {

            final PhysicalCard selikasSite = Filters.findFirstFromTopLocationsOnTable(game, Filters.and(Filters.site,Filters.sameSite(selika),Filters.locationCanBeRelocatedTo(self,3)));
            final PhysicalCard mihoshisSite = Filters.findFirstFromTopLocationsOnTable(game, Filters.and(Filters.site,Filters.sameSite(mihoshi),Filters.locationCanBeRelocatedTo(selika,3)));

            if (mihoshisSite!=null&&selikasSite!=null
                    && GameConditions.canTarget(game, self, selika)
                    && GameConditions.canTarget(game, self, mihoshi)
                    && GameConditions.canTarget(game, self, mihoshisSite)
                    && GameConditions.canTarget(game, self, selikasSite)
            ) {

                final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId);

                action.appendCost(new PayRelocateBetweenLocationsCostEffect(action, playerId, self, selikasSite, 3));

                action.setText("Relocate Plagueis Probe Droid and Selika");
                action.setActionMsg("Relocate " + GameUtils.getCardLink(self) + " to " + GameUtils.getCardLink(selika) + "'s site and relocate "
                        + GameUtils.getCardLink(selika) + " to " + GameUtils.getCardLink(mihoshi) + "'s site");
                action.appendEffect(new PassthruEffect(action) {
                    @Override
                    protected void doPlayEffect(final SwccgGame game) {
                        //droid to Selika's site
                        action.appendEffect(new RelocateBetweenLocationsEffect(action, self, selikasSite));
                        //Selika to Mihoshi's site
                        action.appendEffect(new RelocateBetweenLocationsEffect(action, selika, mihoshisSite));
                    }
                });

                return Collections.singletonList(action);
            }
        }
        return null;
    }
}