package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractNormalEffect;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.PlayCardZoneOption;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.GameUtils;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.actions.RequiredGameTextTriggerAction;
import com.gempukku.swccgo.logic.effects.CompleteJediTestEffect;
import com.gempukku.swccgo.logic.modifiers.MayNotBeTargetedByModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotPlayModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.ResetLandspeedModifier;
import com.gempukku.swccgo.logic.timing.EffectResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Effect
 * Title: New Ways to Motivate Them
 */
public class Card305_162 extends AbstractNormalEffect {
    public Card305_162() {
        super(Side.DARK, 5, PlayCardZoneOption.YOUR_SIDE_OF_TABLE, "New Ways to Motivate Them", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.U);
        setLore("'Perhaps I can find new ways to motivate them.'");
        setGameText("Deploy on table. If a Headmaster or Instructor is on Arx and Sith Test #3 is present with target apprentice, it is completed. Also, you may not play Counter Assault. Each apprentice on Arx is landspeed = 2 and may not be targeted by Swing-And-A-Miss. (Immune To Alter.)");
        addIcons(Icon.ABT);
        addImmuneToCardTitle(Title.Alter);
    }

    @Override
    protected List<RequiredGameTextTriggerAction> getGameTextRequiredAfterTriggers(SwccgGame game, EffectResult effectResult, PhysicalCard self, int gameTextSourceCardId) {
        // Check condition(s)
        if (TriggerConditions.isTableChanged(game, effectResult)) {
            PhysicalCard jediTest3 = Filters.findFirstActive(game, self, Filters.and(Filters.uncompleted_Jedi_Test, Filters.SITH_TEST_3));
            if (jediTest3 != null
                    && Filters.presentWith(self, Filters.apprenticeTargetedByJediTest(jediTest3)).accepts(game, jediTest3)
                    && GameConditions.canSpot(game, self, Filters.and(Filters.or(Filters.HEADMASTER, Filters.INSTRUCTOR), Filters.on(Title.Arx)))) {

                RequiredGameTextTriggerAction action = new RequiredGameTextTriggerAction(self, gameTextSourceCardId);
                action.setSingletonTrigger(true);
                action.setText("Complete " + GameUtils.getFullName(jediTest3));
                action.setActionMsg("Complete " + GameUtils.getCardLink(jediTest3));
                // Perform result(s)
                action.appendEffect(
                        new CompleteJediTestEffect(action, jediTest3));
                return Collections.singletonList(action);
            }
        }
        return null;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        String playerId = self.getOwner();
        Filter apprenticeOnArx = Filters.and(Filters.apprentice, Filters.on(Title.Arx));

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayNotPlayModifier(self, Filters.Counter_Assault, playerId));
        modifiers.add(new ResetLandspeedModifier(self, apprenticeOnArx, 2));
        modifiers.add(new MayNotBeTargetedByModifier(self, apprenticeOnArx, Filters.Swing_And_A_Miss));
        return modifiers;
    }
}