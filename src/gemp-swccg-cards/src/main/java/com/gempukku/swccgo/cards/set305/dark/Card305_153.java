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
import com.gempukku.swccgo.common.Title;
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
 * Title: Hibbity Jibbity With Blaster Rifle
 */

public class Card305_153 extends AbstractAlien {
    public Card305_153() {
        super(Side.DARK, 1, 4, 4, 3, 6, "Hibbity Jibbity With Blaster Rifle", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("Smuggler, Mercenary, Assassin and a soft heart. His personal mantra is 'Anything for a credit.'");
        setGameText("Adds 3 to power of anything he pilots. Adds one battle destiny if with Dag or Slyth. Permanent weapon is •Hibbity's Blaster Rifle (May target a character for free; if targeting a Jedi, draw two destiny, otherwise draw destiny; target hit, and its forfeit = 0, if total destiny +1 > defense value).");
        addPersona(Persona.HIBBITY);
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR, Icon.PERMANENT_WEAPON);
        addKeywords(Keyword.SMUGGLER, Keyword.ASSASSIN, Keyword.MERCENARY);
        setSpecies(Species.MON_CALAMARI);
        setMatchingStarshipFilter(Filters.Tatorutaimu);
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<Modifier>();
        modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 3));
        modifiers.add(new AddsBattleDestinyModifier(self, new WithCondition(self, Filters.or(Filters.Dag, Filters.Slyth)), 1));
        return modifiers;
    }

    // Define "Hibbity's Blaster Rifle" permanent weapon
    @Override
    protected AbstractPermanentWeapon getGameTextPermanentWeapon() {
        AbstractPermanentWeapon permanentWeapon = new AbstractPermanentWeapon(Persona.HIBBITYS_BLASTER_RFILE) {
            @Override
            public List<FireWeaponAction> getGameTextFireWeaponActions(String playerId, SwccgGame game, PhysicalCard self, boolean forFree, int extraForceRequired, PhysicalCard sourceCard, boolean repeatedFiring, Filter targetedAsCharacter, Float defenseValueAsCharacter, Filter fireAtTargetFilter, boolean ignorePerAttackOrBattleLimit) {
                FireWeaponActionBuilder actionBuilder = FireWeaponActionBuilder.startBuildPrep(playerId, game, sourceCard, self, this, forFree, extraForceRequired, repeatedFiring, targetedAsCharacter, defenseValueAsCharacter, fireAtTargetFilter, ignorePerAttackOrBattleLimit)
                        .targetForFree(Filters.or(Filters.character, targetedAsCharacter), TargetingReason.TO_BE_HIT).finishBuildPrep();
                if (actionBuilder != null) {

                    // Build action using common utility
                    FireWeaponAction action = actionBuilder.buildFireWeaponPermanentAurraSingsBlasterRifleAction();
                    return Collections.singletonList(action);
                }

                return null;
            }
        };
        permanentWeapon.addKeyword(Keyword.BLASTER_RIFLE);
        return permanentWeapon;
    }
}


