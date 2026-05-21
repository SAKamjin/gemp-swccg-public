package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractNormalEffect;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.cards.effects.usage.OncePerBattleEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.PlayCardZoneOption;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.conditions.OrCondition;
import com.gempukku.swccgo.logic.effects.LoseForceEffect;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Effect
 * Title: Quermia Celebration
 */
public class Card305_085 extends AbstractNormalEffect {
    public Card305_085() {
        super(Side.LIGHT, 5, PlayCardZoneOption.YOUR_SIDE_OF_TABLE, "Quermia Celebration", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("The heroic actions of Mihoshi's Force Sensitive companions and the Odan-Urr' army saved Quermia from the Vauzem occupation. Definitely a cause for celebration!");
        setGameText("Deploy on table. If Mihoshi at a Quermia Senate site (or Vaelor Quis at an exterior Quermia site), opponent's cards with ability deploy +2 there. Once during battle at a Quermia site, if you just drew a Quermian or [COU] Summit Guard for battle destiny, opponent loses 1 Force.");
        addIcons(Icon.ABT);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition condition = new OrCondition(new AtCondition(self, Filters.Mihoshi, Filters.Quermia_Senate_Site),
                new AtCondition(self, Filters.Vaelor_Quis, Filters.exterior_Quermia_site));
        Filter sameSiteAsAmidala = Filters.and(Filters.Quermia_Senate_Site, Filters.sameSiteAs(self, Filters.Mihoshi));
        Filter sameSiteAsBossNass = Filters.and(Filters.exterior_Quermia_site, Filters.sameSiteAs(self, Filters.Vaelor_Quis));

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, Filters.and(Filters.opponents(self), Filters.hasAbilityOrHasPermanentPilotWithAbility),
                condition, 2, Filters.or(sameSiteAsAmidala, sameSiteAsBossNass)));
        return modifiers;
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        String playerId = self.getOwner();
        String opponent = game.getOpponent(playerId);

        // Check condition(s)
        if (TriggerConditions.isBattleDestinyJustDrawnBy(game, effectResult, playerId)
                && GameConditions.isDestinyCardMatchTo(game, Filters.or(Filters.Quermian, Filters.COU_SUMMIT_GUARD))
                && GameConditions.isDuringBattleAt(game, Filters.Quermia_site)
                && GameConditions.isOncePerBattle(game, self, gameTextSourceCardId)) {

            final RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Make " + opponent + " lose 1 Force");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerBattleEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new LoseForceEffect(action, opponent, 1));
            return Collections.singletonList(action);
        }
        return null;
    }
}