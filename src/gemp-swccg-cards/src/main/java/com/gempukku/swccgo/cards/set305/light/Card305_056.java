package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.conditions.AtCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
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
import com.gempukku.swccgo.logic.effects.PutCardFromLostPileInUsedPileEffect;
import com.gempukku.swccgo.logic.effects.UseForceEffect;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.ForfeitModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Sorami Iziz
 */
public class Card305_056 extends AbstractAlien {
    public Card305_056() {
        super(Side.LIGHT, 3, 2, 1, 2, 2, "Sorami Iziz", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("A Nihilgenia clone serving the Keibatsu family. Sorami first served in Iziz, a wild city on Onderon full of prideful and wild citizens. Serving as musician she grew close to the local insurgents.");
        setGameText("While at a Quermia Senate site or same site as Mihoshi, opponent's [COU] characters are deploy +1 here and Sorami is forfeit +3. If just lost during a battle, may use 2 Force to place Sorami in Used Pile.");
        addIcons(Icon.ABT, Icon.COU);
        addKeywords(Keyword.FEMALE, Keyword.NIHILGENIA, Keyword.MUSICIAN);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition atQuermiaSiteOrSameSiteAsMihoshi = new AtCondition(self, Filters.or(Filters.Quermia_Senate_Site,
                Filters.sameSiteAs(self, Filters.Mihoshi)));

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, Filters.and(Filters.opponents(self), Filters.COU, Filters.character),
                atQuermiaSiteOrSameSiteAsMihoshi, 1, Filters.here(self)));
        modifiers.add(new ForfeitModifier(self, atQuermiaSiteOrSameSiteAsMihoshi, 3));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextLeavesTableOptionalTriggers(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.justLost(game, effectResult, self)
                && GameConditions.isDuringBattle(game)
                && GameConditions.canUseForce(game, playerId, 2)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Place in Used Pile");
            action.setActionMsg("Place " + GameUtils.getCardLink(self) + " in Used Pile");
            // Pay cost(s)
            action.appendCost(
                    new UseForceEffect(action, playerId, 2));
            // Perform result(s)
            action.appendEffect(
                    new PutCardFromLostPileInUsedPileEffect(action, playerId, self, true));
            return Collections.singletonList(action);
        }
        return null;
    }
}
