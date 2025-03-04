package com.duckblade.osrs.dpscalc.calc.model;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Spell
{

	// ancient spells
	ICE_BARRAGE(46, "Ice Barrage", 30, Spellbook.ANCIENT, null),
	BLOOD_BARRAGE(45, "Blood Barrage", 29, Spellbook.ANCIENT, null),
	SHADOW_BARRAGE(44, "Shadow Barrage", 28, Spellbook.ANCIENT, null),
	SMOKE_BARRAGE(43, "Smoke Barrage", 27, Spellbook.ANCIENT, null),
	ICE_BLITZ(42, "Ice Blitz", 26, Spellbook.ANCIENT, null),
	BLOOD_BLITZ(41, "Blood Blitz", 25, Spellbook.ANCIENT, null),
	SHADOW_BLITZ(40, "Shadow Blitz", 24, Spellbook.ANCIENT, null),
	SMOKE_BLITZ(39, "Smoke Blitz", 23, Spellbook.ANCIENT, null),
	ICE_BURST(38, "Ice Burst", 22, Spellbook.ANCIENT, null),
	BLOOD_BURST(37, "Blood Burst", 21, Spellbook.ANCIENT, null),
	SHADOW_BURST(36, "Shadow Burst", 18, Spellbook.ANCIENT, null),
	SMOKE_BURST(35, "Smoke Burst", 17, Spellbook.ANCIENT, null),
	ICE_RUSH(34, "Ice Rush", 16, Spellbook.ANCIENT, null),
	BLOOD_RUSH(33, "Blood Rush", 15, Spellbook.ANCIENT, null),
	SHADOW_RUSH(32, "Shadow Rush", 14, Spellbook.ANCIENT, null),
	SMOKE_RUSH(31, "Smoke Rush", 13, Spellbook.ANCIENT, null),

	// standard spells
	FIRE_SURGE(51, "Fire Surge", 24, Spellbook.STANDARD, Spellement.FIRE),
	EARTH_SURGE(50, "Earth Surge", 23, Spellbook.STANDARD, Spellement.EARTH),
	WATER_SURGE(49, "Water Surge", 22, Spellbook.STANDARD, Spellement.WATER),
	WIND_SURGE(48, "Wind Surge", 21, Spellbook.STANDARD, Spellement.AIR),
	FIRE_WAVE(16, "Fire Wave", 20, Spellbook.STANDARD, Spellement.FIRE),
	EARTH_WAVE(15, "Earth Wave", 19, Spellbook.STANDARD, Spellement.EARTH),
	WATER_WAVE(14, "Water Wave", 18, Spellbook.STANDARD, Spellement.WATER),
	WIND_WAVE(13, "Wind Wave", 17, Spellbook.STANDARD, Spellement.AIR),
	FIRE_BLAST(12, "Fire Blast", 16, Spellbook.STANDARD, Spellement.FIRE),
	EARTH_BLAST(11, "Earth Blast", 15, Spellbook.STANDARD, Spellement.EARTH),
	WATER_BLAST(10, "Water Blast", 14, Spellbook.STANDARD, Spellement.WATER),
	WIND_BLAST(9, "Wind Blast", 13, Spellbook.STANDARD, Spellement.AIR),
	FIRE_BOLT(8, "Fire Bolt", 12, Spellbook.STANDARD, Spellement.FIRE),
	EARTH_BOLT(7, "Earth Bolt", 11, Spellbook.STANDARD, Spellement.EARTH),
	WATER_BOLT(6, "Water Bolt", 10, Spellbook.STANDARD, Spellement.WATER),
	WIND_BOLT(5, "Wind Bolt", 9, Spellbook.STANDARD, Spellement.AIR),
	FIRE_STRIKE(4, "Fire Strike", 8, Spellbook.STANDARD, Spellement.FIRE),
	EARTH_STRIKE(3, "Earth Strike", 6, Spellbook.STANDARD, Spellement.EARTH),
	WATER_STRIKE(2, "Water Strike", 4, Spellbook.STANDARD, Spellement.WATER),
	WIND_STRIKE(1, "Wind Strike", 2, Spellbook.STANDARD, Spellement.AIR),

	// standard but not autocast without special staff
	FLAMES_OF_ZAMORAK(20, "Flames of Zamorak", 20, Spellbook.STANDARD, null),
	CLAWS_OF_GUTHIX(-1, "Claws of Guthix", 20, Spellbook.STANDARD, null),
	SARADOMIN_STRIKE(-1, "Saradomin Strike", 20, Spellbook.STANDARD, null),
	CRUMBLE_UNDEAD(17, "Crumble Undead", 15, Spellbook.STANDARD, null),
	IBAN_BLAST(47, "Iban Blast", 25, Spellbook.STANDARD, null),
	MAGIC_DART(18, "Magic Dart", 10, Spellbook.STANDARD, null),

	// arceuus
	INFERIOR_DEMONBANE(53, "Inferior Demonbane", 16, Spellbook.ARCEUUS, null),
	SUPERIOR_DEMONBANE(54, "Superior Demonbane", 23, Spellbook.ARCEUUS, null),
	DARK_DEMONBANE(55, "Dark Demonbane", 30, Spellbook.ARCEUUS, null),
	GHOSTLY_GRASP(56, "Ghostly Grasp", 12, Spellbook.ARCEUUS, null),
	SKELETAL_GRASP(57, "Skeletal Grasp", 17, Spellbook.ARCEUUS, null),
	UNDEAD_GRASP(58, "Undead Grasp", 24, Spellbook.ARCEUUS, null),
	;

	private static final Set<String> BIND_SPELLS = ImmutableSet.of(
		"Bind",
		"Snare",
		"Entangle"
	);

	private final int varb;
	private final String name;
	private final int baseMaxHit;
	private final Spellbook spellbook;
	private final Spellement spellement;

	public boolean isBindSpell()
	{
		return BIND_SPELLS.contains(name);
	}

	public int getMaxHit(int magicLevel)
	{
		if (spellement == null)
		{
			return baseMaxHit;
		}

		String spellClass = name.split(" ")[1];
		switch (spellClass)
		{
			case "Strike":
				if (magicLevel >= 13)
				{
					return FIRE_STRIKE.baseMaxHit;
				}
				if (magicLevel >= 9)
				{
					return EARTH_STRIKE.baseMaxHit;
				}
				if (magicLevel >= 5)
				{
					return WATER_STRIKE.baseMaxHit;
				}
				return WIND_STRIKE.baseMaxHit;

			case "Bolt":
				if (magicLevel >= 35)
				{
					return FIRE_BOLT.baseMaxHit;
				}
				if (magicLevel >= 29)
				{
					return EARTH_BOLT.baseMaxHit;
				}
				if (magicLevel >= 23)
				{
					return WATER_BOLT.baseMaxHit;
				}
				return WIND_BOLT.baseMaxHit;

			case "Blast":
				if (magicLevel >= 59)
				{
					return FIRE_BLAST.baseMaxHit;
				}
				if (magicLevel >= 53)
				{
					return EARTH_BLAST.baseMaxHit;
				}
				if (magicLevel >= 47)
				{
					return WATER_BLAST.baseMaxHit;
				}
				return WIND_BLAST.baseMaxHit;

			case "Wave":
				if (magicLevel >= 75)
				{
					return FIRE_WAVE.baseMaxHit;
				}
				if (magicLevel >= 70)
				{
					return EARTH_WAVE.baseMaxHit;
				}
				if (magicLevel >= 65)
				{
					return WATER_WAVE.baseMaxHit;
				}
				return WIND_WAVE.baseMaxHit;

			case "Surge":
				if (magicLevel >= 95)
				{
					return FIRE_SURGE.baseMaxHit;
				}
				if (magicLevel >= 90)
				{
					return EARTH_SURGE.baseMaxHit;
				}
				if (magicLevel >= 85)
				{
					return WATER_SURGE.baseMaxHit;
				}
				return WIND_SURGE.baseMaxHit;

			default:
				throw new IllegalArgumentException("No dynamic max hit available for " + this.name);
		}
	}

	public boolean canUseSunfireRunes()
	{
		return spellement == Spellement.FIRE;
	}
}
