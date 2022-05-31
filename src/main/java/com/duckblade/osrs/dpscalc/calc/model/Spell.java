package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Spell
{

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

	// standard but not autocast without special staff
	FLAMES_OF_ZAMORAK(-1, "Flames of Zamorak", 20, Spellbook.STANDARD),
	CLAWS_OF_GUTHIX(-1, "Claws of Guthix", 20, Spellbook.STANDARD),
	SARADOMIN_STRIKE(-1, "Saradomin Strike", 20, Spellbook.STANDARD),
	CRUMBLE_UNDEAD(-1, "Crumble Undead", 15, Spellbook.STANDARD),
	IBAN_BLAST(-1, "Iban Blast", 25, Spellbook.STANDARD),
	MAGIC_DART(-1, "Magic Dart", 10, Spellbook.STANDARD),

	// arceuus
	INFERIOR_DEMONBANE(53, "Inferior Demonbane", 16, Spellbook.ARCEUUS),
	SUPERIOR_DEMONBANE(54, "Superior Demonbane", 23, Spellbook.ARCEUUS),
	DARK_DEMONBANE(55, "Dark Demonbane", 30, Spellbook.ARCEUUS),
	GHOSTLY_GRASP(56, "Ghostly Grasp", 12, Spellbook.ARCEUUS),
	SKELETAL_GRASP(57, "Skeletal Grasp", 17, Spellbook.ARCEUUS),
	UNDEAD_GRASP(58, "Undead Grasp", 24, Spellbook.ARCEUUS),
	;

	private final int varbValue;

	private final String displayName;

	private final int baseMaxHit;

	private final Spellbook spellbook;

}