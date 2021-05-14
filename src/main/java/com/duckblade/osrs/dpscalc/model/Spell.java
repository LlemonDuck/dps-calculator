package com.duckblade.osrs.dpscalc.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static net.runelite.api.ItemID.*;

@RequiredArgsConstructor
public enum Spell
{

	// powered staves/specials
	SALAMANDER_BLAZE("Salamander Blaze", 0, Spellbook.POWERED_STAVES),
	MAGIC_DART("Magic Dart", 10, Spellbook.POWERED_STAVES),
	FLAMES_OF_ZAMORAK("Flames of Zamorak", 20, Spellbook.POWERED_STAVES),
	CLAWS_OF_GUTHIX("Claws of Guthix", 20, Spellbook.POWERED_STAVES),
	SARADOMIN_STRIKE("Saradomin Strike", 20, Spellbook.POWERED_STAVES),
	CRUMBLE_UNDEAD("Crumble Undead", 15, Spellbook.POWERED_STAVES),
	IBAN_BLAST("Iban Blast", 25, Spellbook.POWERED_STAVES),
	SANGUINESTI("Sanguinesti Staff", 24, Spellbook.POWERED_STAVES),
	SWAMP("Trident of the Swamp", 23, Spellbook.POWERED_STAVES),
	SEAS("Trident of the Seas", 20, Spellbook.POWERED_STAVES),
	
	// ancient spells
	ICE_BARRAGE("Ice Barrage", 30, Spellbook.ANCIENT),
	BLOOD_BARRAGE("Blood Barrage", 29, Spellbook.ANCIENT),
	SHADOW_BARRAGE("Shadow Barrage", 28, Spellbook.ANCIENT),
	SMOKE_BARRAGE("Smoke Barrage", 27, Spellbook.ANCIENT),
	ICE_BLITZ("Ice Blitz", 26, Spellbook.ANCIENT),
	BLOOD_BLITZ("Blood Blitz", 25, Spellbook.ANCIENT),
	SHADOW_BLITZ("Shadow Blitz", 24, Spellbook.ANCIENT),
	SMOKE_BLITZ("Smoke Blitz", 23, Spellbook.ANCIENT),
	ICE_BURST("Ice Burst", 22, Spellbook.ANCIENT),
	BLOOD_BURST("Blood Burst", 21, Spellbook.ANCIENT),
	SHADOW_BURST("Shadow Burst", 18, Spellbook.ANCIENT),
	SMOKE_BURST("Smoke Burst", 17, Spellbook.ANCIENT),
	ICE_RUSH("Ice Rush", 16, Spellbook.ANCIENT),
	BLOOD_RUSH("Blood Rush", 15, Spellbook.ANCIENT),
	SHADOW_RUSH("Shadow Rush", 14, Spellbook.ANCIENT),
	SMOKE_RUSH("Smoke Rush", 13, Spellbook.ANCIENT),
	
	// standard spells
	FIRE_SURGE("Fire Surge", 24, Spellbook.STANDARD),
	EARTH_SURGE("Earth Surge", 23, Spellbook.STANDARD),
	WATER_SURGE("Water Surge", 22, Spellbook.STANDARD),
	WIND_SURGE("Wind Surge", 21, Spellbook.STANDARD),
	FIRE_WAVE("Fire Wave", 20, Spellbook.STANDARD),
	EARTH_WAVE("Earth Wave", 19, Spellbook.STANDARD),
	WATER_WAVE("Water Wave", 18, Spellbook.STANDARD),
	WIND_WAVE("Wind Wave", 17, Spellbook.STANDARD),
	FIRE_BLAST("Fire Blast", 16, Spellbook.STANDARD),
	EARTH_BLAST("Earth Blast", 15, Spellbook.STANDARD),
	WATER_BLAST("Water Blast", 14, Spellbook.STANDARD),
	WIND_BLAST("Wind Blast", 13, Spellbook.STANDARD),
	FIRE_BOLT("Fire Bolt", 12, Spellbook.STANDARD),
	EARTH_BOLT("Earth Bolt", 11, Spellbook.STANDARD),
	WATER_BOLT("Water Bolt", 10, Spellbook.STANDARD),
	WIND_BOLT("Wind Bolt", 9, Spellbook.STANDARD),
	FIRE_STRIKE("Fire Strike", 8, Spellbook.STANDARD),
	EARTH_STRIKE("Earth Strike", 6, Spellbook.STANDARD),
	WATER_STRIKE("Water Strike", 4, Spellbook.STANDARD),
	WIND_STRIKE("Wind Strike", 2, Spellbook.STANDARD),
	;

	public enum Spellbook
	{
		STANDARD,
		ANCIENT,
		POWERED_STAVES,
		;
	}

	@Getter
	private final String displayName;

	@Getter
	private final int baseMaxHit;

	private final Spellbook spellbook;

	private static final List<Spell> STANDARD_SPELLS =
			Arrays.stream(values())
					.filter(s -> s.spellbook == Spellbook.STANDARD)
					.collect(Collectors.toList());

	private static final List<Spell> STANDARD_AND_ANCIENT_SPELLS = // there are no staves that can do ancient but not standard
			Arrays.stream(values())
					.filter(s -> s.spellbook != Spellbook.POWERED_STAVES)
					.collect(Collectors.toList());

	private static final List<Spell> WAVE_AND_SURGE = Arrays.asList(
			WIND_WAVE, WATER_WAVE, EARTH_WAVE, FIRE_WAVE, WIND_SURGE, WATER_SURGE, EARTH_SURGE, FIRE_SURGE, CRUMBLE_UNDEAD
	);
	private static final List<Spell> SLAYER_STAFF = Stream.concat(WAVE_AND_SURGE.stream(), Stream.of(MAGIC_DART)).collect(Collectors.toList());
	private static final List<Spell> GUTHIX_STAFF = Stream.concat(SLAYER_STAFF.stream(), Stream.of(CLAWS_OF_GUTHIX)).collect(Collectors.toList());
	private static final List<Spell> SARA_STAFF = Stream.concat(SLAYER_STAFF.stream(), Stream.of(SARADOMIN_STRIKE)).collect(Collectors.toList());
	private static final List<Spell> ZAM_STAFF = Stream.concat(SLAYER_STAFF.stream(), Stream.of(FLAMES_OF_ZAMORAK)).collect(Collectors.toList());
	private static final List<Spell> VOID_MACE = Stream.concat(WAVE_AND_SURGE.stream(), Stream.of(CLAWS_OF_GUTHIX)).collect(Collectors.toList());

	public static List<Spell> forWeapon(int staffId, boolean ahrimsDamned)
	{
		switch (staffId)
		{
			case TRIDENT_OF_THE_SEAS:
			case TRIDENT_OF_THE_SEAS_E:
			case TRIDENT_OF_THE_SEAS_FULL:
				return Collections.singletonList(SEAS);

			case TRIDENT_OF_THE_SWAMP:
			case TRIDENT_OF_THE_SWAMP_E:
				return Collections.singletonList(SWAMP);
				
			case SANGUINESTI_STAFF:
				return Collections.singletonList(SANGUINESTI);
				
			case IBANS_STAFF:
			case IBANS_STAFF_U:
				return Collections.singletonList(IBAN_BLAST);
			
			case SLAYERS_STAFF:
			case SLAYERS_STAFF_E:
				return SLAYER_STAFF;
				
			case STAFF_OF_BALANCE:
				return GUTHIX_STAFF;

			case STAFF_OF_LIGHT:
				return SARA_STAFF;
				
			case STAFF_OF_THE_DEAD:
			case TOXIC_STAFF_OF_THE_DEAD:
				return ZAM_STAFF;
				
			case VOID_KNIGHT_MACE:
			case VOID_KNIGHT_MACE_L:
				return VOID_MACE;
				
			case ANCIENT_STAFF:
			case MASTER_WAND:
			case KODAI_WAND:
			case NIGHTMARE_STAFF:
			case ELDRITCH_NIGHTMARE_STAFF:
			case VOLATILE_NIGHTMARE_STAFF:
			case AHRIMS_STAFF:
			case AHRIMS_STAFF_0: // should technically only be included if wearing full ahrims + damned
			case AHRIMS_STAFF_25:
			case AHRIMS_STAFF_50:
			case AHRIMS_STAFF_75:
			case AHRIMS_STAFF_100:
				return STANDARD_AND_ANCIENT_SPELLS;
				
			case SWAMP_LIZARD:
			case ORANGE_SALAMANDER:
			case RED_SALAMANDER:
			case BLACK_SALAMANDER:
				return Collections.singletonList(SALAMANDER_BLAZE);
				
			default:
				return STANDARD_SPELLS;
		}
	}

}
