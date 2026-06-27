package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractAlien;
import com.gempukku.swccgo.cards.AbstractPermanentWeapon;
import com.gempukku.swccgo.cards.AbstractRebel;
import com.gempukku.swccgo.cards.conditions.WithCondition;
import com.gempukku.swccgo.common.ExpansionSet;
import com.gempukku.swccgo.common.Icon;
import com.gempukku.swccgo.common.Keyword;
import com.gempukku.swccgo.common.Persona;
import com.gempukku.swccgo.common.Rarity;
import com.gempukku.swccgo.common.Side;
import com.gempukku.swccgo.common.Species;
import com.gempukku.swccgo.common.Statistic;
import com.gempukku.swccgo.common.TargetingReason;
import com.gempukku.swccgo.common.Uniqueness;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.actions.FireWeaponAction;
import com.gempukku.swccgo.logic.actions.FireWeaponActionBuilder;
import com.gempukku.swccgo.logic.modifiers.AddsBattleDestinyModifier;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Set: A Better Tomorrow
 * Type: Character
 * Subtype: Alien
 * Title: Lyn With Blaster Rifle
 */

public class Card305_149 extends AbstractAlien {
    public Card305_149() {
        super(Side.DARK, 1, 4, 3, 3, 6, "Lyn With Blaster Rifle", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Lyn continues her life as a mercenary working for Dag Duh Dug's crew. She's known to shoot first.");
        setGameText("Adds 1 to power of anything she pilots. Adds one battle destiny if with Slyth. Permanent weapon is •Lyn's Blaster Rifle (may target a character or creature for free; draw destiny; target hit, and its forfeit = 0, if destiny +1 > defense value).");
        addPersona(Persona.LYN);
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR, Icon.PERMANENT_WEAPON);
        addKeywords(Keyword.MERCENARY, Keyword.FEMALE);
        setSpecies(Species.TWILEK);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 1));
        modifiers.add(new AddsBattleDestinyModifier(self, new WithCondition(self, Filters.Slyth), 1));
        return modifiers;
    }

    // Define "Leia's Blaster Rifle" permanent weapon
    @Override
    protected AbstractPermanentWeapon getGameTextPermanentWeapon() {
        AbstractPermanentWeapon permanentWeapon = new AbstractPermanentWeapon(Persona.LYNS_BLASTER_RIFLE) {
            @Override
            public List<FireWeaponAction> getGameTextFireWeaponActions(String playerId, SwccgGame game, PhysicalCard self, boolean forFree, int extraForceRequired, PhysicalCard sourceCard, boolean repeatedFiring, Filter targetedAsCharacter, Float defenseValueAsCharacter, Filter fireAtTargetFilter, boolean ignorePerAttackOrBattleLimit) {
                FireWeaponActionBuilder actionBuilder = FireWeaponActionBuilder.startBuildPrep(playerId, game, sourceCard, self, this, forFree, extraForceRequired, repeatedFiring, targetedAsCharacter, defenseValueAsCharacter, fireAtTargetFilter, ignorePerAttackOrBattleLimit)
                        .targetForFree(Filters.or(Filters.character, targetedAsCharacter, Filters.creature), TargetingReason.TO_BE_HIT).finishBuildPrep();
                if (actionBuilder != null) {

                    // Build action using common utility
                    FireWeaponAction action = actionBuilder.buildFireWeaponWithHitAction(1, 1, Statistic.DEFENSE_VALUE, true, 0);
                    return Collections.singletonList(action);
                }
                return null;
            }
        };
        permanentWeapon.addKeyword(Keyword.BLASTER_RIFLE);
        return permanentWeapon;
    }
}


