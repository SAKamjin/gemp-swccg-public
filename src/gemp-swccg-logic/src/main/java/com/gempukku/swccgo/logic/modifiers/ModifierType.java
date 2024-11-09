package com.gempukku.swccgo.logic.modifiers;

// This enum represents the types of modifiers
// in Gemp-Swccg.
//
public enum ModifierType {

    // Game flags
    SPECIAL_FLAG,

    // Starting hand
    NUM_CARDS_DRAWN_IN_STARTING_HAND,

    // Card stats
    DESTINY, DESTINY_INCREASE_MODIFIER_LIMIT, PRINTED_DESTINY, UNMODIFIABLE_DESTINY,
    POWER, POWER_INCREASE_MODIFIER_LIMIT, PRINTED_POWER, UNMODIFIABLE_POWER, MAY_NOT_HAVE_POWER_REDUCED, MAY_NOT_HAVE_POWER_INCREASED_BY_CARD,
    ABILITY, UNMODIFIABLE_ABILITY, PRINTED_ABILITY, USE_SPECIFIC_ABILITY_VS_CARD,
    MANEUVER, UNMODIFIABLE_MANEUVER,
    ARMOR, UNMODIFIABLE_ARMOR, PRINTED_ARMOR,
    DEFENSE_VALUE, UNMODIFIABLE_DEFENSE_VALUE, PRINTED_DEFENSE_VALUE, MIN_DEFENSE_VALUE_REDUCED_TO, MAY_NOT_HAVE_DEFENSE_VALUE_REDUCED, MAY_NOT_HAVE_DEFENSE_VALUE_INCREASED_ABOVE_PRINTED, MAX_DEFENSE_VALUE_MODIFIED_TO,
    LANDSPEED, PRINTED_LANDSPEED, UNMODIFIABLE_LANDSPEED, MAY_NOT_HAVE_LANDSPEED_INCREASED,
    HYPERSPEED, UNMODIFIABLE_HYPERSPEED, HYPERSPEED_WHEN_MOVING_FROM_LOCATION, HYPERSPEED_WHEN_MOVING_TO_LOCATION,
    FORFEIT_VALUE, FORFEIT_INCREASE_MODIFIER_LIMIT, PRINTED_FORFEIT_VALUE, UNMODIFIABLE_FORFEIT_VALUE, MAY_NOT_HAVE_FORFEIT_VALUE_REDUCED, REMAINS_IN_PLAY_WHEN_FORFEITED, FORFEIT_VALUE_TO_USE, MAY_NOT_HAVE_FORFEIT_VALUE_INCREASED, MAY_NOT_HAVE_FORFEIT_VALUE_INCREASED_ABOVE_PRINTED,
    FEROCITY, PRINTED_FEROCITY, UNMODIFIABLE_FEROCITY,
    POLITICS, UNMODIFIABLE_POLITICS, PRINTED_MANEUVER,
    USE_POLITICS_FOR_POWER, USE_CALCULATION_FOR_DEPLOY_COST,

    // Keywords, Icons, Skills, Species
    GIVE_KEYWORD,REMOVE_KEYWORD,
    GIVE_ICON, CANCEL_ICONS, EQUALIZE_FORCE_ICONS, CANCEL_FORCE_ICON, CANCEL_FORCE_ICONS, MAY_NOT_ADD_ICON,
    GIVE_SPECIES,
    CANT_STEAL,
    CANT_DRIVE_OR_PILOT,
    DOES_NOT_ADD_TO_POWER_WHEN_PILOTING,
    MAY_NOT_PILOT_TARGET,
    ADD_CARD_TYPE,
    MODIFY_CARD_SUBTYPE,

    // Agenda
    GIVE_AGENDA,

    // Force generation
    UNMODIFIABLE_FORCE_GENERATION_AT_LOCATION,
    FORCE_GENERATION_AT_LOCATION,
    LIMIT_FORCE_GENERATION_AT_LOCATION,
    TOTAL_FORCE_GENERATION,
    FORCE_GENERATION_AT_LOCATION_IMMUNE_TO,
    FORCE_GENERATION_AT_LOCATION_IMMUNE_TO_CANCEL,
    PERSONAL_FORCE_GENERATION,

    // Totals at location
    TOTAL_POWER_AT_LOCATION, TOTAL_ABILITY_AT_LOCATION, UNMODIFIABLE_TOTAL_ABILITY_AT_LOCATION, MAY_NOT_HAVE_TOTAL_ABILITY_AT_LOCATION_REDUCED,

    // Exist
    MAY_NOT_EXIST_AT_TARGET,

    // Deployment
    DEPLOYS_FREE,
    DEPLOYS_FREE_TO_TARGET,
    DEPLOY_COST, PRINTED_DEPLOY_COST, DEPLOY_COST_USING_DEJARIK_RULES,
    MAX_AMOUNT_TO_REDUCE_DEPLOY_COST_BY,
    MAY_NOT_HAVE_DEPLOY_COST_MODIFIED, MAY_NOT_HAVE_DEPLOY_COST_INCREASED,
    UNMODIFIABLE_DEPLOY_COST,
    UNMODIFIABLE_DEPLOY_COST_TO_TARGET,
    PRINTED_DEPLOY_COST_TO_TARGET,
    SELF_DEPLOY_COST_TO_TARGET,
    DEPLOY_COST_TO_TARGET, UNMODIFIABLE_SIMULTANEOUS_PILOT_DEPLOY_COST,
    DEPLOY_COST_WITH_PILOT, SIMULTANEOUS_PILOT_DEPLOY_COST, SIMULTANEOUS_PILOT_DEPLOYS_FOR_FREE,
    EXTRA_FORCE_COST_TO_DEPLOY_TO_TARGET,
    EXTRA_FORCE_COST_TO_DEPLOY_FOR_FREE_EXCEPT_BY_OWN_GAME_TEXT,
    MAY_DEPLOY_WITHOUT_PRESENCE_OR_FORCE_ICONS, MAY_DEPLOY_PILOT_SIMULTANEOUSLY_WITHOUT_PRESENCE_OR_FORCE_ICONS,
    MAY_NOT_DEPLOY_TO_TARGET, MAY_DEPLOY_TO_TARGET, MAY_DEPLOY_TO_DAGOBAH_TARGET, MAY_DEPLOY_AS_LANDED_TO_TARGET, MAY_DEPLOY_TO_AHCHTO_TARGET,
    MAY_DEPLOY_WITHOUT_REPLACEMENT_TO_TARGET,
    DEPLOY_ONLY_USING_OWN_FORCE_TO_TARGET,
    INTERRUPT_PLAYS_FOR_FREE,
    EXTRA_FORCE_COST_TO_PLAY_INTERRUPT, MAY_DEPLOY_AS_IF_FROM_HAND,
    IMMUNE_TO_DEPLOY_COST_MODIFIERS_TO_TARGET,
    IGNORES_DEPLOYMENT_RESTRICTIONS_FROM_CARD,
    IGNORES_DEPLOYMENT_RESTRICTIONS_FROM_CARD_WHEN_DEPLOYING_TO_LOCATION,
    IGNORES_LOCATION_DEPLOYMENT_RESTRICTIONS_FROM_CARD_WHEN_DEPLOYING_TO_LOCATION,
    IGNORES_LOCATION_DEPLOYMENT_RESTRICTIONS_WHEN_DEPLOYING_TO_LOCATION,
    IGNORES_LOCATION_DEPLOYMENT_RESTRICTIONS_IN_GAME_TEXT,
    IGNORES_LOCATION_DEPLOYMENT_RESTRICTIONS_FROM_CARD,
    MAY_DEPLOY_DURING_CURRENT_PHASE,
    APPLIES_OWN_DEPLOYMENT_MODIFIERS_AT_ANY_LOCATION,
    MAY_NOT_DEPLOY_SITES_BETWEEN_SITES,
    MAY_DEPLOY_WITHOUT_REPLACEMENT,
    DEPLOYS_ADJACENT_TO_SPECIFIC_LOCATION,

    // Movement
    MAY_NOT_MOVE,
    MAY_NOT_MOVE_USING_HYPERSPEED,
    MAY_NOT_MOVE_USING_LANDSPEED,
    MAY_NOT_MOVE_FROM_LOCATION,
    MAY_NOT_MOVE_FROM_LOCATION_USING_HYPERSPEED,
    MAY_NOT_MOVE_FROM_LOCATION_USING_LANDSPEED,
    MAY_NOT_MOVE_FROM_LOCATION_TO_LOCATION,
    MAY_NOT_MOVE_FROM_LOCATION_TO_LOCATION_USING_HYPERSPEED,
    MAY_NOT_MOVE_FROM_LOCATION_TO_LOCATION_USING_LANDSPEED,
    MAY_NOT_MOVE_AWAY_FROM_LOCATION,
    MAY_NOT_MOVE_TO_LOCATION,
    MAY_NOT_MOVE_TO_LOCATION_USING_HYPERSPEED,
    MAY_NOT_MOVE_TO_LOCATION_USING_LANDSPEED,
    MAY_ONLY_MOVE_USING_LANDSPEED,
    MOVED_ONLY_BY_OPPONENT,
    MOVES_USING_LANDSPEED_ONLY_DURING_DEPLOY_PHASE,
    TIE_MAY_LAND_AT_EXTERIOR_SITE,
    LANDS_AS_UNLIMITED_MOVE,
    TAKES_OFF_AS_UNLIMITED_MOVE,

    // Movement (landspeed requirements)
    MOVE_FROM_LOCATION_LANDSPEED_REQUIREMENT,
    IMMUNE_TO_LANDSPEED_REQUIREMENTS,

    // Movement (for free)
    MOVES_FREE,
    MOVES_FREE_USING_HYPERSPEED,
    MOVES_FREE_USING_LANDSPEED,
    MOVES_FREE_FROM_LOCATION,
    MOVES_FREE_FROM_LOCATION_USING_HYPERSPEED,
    MOVES_FREE_FROM_LOCATION_USING_LANDSPEED,
    MOVES_FREE_FROM_LOCATION_TO_LOCATION,
    MOVES_FREE_FROM_LOCATION_TO_LOCATION_USING_HYPERSPEED,
    MOVES_FREE_FROM_LOCATION_TO_LOCATION_USING_LANDSPEED,
    MOVES_FREE_TO_LOCATION,
    MOVES_FREE_TO_LOCATION_USING_HYPERSPEED,
    MOVES_FREE_TO_LOCATION_USING_LANDSPEED,
    MOVES_FREE_TOWARD_TARGET,
    SHUTTLES_FOR_FREE,
    SHUTTLES_FOR_FREE_FROM_LOCATION,
    SHUTTLES_FOR_FREE_FROM_LOCATION_TO_LOCATION,
    SHUTTLES_FOR_FREE_TO_LOCATION,
    LANDS_FOR_FREE,
    LANDS_FOR_FREE_FROM_LOCATION,
    LANDS_FOR_FREE_FROM_LOCATION_TO_LOCATION,
    LANDS_FOR_FREE_TO_LOCATION,
    TAKES_OFF_FOR_FREE,
    TAKES_OFF_FOR_FREE_FROM_LOCATION,
    TAKES_OFF_FOR_FREE_FROM_LOCATION_TO_LOCATION,
    TAKES_OFF_FOR_FREE_TO_LOCATION,
    SHIPDOCKS_FOR_FREE,
    DOCKING_BAY_TRANSIT_FROM_FOR_FREE,
    DOCKING_BAY_TRANSIT_TO_FOR_FREE,
    MAY_SHUTTLE_DIRECTLY_FROM_LOCATION_TO_LOCATION,
    MAY_USE_LOCATION_TO_SHUTTLE_TRANSFER_LAND_OR_TAKE_OFF_FOR_FREE_INSTEAD_OF_RELATED_STARSHIP,

    // Movement (cost)
    MOVE_COST,
    MOVE_COST_USING_HYPERSPEED,
    MOVE_COST_USING_LANDSPEED,
    MOVE_COST_FROM_LOCATION,
    MOVE_COST_FROM_LOCATION_USING_HYPERSPEED,
    MOVE_COST_FROM_LOCATION_USING_LANDSPEED,
    MOVE_COST_FROM_LOCATION_TO_LOCATION,
    MOVE_COST_FROM_LOCATION_TO_LOCATION_USING_HYPERSPEED,
    MOVE_COST_FROM_LOCATION_TO_LOCATION_USING_LANDSPEED,
    MOVE_COST_TO_LOCATION,
    MOVE_COST_TO_LOCATION_USING_HYPERSPEED,
    MOVE_COST_TO_LOCATION_USING_LANDSPEED,
    DOCKING_BAY_TRANSIT_FROM_COST,
    DOCKING_BAY_TRANSIT_TO_COST,
    IGNORE_OTHER_DOCKING_BAY_TRANSIT_COST,
    ENTER_EXIT_COST,

    // Reacting
    MAY_NOT_REACT,
    MAY_NOT_REACT_FROM_LOCATION,
    MAY_NOT_REACT_TO_LOCATION,
    MAY_DEPLOY_AS_REACT_TO_TARGET,
    MAY_DEPLOY_WITH_PILOT_OR_DRIVER_AS_REACT_TO_TARGET,
    MAY_DEPLOY_OTHER_CARD_AS_REACT_TO_TARGET,
    MAY_MOVE_AS_REACT_TO_LOCATION,
    MAY_MOVE_AWAY_AS_REACT_TO_LOCATION,
    MAY_MOVE_OTHER_CARD_AS_REACT_TO_LOCATION,
    MAY_MOVE_OTHER_CARD_AWAY_AS_REACT_TO_LOCATION,

    // Force draining
    IGNORES_OBJECTIVE_RESTRICTIONS_WHEN_FORCE_DRAINING_AT_LOCATION,
    INITIATE_FORCE_DRAIN_COST,
    FORCE_DRAIN_AMOUNT,
    UNMODIFIABLE_FORCE_DRAIN_AMOUNT,
    MAY_FORCE_DRAIN,
    MAY_NOT_FORCE_DRAIN_AT_LOCATION,
    CANCEL_FORCE_DRAIN_BONUSES_FROM_CARD,
    CANCEL_OPPONENTS_FORCE_DRAIN_BONUSES,
    CANCEL_OPPONENTS_FORCE_DRAIN_MODIFIERS,
    MAY_NOT_CANCEL_FORCE_DRAIN_AT_LOCATION,
    MAY_NOT_MODIFY_FORCE_DRAIN_AT_LOCATION,
    MAY_NOT_REDUCE_FORCE_DRAIN_AT_LOCATION,
    MAY_NOT_REDUCE_FORCE_LOSS_FROM_FORCE_DRAIN_AT_LOCATION,
    MAY_NOT_MODIFY_FORCE_DRAINS_BY_USING_CARD,
    MAY_NOT_CANCEL_FORCE_DRAINS_BY_USING_CARD,
    FORCE_DRAIN_MODIFIERS_MAY_NOT_BE_CANCELED,
    FORCE_DRAIN_BONUSES_MAY_NOT_BE_CANCELED,

    // Battles and Force loss
    INITIATE_BATTLE_FOR_FREE, MAY_INITIATE_BATTLE_FOR_FREE, UNMODIFIABLE_INITIATE_BATTLE_COST, INITIATE_BATTLE_COST, INITIATE_BATTLE_COST_AS_LOSE_FORCE,
    MAY_NOT_PARTICIPATE_IN_BATTLE, MAY_NOT_PARTICIPATE_IN_BATTLE_INITIATED_BY_OWNER,
    EXCLUDED_FROM_BATTLE, MAY_NOT_BE_EXCLUDED_FROM_BATTLE, PASSENGER_APPLIES_ABILITY_FOR_BATTLE_DESTINY,
    MAY_NOT_APPLY_ABILITY_FOR_BATTLE_DESTINY, PERMANENT_PILOTS_MAY_NOT_APPLY_ABILITY_FOR_BATTLE_DESTINY, ABILITY_FOR_BATTLE_DESTINY, TOTAL_ABILITY_FOR_BATTLE_DESTINY,
    ABILITY_REQUIRED_FOR_BATTLE_DESTINY_MODIFIER, UNMODIFIABLE_ABILITY_REQUIRED_FOR_BATTLE_DESTINY, UNMODIFIABLE_ABILITY_MORE_THAN_REQUIRED_FOR_BATTLE_DESTINY,
    MAY_INITIATE_BATTLE, MAY_BE_BATTLED, MAY_NOT_BE_BATTLED, MAY_NOT_INITIATE_BATTLE_AT_LOCATION, MAY_INITIATE_BATTLE_AT_LOCATION, IGNORES_OBJECTIVE_RESTRICTIONS_WHEN_INITIATING_BATTLE_AT_LOCATION,
    MAY_NOT_CANCEL_BATTLE, TOTAL_POWER_DURING_BATTLE,

    // Battle damage and Force loss
    BATTLE_DAMAGE, FORCE_LOSS, FORCE_LOSS_DIVIDER, SATISFIES_ALL_BATTLE_DAMAGE_WHEN_FORFEITED, FORCE_LOSS_MINIMUM,
    FORFEITED_TO_USED_PILE, NO_BATTLE_DAMAGE, BATTLE_DAMAGE_LIMIT,

    // Force loss
    LIMIT_FORCE_LOSS_FROM_CARD, LIMIT_FORCE_LOSS_FROM_FORCE_DRAIN, LIMIT_FORCE_LOSS_FROM_INSERT_CARD,

    // Force retrieval
    FORCE_RETRIEVAL, UNMODIFIABLE_FORCE_RETRIEVAL, FORCE_RETRIEVAL_FOR_BOUNTY, MAY_NOT_CONTRIBUTE_TO_FORCE_RETRIEVAL, PLAYERS_CARDS_AT_LOCATION_MAY_NOT_CONTRIBUTE_TO_FORCE_RETRIEVAL,
    FORCE_RETRIEVAL_IMMUNE_TO_SECRET_PLANS,

    // Attacks
    MAY_NOT_INITIATE_ATTACKS_AT_LOCATION, MAY_NOT_ATTACK, MAY_NOT_ATTACK_TARGET, MAY_NOT_BE_ATTACKED, MAY_ATTACK_TARGET,
    MAY_INITIATE_ATTACKS_AT_LOCATION, EATEN_BY_IS_PLACED_OUT_OF_PLAY, PARASITE_TARGET,
    NUM_ATTACK_DESTINY_DRAWS,

    // Attrition
    ATTRITION, UNMODIFIABLE_ATTRITION,
    IMMUNITY_TO_ATTRITION_LESS_THAN, IMMUNITY_TO_ATTRITION_OF_EXACTLY, IMMUNITY_TO_ATTRITION_CHANGE, LOSE_IMMUNITY_TO_ATTRITION, IMMUNITY_TO_ATTRITION_MAY_NOT_BE_CANCELED, IMMUNITY_TO_ATTRITION_LIMITED_TO_VALUE,
    MAY_NOT_SATISFY_ATTRITION, SATISFIES_ALL_ATTRITION_WHEN_FORFEITED,

    // Targeting
    MAY_NOT_BE_TARGETED_BY, MAY_NOT_BE_HIT_BY, MAY_NOT_BE_TARGETED_BY_WEAPON_USER,
    IMMUNE_TO_TITLE, MAY_NOT_BE_STOLEN, MAY_NOT_TARGET_TO_BE_HIT, MAY_NOT_TARGET_TO_BE_CAPTURED, MAY_NOT_TARGET_TO_BE_LOST, MAY_NOT_BE_CHOKED,
    EFFECT_TARGET, JEDI_TEST_TARGET, MOUSE_DROID_TARGET, TARGET_ADJACENT_SITE, TARGET_TWO_SITE_AWAY, MAY_TARGET_AT_NEAREST_RELATED_EXTERIOR_SITE,
    MAY_BE_TARGETED_BY, MAY_NOT_TARGET_TO_BE_FROZEN, MAY_NOT_TARGET_TO_BE_TORTURED, MAY_NOT_BE_PURCHASED, MAY_NOT_BE_DISARMED, DEVICE_MAY_NOT_BE_REMOVED_UNLESS_DISARMED,
    CANT_USE_TO_TRANSPORT_TO_OR_FROM_LOCATION,
    MAY_NOT_TARGET_TO_BE_PLACED_OUT_OF_PLAY_BY,

    // Suspend card
    SUSPEND_CARD, MAY_NOT_BE_SUSPENDED,

    // Suspend effects from card
    SUSPEND_EFFECTS_FROM_CARD,

    // Game text
    CANCEL_GAME_TEXT, CANCEL_LOCATION_GAME_TEXT_FOR_PLAYER, MODIFY_GAME_TEXT,
    EFFECTS_OF_REVOLUTION_CANCELED, MAY_NOT_HAVE_GAME_TEXT_CANCELED,

    // Playing cards
    MAY_NOT_PLAY, MAY_NOT_PLAY_USING_DEJARIK_RULES, UNIQUE, NOT_UNIQUE, LOST_INTERRUPT, USED_INTERRUPT,

    // Using cards
    MAY_NOT_CARRY, MAY_NOT_BE_USED,
    MAY_NOT_USE_DEVICES, MAY_NOT_USE_WEAPONS,
    MAY_USE_ANY_NUMBER_OF_DEVICES, MAY_USE_ANY_NUMBER_OF_WEAPONS,
    MAY_BE_USED_BY_LANDED_STARSHIP,
    MAY_USE_DEVICE, MAY_USE_WEAPON,

    // Transferring
    TRANSFERS_FREE_TO_TARGET,

    // Firing weapons
    MAY_NOT_BE_FIRED,
    PRINTED_FIRE_WEAPON_COST,
    FIRES_FOR_FREE, FIRE_WEAPON_FIRED_BY_FOR_FREE,
    FIRES_FOR_DOUBLE,
    FIRE_WEAPON_COST, FIRE_WEAPON_FIRED_BY_COST, FIRE_WEAPON_FIRED_AT_COST,
    EXTRA_FORCE_COST_TO_FIRE_WEAPON,
    MAY_NOT_FIRE_WEAPONS, MAY_FIRE_ANY_NUMBER_OF_WEAPONS, MAY_FIRE_TWICE_PER_BATTLE,
    MAY_FIRE_REPEATEDLY_FOR_COST,
    MAY_FIRE_ARTILLERY_WEAPON_WITHOUT_WARRIOR_PRESENT,
    MAY_FIRE_A_WEAPON_TWICE_PER_BATTLE,

    // Weapon destiny
    DESTINY_WHEN_DRAWN_FOR_WEAPON_DESTINY,
    EACH_WEAPON_DESTINY,
    TOTAL_WEAPON_DESTINY,
    MAY_NOT_CANCEL_WEAPON_DESTINY,
    MAY_NOT_MODIFY_WEAPON_DESTINY,

    // Destiny
    EACH_DESTINY_DRAW_FOR_ACTION_SOURCE, DESTINY_WHEN_DRAWN_FOR_DESTINY, EACH_DESTINY_DRAW, TOTAL_DESTINY,
    DRAW_DESTINY_FROM_BOTTOM_OF_DECK, MAY_NOT_CANCEL_DESTINY_DRAWS, MAY_NOT_CANCEL_DESTINY_DRAWS_UNLESS_BEING_REDRAWN,

    // Destiny to power or attrition only
    NUM_DESTINY_DRAWS_TO_POWER_ONLY, NUM_DESTINY_DRAWS_TO_ATTRITION_ONLY,
    MAY_NOT_ADD_DESTINY_DRAWS_TO_POWER, MAY_NOT_ADD_DESTINY_DRAWS_TO_ATTRITION,

    // Battle destiny
    NUM_BATTLE_DESTINY_DRAWS, MIN_BATTLE_DESTINY_DRAWS, MAX_BATTLE_DESTINY_DRAWS,
    BATTLE_DESTINY_DRAWS_MAY_NOT_BE_LIMITED_FOR_EITHER_PLAYER,
    EACH_BATTLE_DESTINY_AT_LOCATION, TOTAL_BATTLE_DESTINY_AT_LOCATION, UNMODIFIABLE_TOTAL_BATTLE_DESTINY_AT_LOCATION,
    DESTINY_WHEN_DRAWN_FOR_BATTLE_DESTINY,
    MAY_NOT_CANCEL_BATTLE_DESTINY,
    MAY_NOT_CANCEL_BATTLE_DESTINY_UNLESS_BEING_REDRAWN,
    MAY_NOT_MODIFY_BATTLE_DESTINY,
    MAY_NOT_RESET_BATTLE_DESTINY,
    MAY_NOT_SUBSTITUTE_BATTLE_DESTINY,
    MAY_NOT_ADD_BATTLE_DESTINY_DRAWS,
    MAY_NOT_MODIFY_TOTAL_BATTLE_DESTINY,
    MAY_NOT_INCREASE_TOTAL_BATTLE_DESTINY,
    MAY_NOT_RESET_TOTAL_BATTLE_DESTINY,

    // Asteroid destiny
    EACH_ASTEROID_DESTINY,
    TOTAL_ASTEROID_DESTINY,

    // Movement destiny
    TOTAL_MOVEMENT_DESTINY,

    // Epic event destiny draw
    EACH_EPIC_EVENT_DESTINY_DRAW,

    // Carbon-Freezing destiny
    EACH_CARBON_FREEZING_DESTINY,
    TOTAL_CARBON_FREEZING_DESTINY,

    // Race destiny
    MAY_NOT_DRAW_RACE_DESTINY,
    NUM_RACE_DESTINY_DRAWS,
    NUM_RACE_DESTINY_DRAW_AND_CHOOSE,
    RACE_DESTINY,

    // Search party destiny
    EACH_SEARCH_PARTY_DESTINY_AT_LOCATION,

    // Training destiny
    EACH_TRAINING_DESTINY,
    TOTAL_TRAINING_DESTINY,

    // Tractor beam destiny
    EACH_TRACTOR_BEAM_DESTINY,
    TOTAL_TRACTOR_BEAM_DESTINY,

    // Capturing
    MAY_ESCORT_A_CAPTIVE,
    MAY_ESCORT_ANY_NUMBER_OF_CAPTIVES,
    MAY_NOT_BE_TRANSFERRED,

    // Political Effects
    MAY_BE_PLACED_ON_OWNERS_POLITICAL_EFFECT,

    // Jedi Testing
    MAY_NOT_ATTEMPT_JEDI_TESTS,
    PLACE_JEDI_TEST_ON_TABLE_WHEN_COMPLETED,
    JEDI_TEST_SUSPENDED_INSTEAD_OF_LOST,

    // Converting locations
    MAY_NOT_BE_CONVERTED,

    // Sabacc
    SABACC_TOTAL, MAY_CLONE_DESTINY_IN_SABACC,

    // Addition actions
    MAY_PLAY_TO_INITIATE_EPIC_DUEL,
    MAY_PLAY_TO_CANCEL_FORCE_DRAIN,
    MAY_PLAY_TO_CANCEL_CARD,

    // Duel
    DUEL_TOTAL, NUM_DUEL_DESTINY_DRAWS, EACH_DUEL_DESTINY,

    // Lightsaber combat
    LIGHTSABER_COMBAT_TOTAL, EACH_LIGHTSABER_COMBAT_DESTINY, NUM_LIGHTSABER_COMBAT_DESTINY_DRAWS,

    // Blow away
    BLOW_AWAY_ALDERAAN_ATTEMPT_TOTAL,
    BLOW_AWAY_BLOCKADE_FLAGSHIP_ATTEMPT_TOTAL,
    BLOW_AWAY_SHIELD_GATE_ATTEMPT_TOTAL,
    BLOWN_AWAY_FORCE_LOSS,
    BLOWN_AWAY_FORCE_MULTIPLIER,

    // Highest-ability character
    MAY_NOT_BE_HIGHEST_ABILITY_CHARACTER,

    // Search party
    MAY_NOT_JOIN_SEARCH_PARTY,

    // Stealing
    LOST_IF_ABOUT_TO_BE_STOLEN,

    // Expand location game text
    EXPAND_LOCATION_GAME_TEXT,

    // Use opponent's Force
    MAY_USE_OPPONENTS_FORCE,

    // Converting characters (replacing)
    MAY_BE_REPLACED_BY_OPPONENT,
    MAY_NOT_BE_REPLACED_BY_OPPONENT,

    // Misc
    BATTLEGROUND,
    MAY_ONLY_DEPLOY_ONE_OPERATIVE_PER_TURN_RULE,
    MAY_NOT_DEPLOY_MOVE_OPERATIVE_RULE,
    MAY_NOT_DEPLOY_MOVE_SITH_PROBE_DROID,
    MAY_NOT_ALLOW_PLAYER_TO_DOWNLOAD_CARDS,
    MAY_NOT_CLOAK,
    MAY_NOT_ATTACH,
    MAY_CARRY_PASSENGER_AS_IF_CREATURE_VEHICLE,
    ADDITION_CALCULATION, MULTIPLICATION_CALCULATION, INITIAL_CALCULATION, RESET_CALCULATION,
    ROTATE_LOCATION, UNDERCOVER,
    PILOT_CAPACITY, ASTROMECH_CAPACITY,
    ABILITY_REQUIRED_TO_CONTROL_LOCATION,
    IGNORE_DURING_EPIC_EVENT_CALCULATION, IS_POWERED, DOES_NOT_REQUIRE_POWER_SOURCE,
    EPIC_EVENT_CALCULATION_TOTAL, UNDER_HOTH_ENERGY_SHIELD, MAY_NOT_BE_COVERED_BY_HOTH_ENERGY_SHIELD,
    IS_DOUBLED, PLAYER_TO_SELECT_CARD_TARGET_AT_LOCATION,
    MAY_NOT_BE_CANCELED, MAY_NOT_BE_GRABBED, MAY_NOT_BE_PLACED_OUT_OF_PLAY, MAY_NOT_BE_FLIPPED, SUSPEND_PERMANENT_PILOT, SUSPEND_PERMANENT_ASTROMECH,
    PLACE_IN_USED_PILE_WHEN_CANCELED,
    CANT_SEARCH_CARD_PILE,
    MAY_USE_OTHER_CHARACTERS_COMBAT_CARDS, LIGHTSABER_COMBAT_FORCE_LOSS,
    PODRACE_FORCE_RETRIEVAL,
    PODRACE_FORCE_LOSS,
    CROSS_OVER_ATTEMPT_TOTAL, CALCULATION_TOTAL, CALCULATION_TOTAL_WHEN_TARGETED,
    MAY_MAKE_KESSEL_RUN_WHEN_NOT_SMUGGLER,
    MAY_NOT_BE_REVIVED,
    MAY_NOT_BE_TURNED_ON,
    MAY_NOT_BREAK_OWN_COVER_DURING_DEPLOY_PHASE,
    MAY_NOT_SEARCH_CARD_PILE,
    NOT_LOST_IF_ASTEROID_SECTOR_DRAWN_FOR_ASTEROID_DESTINY,
    PLACED_OUT_OF_PLAY_WHEN_COMPLETED,
    MAY_BE_TARGETED_BY_WEAPONS,
    MAY_BE_TARGETED_BY_WEAPONS_AS_IF_PRESENT,
    TARGETED_BY_WEAPONS_LIKE_A_STARFIGHTER,
    DEPLOYS_AND_MOVES_LIKE_UNDERCOVER_SPY,
    CHARACTERS_ABOARD_MAY_JUMP_OFF,
    REPLACE_ABILITY_1_PERMANENT_PILOTS,
    MAY_NOT_APPLY_ABILITY_FOR_SENSE_ALTER_DESTINY,
    MAY_NOT_REMOVE_CARDS_FROM_OPPONENTS_HAND,
    MAY_DEPLOY_INSTEAD_OF_STARFIGHTER_USING_COMBAT_RESPONSE,
    MAY_DEPLOY_WITH_INSTEAD_OF_MATCHING_STARFIGHTER_USING_COMBAT_RESPONSE,
    MINDSCANNED_CHARACTER,
    TRACTOR_BEAM_DESTINATION,
    COMMUNING,
    JABBAS_SAIL_BARGE_MAY_DEPLOY_HERE,
    SCUM_AND_VILLAINY_MAY_DEPLOY_HERE,
    CONSIDERED_OUT_OF_PLAY,
    MAY_NOT_BE_PLAYED_UNLESS_IMMUNE_TO_SPECIFIC_TITLE,
    MAY_BE_REVEALED_AS_RESISTANCE_AGENT,
    MAY_NOT_REMOVE_JUST_LOST_CARDS_FROM_LOST_PILE,
    CONFLICT_CARD,
    CREDIT_CARD,
}
