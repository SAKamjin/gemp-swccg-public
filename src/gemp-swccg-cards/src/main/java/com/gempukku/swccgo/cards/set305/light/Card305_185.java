package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.cards.effects.usage.OncePerBattleEffect;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.choose.TakeDestinyCardIntoHandEffect;
import com.gempukku.swccgo.logic.modifiers.DeployCostToLocationModifier;
import com.gempukku.swccgo.logic.modifiers.ForfeitModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.timing.results.DestinyDrawnResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Quermian General
 */
public class Card305_185 extends AbstractAlien {
    public Card305_185() {
        super(Side.LIGHT, 2, 2, 2, 2, 4, "Quermian General", Uniqueness.RESTRICTED_3, ExpansionSet.ABT, Rarity.C);
        setLore("The Quermian army has served more as a means of physicial discipline and academic study for generations. It wasn't until the Vauzem Dominion invaded that the army was tested. Leader.");
        setGameText("Deploys -1 to Quermia. Your Quermians at same site are forfeit +2. While in a battle, if you just drew a Quermian for battle destiny you may take that card into hand.");
        addIcons(Icon.ABT, Icon.WARRIOR);
        addKeywords(Keyword.GENERAL, Keyword.LEADER);
        setSpecies(Species.QUERMIAN);
    }

    @Override
    protected List<Modifier> getGameTextAlwaysOnModifiers(SwccgGame game, PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new DeployCostToLocationModifier(self, -1, Filters.Deploys_at_Quermia));
        return modifiers;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new ForfeitModifier(self, Filters.and(Filters.your(self), Filters.Quermian, Filters.atSameSite(self)), 2));
        return modifiers;
    }

    @Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.isBattleDestinyJustDrawnBy(game, effectResult, playerId)
                && GameConditions.isInBattle(game, self)
                && GameConditions.isOncePerBattle(game, self, playerId, gameTextSourceCardId)
                && GameConditions.isDestinyCardMatchTo(game, Filters.Quermian)
                && GameConditions.canTakeDestinyCardIntoHand(game, playerId)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId);
            action.setText("Take destiny card into hand");
            action.setActionMsg("Take just drawn destiny card, " + GameUtils.getCardLink(((DestinyDrawnResult) effectResult).getCard()) + ", into hand");
            // Update usage limit(s)
            action.appendUsage(
                    new OncePerBattleEffect(action));
            // Perform result(s)
            action.appendEffect(
                    new TakeDestinyCardIntoHandEffect(action));
            return Collections.singletonList(action);
        }
        return null;
    }
}
