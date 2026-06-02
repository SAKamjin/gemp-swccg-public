package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractPermanentAboard;
import com.gempukku.swccgo.cards.AbstractPermanentPilot;
import com.gempukku.swccgo.cards.AbstractStarfighter;
import com.gempukku.swccgo.cards.conditions.AtSameSystemAsCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.ModelType;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.DeployCostForSimultaneouslyDeployingPilotModifier;
import com.gempukku.swccgo.logic.modifiers.DeployCostToTargetModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Starship
 * Subtype: Starfighter
 * Title: VDS-T813
 */
public class Card305_119 extends AbstractStarfighter {
    public Card305_119() {
        super(Side.DARK, 3, 4, 3, null, 4, null, 4, "VDS-T813", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Droid starfighter programmed for battleship defense. A corruption in his droid core causes him to pursue enemy starfighters without mercy. Vauzem Dominion engineers cannot find out why.");
        setGameText("While at same system as your battleship, VDS-T813 is power +3, and opponent's pilots deploy +2 to starfighters at this system.");
        addIcons(Icon.ABT, Icon.VAUZEM, Icon.PILOT, Icon.PRESENCE);
        addKeywords(Keyword.DFS_SQUADRON, Keyword.NO_HYPERDRIVE);
        addModelType(ModelType.DROID_STARFIGHTER);
    }

    @Override
    protected List<? extends AbstractPermanentAboard> getGameTextPermanentsAboard() {
        return Collections.singletonList(new AbstractPermanentPilot() {});
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition atSameSystemAsYourBattleship = new AtSameSystemAsCondition(self, Filters.and(Filters.your(self), Filters.battleship));
        Filter opponentsPilots = Filters.and(Filters.opponents(self), Filters.pilot);

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new PowerModifier(self, atSameSystemAsYourBattleship, 3));
        modifiers.add(new DeployCostToTargetModifier(self, opponentsPilots, atSameSystemAsYourBattleship, 2, Filters.and(Filters.starfighter, Filters.atSameSystem(self))));
        modifiers.add(new DeployCostForSimultaneouslyDeployingPilotModifier(self, opponentsPilots, atSameSystemAsYourBattleship,2, Filters.starfighter, Filters.here(self)));
        return modifiers;
    }
}
