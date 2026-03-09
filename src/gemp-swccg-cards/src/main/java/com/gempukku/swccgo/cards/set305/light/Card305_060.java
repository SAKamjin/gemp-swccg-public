package com.gempukku.swccgo.cards.set305.light;

import com.gempukku.swccgo.cards.AbstractCharacterWeapon;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.PlayCardOptionId;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Statistic;
import com.gempukku.swccgo.common.TargetingReason;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.FireWeaponAction;
import com.gempukku.swccgo.logic.actions.FireWeaponActionBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Weapon
 * Subtype: Character
 * Title: Jon Silvon Beskar Spear
 */
public class Card305_060 extends AbstractCharacterWeapon {
    public Card305_060() {
        super(Side.LIGHT, 6, "Jon Silvon Beskar Spear", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.C);
        setLore("Tarpals' electropole is nearly as old as he is. Used to enforce the laws of Otoh Gunga.");
        setGameText("Deploys on Jon Silvon. May 'throw' (place in Used Pile) to target a character, creature or vehicle for free. Draw destiny. If destiny +3 > defense value, target is landspeed = 0 and power -3 for remainder of turn.");
        addIcons(Icon.ABT);
        setMatchingCharacterFilter(Filters.JONSILVON);
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.and(Filters.your(self), Filters.JONSILVON);
    }

    @Override
    protected Filter getGameTextValidToUseWeaponFilter(final SwccgGame game, final PhysicalCard self) {
        return Filters.JONSILVON;
    }

    @Override
    protected List<FireWeaponAction> getGameTextFireWeaponActions(String playerId, final SwccgGame game, final PhysicalCard self, boolean forFree, int extraForceRequired, PhysicalCard sourceCard, boolean repeatedFiring, Filter targetedAsCharacter, Float defenseValueAsCharacter, Filter fireAtTargetFilter, boolean ignorePerAttackOrBattleLimit) {
        FireWeaponActionBuilder actionBuilder = FireWeaponActionBuilder.startBuildPrep(playerId, game, sourceCard, self, forFree, extraForceRequired, repeatedFiring, targetedAsCharacter, defenseValueAsCharacter, fireAtTargetFilter, ignorePerAttackOrBattleLimit)
                .targetForFree(Filters.or(Filters.character, targetedAsCharacter, Filters.creature, Filters.vehicle), TargetingReason.OTHER).finishBuildPrep();
        if (actionBuilder != null) {

            // Build action using common utility
            FireWeaponAction action = actionBuilder.buildFireWeaponElectropoleAction(true, 1, 3, Statistic.DEFENSE_VALUE, true, -3);
            return Collections.singletonList(action);
        }
        return null;
    }
}
