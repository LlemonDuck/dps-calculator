package com.duckblade.osrs.dpscalc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Prayer
{

	THICK_SKIN("Thick Skin", PrayerGroup.UTILITY, 1f, 1f, 3),
	BURST_OF_STRENGTH("Burst of Strength", PrayerGroup.MELEE, 1f, 1.05f, 3),
	CLARITY_OF_THOUGHT("Clarity of Thought", PrayerGroup.MELEE, 1.05f, 1f, 3),
	SHARP_EYE("Sharp Eye", PrayerGroup.RANGED, 1.05f, 1.05f, 3),
	MYSTIC_WILL("Mystic Will", PrayerGroup.MAGE, 1.05f, 1f, 3),
	ROCK_SKIN("Rock Skin", PrayerGroup.UTILITY, 1f, 1f, 6),
	SUPERHUMAN_STRENGTH("Superhuman Strength", PrayerGroup.MELEE, 1f, 1.1f, 6),
	IMPROVED_REFLEXES("Improved Reflexes", PrayerGroup.MELEE, 1.1f, 1f, 6),
	RAPID_RESTORE("Rapid Restore", PrayerGroup.UTILITY, 1f, 1f, 1),
	RAPID_HEAL("Rapid Heal", PrayerGroup.UTILITY, 1f, 1f, 2),
	PROTECT_ITEM("Protect Item", PrayerGroup.UTILITY, 1f, 1f, 2),
	HAWK_EYE("Hawk Eye", PrayerGroup.RANGED, 1.1f, 1.1f, 6),
	MYSTIC_LORE("Mystic Lore", PrayerGroup.MAGE, 1.1f, 1f, 6),
	STEEL_SKIN("Steel Skin", PrayerGroup.UTILITY, 1f, 1f, 12),
	ULTIMATE_STRENGTH("Ultimate Strength", PrayerGroup.MELEE, 1f, 1.15f, 12),
	INCREDIBLE_REFLEXES("Incredible Reflexes", PrayerGroup.MELEE, 1.15f, 1f, 12),
	PROTECT_FROM_MAGIC("Protect from Magic", PrayerGroup.UTILITY, 1f, 1.05f, 12),
	PROTECT_FROM_RANGED("Protect from Missiles", PrayerGroup.UTILITY, 1f, 1.05f, 12),
	PROTECT_FROM_MELEE("Protect from Melee", PrayerGroup.UTILITY, 1f, 1.05f, 12),
	EAGLE_EYE("Eagle Eye", PrayerGroup.RANGED, 1.15f, 1.15f, 12),
	MYSTIC_MIGHT("Mystic Might", PrayerGroup.MAGE, 1.15f, 1f, 12),
	RETRIBUTION("Retribution", PrayerGroup.UTILITY, 1f, 1f, 3),
	REDEMPTION("Redemption", PrayerGroup.UTILITY, 1f, 1f, 6),
	SMITE("Smite", PrayerGroup.UTILITY, 1f, 1f, 18),
	PRESERVE("Preserve", PrayerGroup.UTILITY, 1f, 1f, 2),
	CHIVALRY("Chivalry", PrayerGroup.MELEE, 1.15f, 1.18f, 24),
	PIETY("Piety", PrayerGroup.MELEE, 1.2f, 1.23f, 24),
	RIGOUR("Rigour", PrayerGroup.RANGED, 1.2f, 1.23f, 24),
	AUGURY("Augury", PrayerGroup.MAGE, 1.25f, 1f, 24),
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

}
