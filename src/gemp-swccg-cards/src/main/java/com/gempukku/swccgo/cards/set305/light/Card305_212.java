package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.cards.conditions.InBattleWithCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.CancelDestinyEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Rebel
 * Title: Kai Lap'lamiz
 */

public class Card305_212 extends AbstractRebel {
    public Card305_212() {
        super(Side.LIGHT, 1, 4, 4, 4, 5, "Kai Lap'lamiz", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Kai explored the galaxy after reaching adulthood. Seeking out the peaceful planets far from the Brotherhood's influence. He longs to reconcile with his siblings and the influence their father has on them.");
        setGameText("Adds 2 to any ship he pilots. Power is +2 if in a battle with a Lap'lamiz and may spend 2 Force to cancel a destiny draw.");
        addPersona(Persona.KAI);
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
        modifiers.add(new PowerModifier(self, new InBattleWithCondition(self, Filters.Laplamiz), 2));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        if (TriggerConditions.isDestinyJustDrawn(game, effectResult)
                && GameConditions.isInBattleWith(game, self, Filters.Laplamiz)
                && GameConditions.canUseForce(game, playerId, 2)
                && GameConditions.canCancelDestiny(game, playerId)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Cancel destiny draw");

            action.appendCost(
                    new UseForceEffect(action, playerId, 2));

            action.appendEffect(
                    new CancelDestinyEffect(action));

            return Collections.singletonList(action);
        }
        return null;
    }
}
