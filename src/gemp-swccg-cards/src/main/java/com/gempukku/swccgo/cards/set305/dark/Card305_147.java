package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerTurnEffect;
import com.gempukku.swccgo.cards.evaluators.PresentEvaluator;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.actions.TopLevelGameTextAction;
import com.gempukku.swccgo.logic.effects.CancelGameTextUntilEndOfTurnEffect;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.effects.TargetCardOnTableEffect;
import com.gempukku.swccgo.logic.effects.UnrespondableEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.modifiers.ArmorModifier;
import com.gempukku.swccgo.logic.modifiers.EachBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.Action;
import com.gempukku.swccgo.logic.timing.StandardEffect;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Slyth, Enraged
 */
public class Card305_147 extends AbstractAlien {
    public Card305_147() {
        super(Side.DARK, 3, 6, 9, 2, 6, "Slyth, Enraged", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.UR);
        setArmor(4);
        setLore("Often hired by Kamjin as a smuggler or bounty hunter. More often than not Slyth becomes enraged to discover that Kamjin has failed to pay. Only Lyn can calm him when he becomes enraged.");
        setGameText("X = number of Aliens present. While with Dag or Lyn, adds 2 to your battle destiny draws here. During your turn, target one Alien of power < 5 present. Target's game text canceled for remainder of turn. End of your turn: Use 2 Force to maintain OR Lose 2 Force to place in Used Pile OR Place out of play.");
        addPersona(Persona.SLYTH);
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR, Icon.MAINTENANCE);
        addKeywords(Keyword.SMUGGLER, Keyword.BOUNTY_HUNTER);
        setSpecies(Species.TRANDOSHAN);
        setMatchingStarshipFilter(Filters.Tatorutaimu);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ArmorModifier(self, new PresentEvaluator(self, Filters.alien)));
        modifiers.add(new EachBattleDestinyModifier(self, Filters.here(self), new WithCondition(self, Filters.or(Filters.Dag, Filters.Lyn)),
                2, self.getOwner()));
        return modifiers;
    }

    @Override
    protected List<TopLevelGameTextAction> getGameTextTopLevelActions(final String playerId, final SwccgGame game, final PhysicalCard self, int gameTextSourceCardId) {
        Filter targetFilter = Filters.and(Filters.alien, Filters.powerLessThan(5), Filters.present(self));

        // Check condition(s)
        if (GameConditions.isOnceDuringYourTurn(game, self, playerId, gameTextSourceCardId)
                && GameConditions.canTarget(game, self, targetFilter)) {

            final TopLevelGameTextAction action = new TopLevelGameTextAction(self, gameTextSourceCardId);
            action.setText("Cancel an Alien's game text");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerTurnEffect(action));
            // Choose target(s)
            action.appendTargeting(
                    new TargetCardOnTableEffect(action, playerId, "Choose Alien", targetFilter) {
                        @Override
                        protected void cardTargeted(int targetGroupId, final PhysicalCard targetedCard) {
                            action.addAnimationGroup(targetedCard);
                            // Allow response(s)
                            action.allowResponses("Cancel " + GameUtils.getCardLink(targetedCard) + "'s game text",
                                    new UnrespondableEffect(action) {
                                        @Override
                                        protected void performActionResults(Action targetingAction) {
                                            // Perform result(s)
                                            action.appendEffect(
                                                    new CancelGameTextUntilEndOfTurnEffect(action, targetedCard));
                                        }
                                    }
                            );
                        }
                    }
            );
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    protected StandardEffect getGameTextMaintenanceMaintainCost(Action action, final String playerId) {
        return new UseForceEffect(action, playerId, 2);
    }

    @Override
    protected StandardEffect getGameTextMaintenanceRecycleCost(Action action, final String playerId) {
        return new LoseForceEffect(action, playerId, 2, true);
    }
}
