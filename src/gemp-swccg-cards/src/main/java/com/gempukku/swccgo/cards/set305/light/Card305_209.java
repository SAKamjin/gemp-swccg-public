package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractCapitalStarship;
import com.gempukku.swccgo.cards.AbstractPermanentAboard;
import com.gempukku.swccgo.cards.AbstractPermanentPilot;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.SubtractFromOpponentsTotalPowerAndAttritionEffect;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.game.state.BattleState;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.DrawDestinyEffect;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Capital
 * Title: Resurgent
 */
public class Card305_209 extends AbstractCapitalStarship {
    public Card305_209() {
        super(Side.LIGHT, 1, 8, 8, 6, null, 3, 9, "Resurgent", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Serving alongside the Sunrider and Peacekeeper, the Resurgent serves as the O.E.F. Navy's core might. Far more modern than the other two ships, the Resurgent emphasizes engaging capital ships.");
        setGameText("May add 6 pilots, 8 passengers, 2 vehicles and 4 starfighters. Has ship-docking capability. Permanent pilot aboard provides ability of 2. If in battle with another [COU] capital starship, may draw destiny. Subtract that amount from opponent's attrition and total power.");
        addIcons(Icon.ABT, Icon.COU, Icon.PILOT, Icon.NAV_COMPUTER, Icon.SCOMP_LINK);
        addModelType(ModelType.NEBULA_CLASS_STAR_DESTROYER);
        setPilotCapacity(6);
        setPassengerCapacity(8);
        setVehicleCapacity(2);
        setStarfighterCapacity(4);
    }

    @Override
    protected List<? extends AbstractPermanentAboard> getGameTextPermanentsAboard() {
        return Collections.singletonList(new AbstractPermanentPilot(2) {});
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, final EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.isInitialAttritionJustCalculated(game, effectResult)
                && GameConditions.isInBattleWith(game, self, Filters.COU_capital_starship)
                && GameConditions.canDrawDestiny(game, playerId)) {
            final BattleState battleState = game.getGameState().getBattleState();
            if (battleState.hasAttritionTotal(game.getOpponent(playerId))) {

                final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
                action.setText("Reduce opponent's attrition and total power");
                // Perform result(s)
                action.appendEffect(
                        new DrawDestinyEffect(action, playerId, 1, DestinyType.DESTINY_TO_REDUCE_ATTRITION_POWER) {
                            @Override
                            protected void destinyDraws(SwccgGame game, List<PhysicalCard> destinyCardDraws, List<Float> destinyDrawValues, Float totalDestiny) {
                                if (totalDestiny != null && totalDestiny > 0) {
                                    action.appendEffect(
                                            new SubtractFromOpponentsTotalPowerAndAttritionEffect(action, totalDestiny));
                                }
                            }
                        });
                return Collections.singletonList(action);
            }
        }
        return null;
    }
}
