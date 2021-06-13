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
	SALAMANDER_BLAZE(-1, "Salamander Blaze", 0, Spellbook.POWERED_STAVES),
	MAGIC_DART(-1, "Magic Dart", 10, Spellbook.POWERED_STAVES),
	FLAMES_OF_ZAMORAK(-1, "Flames of Zamorak", 20, Spellbook.POWERED_STAVES),
	CLAWS_OF_GUTHIX(-1, "Claws of Guthix", 20, Spellbook.POWERED_STAVES),
	SARADOMIN_STRIKE(-1, "Saradomin Strike", 20, Spellbook.POWERED_STAVES),
	CRUMBLE_UNDEAD(-1, "Crumble Undead", 15, Spellbook.POWERED_STAVES),
	IBAN_BLAST(-1, "Iban Blast", 25, Spellbook.POWERED_STAVES),
	SANGUINESTI(-1, "Sanguinesti Staff", 24, Spellbook.POWERED_STAVES),
	SWAMP(-1, "Trident of the Swamp", 23, Spellbook.POWERED_STAVES),
	SEAS(-1, "Trident of the Seas", 20, Spellbook.POWERED_STAVES),
	
	// ancient spells
	ICE_BARRAGE(46, "Ice Barrage", 30, Spellbook.ANCIENT),
	BLOOD_BARRAGE(45, "Blood Barrage", 29, Spellbook.ANCIENT),
	SHADOW_BARRAGE(44, "Shadow Barrage", 28, Spellbook.ANCIENT),
	SMOKE_BARRAGE(43, "Smoke Barrage", 27, Spellbook.ANCIENT),
	ICE_BLITZ(42, "Ice Blitz", 26, Spellbook.ANCIENT),
	BLOOD_BLITZ(41, "Blood Blitz", 25, Spellbook.ANCIENT),
	SHADOW_BLITZ(40, "Shadow Blitz", 24, Spellbook.ANCIENT),
	SMOKE_BLITZ(39, "Smoke Blitz", 23, Spellbook.ANCIENT),
	ICE_BURST(38, "Ice Burst", 22, Spellbook.ANCIENT),
	BLOOD_BURST(37, "Blood Burst", 21, Spellbook.ANCIENT),
	SHADOW_BURST(36, "Shadow Burst", 18, Spellbook.ANCIENT),
	SMOKE_BURST(35, "Smoke Burst", 17, Spellbook.ANCIENT),
	ICE_RUSH(34, "Ice Rush", 16, Spellbook.ANCIENT),
	BLOOD_RUSH(33, "Blood Rush", 15, Spellbook.ANCIENT),
	SHADOW_RUSH(32, "Shadow Rush", 14, Spellbook.ANCIENT),
	SMOKE_RUSH(31, "Smoke Rush", 13, Spellbook.ANCIENT),
	
	// standard spells
	FIRE_SURGE(51, "Fire Surge", 24, Spellbook.STANDARD),
	EARTH_SURGE(50, "Earth Surge", 23, Spellbook.STANDARD),
	WATER_SURGE(49, "Water Surge", 22, Spellbook.STANDARD),
	WIND_SURGE(48, "Wind Surge", 21, Spellbook.STANDARD),
	FIRE_WAVE(16, "Fire Wave", 20, Spellbook.STANDARD),
	EARTH_WAVE(15, "Earth Wave", 19, Spellbook.STANDARD),
	WATER_WAVE(14, "Water Wave", 18, Spellbook.STANDARD),
	WIND_WAVE(13, "Wind Wave", 17, Spellbook.STANDARD),
	FIRE_BLAST(12, "Fire Blast", 16, Spellbook.STANDARD),
	EARTH_BLAST(11, "Earth Blast", 15, Spellbook.STANDARD),
	WATER_BLAST(10, "Water Blast", 14, Spellbook.STANDARD),
	WIND_BLAST(9, "Wind Blast", 13, Spellbook.STANDARD),
	FIRE_BOLT(8, "Fire Bolt", 12, Spellbook.STANDARD),
	EARTH_BOLT(7, "Earth Bolt", 11, Spellbook.STANDARD),
	WATER_BOLT(6, "Water Bolt", 10, Spellbook.STANDARD),
	WIND_BOLT(5, "Wind Bolt", 9, Spellbook.STANDARD),
	FIRE_STRIKE(4, "Fire Strike", 8, Spellbook.STANDARD),
	EARTH_STRIKE(3, "Earth Strike", 6, Spellbook.STANDARD),
	WATER_STRIKE(2, "Water Strike", 4, Spellbook.STANDARD),
	WIND_STRIKE(1, "Wind Strike", 2, Spellbook.STANDARD),
	;

	public enum Spellbook
	{
		STANDARD,
		ANCIENT,
		POWERED_STAVES,
		;
	}
	
	public static final int SPELL_SELECTED_VARBIT = 276;
	
	@Getter
	private final int varbValue;

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
			case ANCIENT_STAFF_20431:
			case MASTER_WAND:
			case KODAI_WAND:
			case NIGHTMARE_STAFF:
			case ELDRITCH_NIGHTMARE_STAFF:
			case VOLATILE_NIGHTMARE_STAFF:
				return STANDARD_AND_ANCIENT_SPELLS;
				
			case SWAMP_LIZARD:
			case ORANGE_SALAMANDER:
			case RED_SALAMANDER:
			case BLACK_SALAMANDER:
				return Collections.singletonList(SALAMANDER_BLAZE);

			case AHRIMS_STAFF:
			case AHRIMS_STAFF_0:
			case AHRIMS_STAFF_25:
			case AHRIMS_STAFF_50:
			case AHRIMS_STAFF_75:
			case AHRIMS_STAFF_100:
				if (ahrimsDamned)
					return STANDARD_AND_ANCIENT_SPELLS;
				else
					return STANDARD_SPELLS;
				
			default:
				return STANDARD_SPELLS;
		}
	}

}
