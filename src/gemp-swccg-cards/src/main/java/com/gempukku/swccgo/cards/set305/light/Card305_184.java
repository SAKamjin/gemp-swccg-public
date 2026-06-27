package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.conditions.DefendingBattleCondition;
import com.gempukku.swccgo.cards.conditions.OnCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Title;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.conditions.AndCondition;
import com.gempukku.swccgo.logic.conditions.Condition;
import com.gempukku.swccgo.logic.modifiers.DestinyWhenDrawnForBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.MayNotBeTargetedByWeaponsModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.modifiers.MoveCostUsingLandspeedModifier;
import com.gempukku.swccgo.logic.modifiers.PowerModifier;

import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Quermian Guard
 */
public class Card305_184 extends AbstractAlien {
    public Card305_184() {
        super(Side.LIGHT, 2, 2, 0, 1, 2, "Quermian Guard", Uniqueness.UNRESTRICTED, ExpansionSet.ABT, Rarity.C);
        setLore("While their order originated in combat the Quermian's guards have become more ceremonial in nature as their society progressed towards being diplomats and teachers.");
        setGameText("Your Quermian leaders present may not be targeted by weapons. While defending a battle on Quermia, Power +4 and, whenever you draw a Quermian for battle destiny, add 3 to that destiny. Requires +2 Force to use landspeed.");
        addIcons(Icon.ABT, Icon.WARRIOR);
        setSpecies(Species.QUERMIAN);
        addKeyword(Keyword.GUARD);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        Condition defendingOnQuermiaCondition = new AndCondition(new DefendingBattleCondition(self), new OnCondition(self, Title.Quermia));

        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new MayNotBeTargetedByWeaponsModifier(self, Filters.and(Filters.your(self), Filters.Quermian, Filters.leader,
                Filters.present(self))));
        modifiers.add(new PowerModifier(self, defendingOnQuermiaCondition, 4));
        modifiers.add(new DestinyWhenDrawnForBattleDestinyModifier(self, Filters.and(Filters.your(self), Filters.Quermian),
                defendingOnQuermiaCondition, 3));
        modifiers.add(new MoveCostUsingLandspeedModifier(self, 2));
        return modifiers;
    }
}
