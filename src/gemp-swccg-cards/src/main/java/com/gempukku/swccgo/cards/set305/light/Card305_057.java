package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtSameSiteAsCondition;
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
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.effects.choose.TakeCardIntoHandFromLostPileEffect;
import com.gempukku.swccgo.logic.modifiers.DefenseValueModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Nozomi Keldabe
 */
public class Card305_057 extends AbstractAlien {
    public Card305_057() {
        super(Side.LIGHT, 3, 2, 1, 2, 2, "Nozomi Keldabe", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setArmor(3);
        setLore("A Nihilgenia clone serving the Keibatsu family. Nozomi first served in Keldabe on Mandalore. While there she earned the right to wear Mandalorian armor though she did not take the creed.");
        setGameText("While at same site as Mihoshi, Nozomi is power +2 and Mihoshi is defense value +2. If just lost during a battle, may use 2 Force to take Nozomi into hand.");
        addIcons(Icon.ABT, Icon.COU, Icon.WARRIOR);
        addKeywords(Keyword.FEMALE, Keyword.NIHILGENIA);
        addPersona(Persona.NOZOMI);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition atSameSiteAsMihoshi = new AtSameSiteAsCondition(self, Filters.Mihoshi);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, Filters.NOZOMI, atSameSiteAsMihoshi, 2));
        modifiers.add(new DefenseValueModifier(self, Filters.Mihoshi, atSameSiteAsMihoshi, 2));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextLeavesTableOptionalTriggers(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.justLost(game, effectResult, self)
                && GameConditions.isDuringBattle(game)
                && GameConditions.canUseForce(game, playerId, 2)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Take into hand");
            action.setActionMsg("Take " + GameUtils.getCardLink(self) + " into hand");
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerId, 2));
            // Perform result(s)
            action.appendEffect(
                    new TakeCardIntoHandFromLostPileEffect(action, playerId, self, false, true));
            return Collections.singletonList(action);
        }
        return null;
    }
}
