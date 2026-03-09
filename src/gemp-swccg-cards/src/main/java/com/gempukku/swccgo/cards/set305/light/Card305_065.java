package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.InBattleWithCondition;
import com.gempukku.swccgo.cards.effects.SatisfyAllBattleDamageAndAttritionEffect;
import com.gempukku.swccgo.cards.effects.SatisfyAllBattleDamageEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.ForfeitCardFromTableEffect;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotBeTargetedByWeaponsModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Sergeant Keira Thalune
 */

public class Card305_065 extends AbstractAlien {
    public Card305_065() {
        super(Side.LIGHT, 2, 3, 4, 2, 3, "Sergeant Keira Thalune", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("Keira enlisted after her family’s transport business was wiped out by pirates. She’s fiery, clever, and has a knack for spotting trouble seconds before it starts. ");
        setGameText("Deploys -2 to same site as Mihoshi. While in a battle with Mihoshi or Alethia Archenksova, your leaders present may not be targeted by weapons, and Keira may be forfeited to satisfy all battle damage and attrition against you.");
        addIcons(Icon.COU, Icon.ABT, Icon.WARRIOR);
        addKeywords(Keyword.COU_SUMMIT_GUARD);
        addPersona(Persona.KEIRA_THALUNE);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, -2, Filters.sameSiteAs(self, Filters.Mihoshi)));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayNotBeTargetedByWeaponsModifier(self, Filters.and(Filters.your(self), Filters.leader, Filters.present(self)),
                new InBattleWithCondition(self, Filters.or(Filters.Mihoshi, Filters.ALETHIA))));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, final EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.isResolvingBattleDamageAndAttrition(game, effectResult, playerId)
                && GameConditions.canForfeitToSatisfyAttritionAndBattleDamage(game, playerId, self)
                && Filters.Keira_Thalune.accepts(game, self)
                && GameConditions.isInBattleWith(game, self, Filters.or(Filters.Mihoshi, Filters.ALETHIA))) {
            boolean cannotSatisfyAttrition = game.getModifiersQuerying().cannotSatisfyAttrition(game.getGameState(), self);

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
            if (cannotSatisfyAttrition)
                action.setText("Forfeit to satisfy all battle damage");
            else
                action.setText("Forfeit to satisfy all battle damage and attrition");
            // Pay cost(s)
            action.appendCost(
                    new ForfeitCardFromTableEffect(action, self));
            action.setActionMsg(null);
            // Perform result(s)
            if (cannotSatisfyAttrition)
                action.appendEffect(
                        new SatisfyAllBattleDamageEffect(action, playerId));
            else
                action.appendEffect(
                        new SatisfyAllBattleDamageAndAttritionEffect(action, playerId));
            return Collections.singletonList(action);
        }
        return null;
    }
}
