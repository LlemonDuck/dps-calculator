package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Prayer
{

	THICK_SKIN("Thick Skin", PrayerGroup.UTILITY, 1f, 1f, 3, net.runelite.api.Prayer.THICK_SKIN),
	BURST_OF_STRENGTH("Burst of Strength", PrayerGroup.MELEE, 1f, 1.05f, 3, net.runelite.api.Prayer.BURST_OF_STRENGTH),
	CLARITY_OF_THOUGHT("Clarity of Thought", PrayerGroup.MELEE, 1.05f, 1f, 3, net.runelite.api.Prayer.CLARITY_OF_THOUGHT),
	SHARP_EYE("Sharp Eye", PrayerGroup.RANGED, 1.05f, 1.05f, 3, net.runelite.api.Prayer.SHARP_EYE),
	MYSTIC_WILL("Mystic Will", PrayerGroup.MAGE, 1.05f, 1f, 3, net.runelite.api.Prayer.MYSTIC_WILL),
	ROCK_SKIN("Rock Skin", PrayerGroup.UTILITY, 1f, 1f, 6, net.runelite.api.Prayer.ROCK_SKIN),
	SUPERHUMAN_STRENGTH("Superhuman Strength", PrayerGroup.MELEE, 1f, 1.1f, 6, net.runelite.api.Prayer.SUPERHUMAN_STRENGTH),
	IMPROVED_REFLEXES("Improved Reflexes", PrayerGroup.MELEE, 1.1f, 1f, 6, net.runelite.api.Prayer.IMPROVED_REFLEXES),
	RAPID_RESTORE("Rapid Restore", PrayerGroup.UTILITY, 1f, 1f, 1, net.runelite.api.Prayer.RAPID_RESTORE),
	RAPID_HEAL("Rapid Heal", PrayerGroup.UTILITY, 1f, 1f, 2, net.runelite.api.Prayer.RAPID_HEAL),
	PROTECT_ITEM("Protect Item", PrayerGroup.UTILITY, 1f, 1f, 2, net.runelite.api.Prayer.PROTECT_ITEM),
	HAWK_EYE("Hawk Eye", PrayerGroup.RANGED, 1.1f, 1.1f, 6, net.runelite.api.Prayer.HAWK_EYE),
	MYSTIC_LORE("Mystic Lore", PrayerGroup.MAGE, 1.1f, 1f, 6, net.runelite.api.Prayer.MYSTIC_LORE),
	STEEL_SKIN("Steel Skin", PrayerGroup.UTILITY, 1f, 1f, 12, net.runelite.api.Prayer.STEEL_SKIN),
	ULTIMATE_STRENGTH("Ultimate Strength", PrayerGroup.MELEE, 1f, 1.15f, 12, net.runelite.api.Prayer.ULTIMATE_STRENGTH),
	INCREDIBLE_REFLEXES("Incredible Reflexes", PrayerGroup.MELEE, 1.15f, 1f, 12, net.runelite.api.Prayer.INCREDIBLE_REFLEXES),
	PROTECT_FROM_MAGIC("Protect from Magic", PrayerGroup.UTILITY, 1f, 1.05f, 12, net.runelite.api.Prayer.PROTECT_FROM_MAGIC),
	PROTECT_FROM_MISSILES("Protect from Missiles", PrayerGroup.UTILITY, 1f, 1.05f, 12, net.runelite.api.Prayer.PROTECT_FROM_MISSILES),
	PROTECT_FROM_MELEE("Protect from Melee", PrayerGroup.UTILITY, 1f, 1.05f, 12, net.runelite.api.Prayer.PROTECT_FROM_MELEE),
	EAGLE_EYE("Eagle Eye", PrayerGroup.RANGED, 1.15f, 1.15f, 12, net.runelite.api.Prayer.EAGLE_EYE),
	MYSTIC_MIGHT("Mystic Might", PrayerGroup.MAGE, 1.15f, 1f, 12, net.runelite.api.Prayer.MYSTIC_MIGHT),
	RETRIBUTION("Retribution", PrayerGroup.UTILITY, 1f, 1f, 3, net.runelite.api.Prayer.RETRIBUTION),
	REDEMPTION("Redemption", PrayerGroup.UTILITY, 1f, 1f, 6, net.runelite.api.Prayer.REDEMPTION),
	SMITE("Smite", PrayerGroup.UTILITY, 1f, 1f, 18, net.runelite.api.Prayer.SMITE),
	PRESERVE("Preserve", PrayerGroup.UTILITY, 1f, 1f, 2, net.runelite.api.Prayer.PRESERVE),
	CHIVALRY("Chivalry", PrayerGroup.MELEE, 1.15f, 1.18f, 24, net.runelite.api.Prayer.CHIVALRY),
	PIETY("Piety", PrayerGroup.MELEE, 1.2f, 1.23f, 24, net.runelite.api.Prayer.PIETY),
	RIGOUR("Rigour", PrayerGroup.RANGED, 1.2f, 1.23f, 24, net.runelite.api.Prayer.RIGOUR),
	AUGURY("Augury", PrayerGroup.MAGE, 1.25f, 1f, 24, net.runelite.api.Prayer.AUGURY),
	;

	public enum PrayerGroup
	{
		MAGE,
		MELEE,
		RANGED,
		UTILITY,
		;
	}

	private final String displayName;
	private final PrayerGroup prayerGroup;
	private final float attackMod;
	private final float strengthMod;
	private final int drainRate;
	private final net.runelite.api.Prayer rlPrayer;

}
