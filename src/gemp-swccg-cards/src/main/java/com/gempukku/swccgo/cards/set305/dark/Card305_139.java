package com.gempukku.swccgo.cards.set305.dark;

import com.gempukku.swccgo.cards.AbstractSith;
import com.gempukku.swccgo.cards.conditions.PresentAtCondition;
import com.gempukku.swccgo.cards.GameConditions;
import com.gempukku.swccgo.common.*;
import com.gempukku.swccgo.filters.Filter;
import com.gempukku.swccgo.filters.Filters;
import com.gempukku.swccgo.game.PhysicalCard;
import com.gempukku.swccgo.game.SwccgGame;
import com.gempukku.swccgo.logic.modifiers.DeploysFreeToTargetModifier;
import com.gempukku.swccgo.logic.modifiers.FiresForFreeModifier;
import com.gempukku.swccgo.logic.modifiers.AddsPowerToPilotedBySelfModifier;
import com.gempukku.swccgo.logic.modifiers.Modifier;
import com.gempukku.swccgo.logic.timing.EffectResult;
import com.gempukku.swccgo.logic.effects.choose.DeployCardToTargetFromReserveDeckEffect;
import com.gempukku.swccgo.logic.actions.OptionalGameTextTriggerAction;
import com.gempukku.swccgo.logic.TriggerConditions;
import com.gempukku.swccgo.logic.modifiers.ImmuneToAttritionLessThanModifier;
import com.gempukku.swccgo.logic.modifiers.MayBeReplacedByOpponentModifier;
import com.gempukku.swccgo.logic.modifiers.MayUseWeaponModifier;
import com.gempukku.swccgo.logic.modifiers.MayDeployToTargetModifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Set: A Better Tomorrow
 * Type: Character
 * SubType: Sith
 * Title: Komilia Lap'lamiz, Sith Acolyte
 */
public class Card305_139 extends AbstractSith {
    public Card305_139() {
        super(Side.DARK, 1, 5, 5, 5, 8, "Komilia Lap'lamiz, Sith Acolyte", Uniqueness.UNIQUE, ExpansionSet.ABT, Rarity.R);
        setLore("After rejoining her father, Komilia devoted herself to the Dark Side of the Force. If she completes her training she'll be a threat more powerful than the galaxy has seen before.");
        setGameText("Adds 2 to anything she pilots. Deploys only to Arx. When deployed, may ▼ a weapon on Komilia. Blasters deploy and fire for free on Komilia. May be targeted by Thermal Detonator. Immune to attrition < 5.");
        addPersona(Persona.KOMILIA);
		addKeywords(Keyword.FEMALE);
        addIcons(Icon.ABT, Icon.PILOT, Icon.WARRIOR, Icon.CSP);
    }

    @Override
    protected Filter getGameTextValidDeployTargetFilter(SwccgGame game, PhysicalCard self, PlayCardOptionId playCardOptionId, boolean asReact) {
        return Filters.Deploys_at_Arx;
    }

    @Override
    protected List<Modifier> getGameTextWhileActiveInPlayModifiers(SwccgGame game, final PhysicalCard self) {
        List<Modifier> modifiers = new LinkedList<>();
		modifiers.add(new AddsPowerToPilotedBySelfModifier(self, 2));
		modifiers.add(new DeploysFreeToTargetModifier(self, Filters.blaster, Filters.persona(Persona.KOMILIA)));
        modifiers.add(new FiresForFreeModifier(self, Filters.and(Filters.blaster, Filters.attachedTo(Filters.persona(Persona.KOMILIA)))));
        modifiers.add(new ImmuneToAttritionLessThanModifier(self, 5));modifiers.add(new MayBeReplacedByOpponentModifier(self, new PresentAtCondition(self, Filters.site)));
		modifiers.add(new MayUseWeaponModifier(self, Filters.Thermal_Detonator));
		modifiers.add(new MayDeployToTargetModifier(self, Filters.and(Filters.your(self), Filters.Thermal_Detonator), self));
        return modifiers;
    }
	
	@Override
    protected List<OptionalGameTextTriggerAction> getGameTextOptionalAfterTriggers(final String playerId, SwccgGame game, EffectResult effectResult, final PhysicalCard self, int gameTextSourceCardId) {
        GameTextActionId gameTextActionId = GameTextActionId.KOMILIA__DOWNLOAD_WEAPON;

        // Check condition(s)
        if (TriggerConditions.justDeployed(game, effectResult, self)
                && GameConditions.canDeployCardFromReserveDeck(game, playerId, self, gameTextActionId, true, false)) {

            final OptionalGameTextTriggerAction action = new OptionalGameTextTriggerAction(self, gameTextSourceCardId, gameTextActionId);
            action.setText("Deploy weapon from Reserve Deck");
            action.setActionMsg("Deploy a weapon on Komilia from Reserve Deck");

            // Perform result(s)
            action.appendEffect(
                    new DeployCardToTargetFromReserveDeckEffect(action, Filters.weapon, Filters.sameCardId(self), true));
            return Collections.singletonList(action);
        }
        return null;
    }
}
