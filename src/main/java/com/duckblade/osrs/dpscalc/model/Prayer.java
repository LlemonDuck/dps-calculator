package com.duckblade.osrs.dpscalc.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Prayer
{

	AUGURY("Augury", PrayerGroup.OFFENSE, 1.25f, 1f, 24),
	RIGOUR("Rigour", PrayerGroup.OFFENSE, 1.2f, 1.23f, 24),
	PIETY("Piety", PrayerGroup.OFFENSE, 1.2f, 1.23f, 24),
	CHIVALRY("Chivalry", PrayerGroup.OFFENSE, 1.15f, 1.18f, 24),
	MYSTIC_MIGHT("Mystic Might", PrayerGroup.OFFENSE, 1.15f, 1f, 12),
	EAGLE_EYE("Eagle Eye", PrayerGroup.OFFENSE, 1.15f, 1f, 12),
	INCREDIBLE_REFLEXES("Incredible Reflexes", PrayerGroup.OFFENSE, 1.15f, 1f, 12),
	ULTIMATE_STRENGTH("Ultimate Strength", PrayerGroup.OFFENSE, 1f, 1.15f, 12),
	MYSTIC_LORE("Mystic Lore", PrayerGroup.OFFENSE, 1.1f, 1f, 6),
	HAWK_EYE("Hawk Eye", PrayerGroup.OFFENSE, 1.1f, 1f, 6),
	IMPROVED_REFLEXES("Improved Reflexes", PrayerGroup.OFFENSE, 1.1f, 1f, 6),
	SUPERHUMAN_STRENGTH("Superhuman Strength", PrayerGroup.OFFENSE, 1f, 1.1f, 6),
	MYSTIC_WILL("Mystic Will", PrayerGroup.OFFENSE, 1.05f, 1f, 3),
	SHARP_EYE("Sharp Eye", PrayerGroup.OFFENSE, 1.05f, 1f, 3),
	CLARITY_OF_THOUGHT("Clarity of Thought", PrayerGroup.OFFENSE, 1.05f, 1f, 3),
	BURST_OF_STRENGTH("Burst of Strength", PrayerGroup.OFFENSE, 1f, 1.05f, 3),
	PROTECT_FROM_MAGIC("Protect from Magic", PrayerGroup.UTILITY, 1f, 1.05f, 12),
	PROTECT_FROM_RANGED("Protect from Missiles", PrayerGroup.UTILITY, 1f, 1.05f, 12),
	PROTECT_FROM_MELEE("Protect from Melee", PrayerGroup.UTILITY, 1f, 1.05f, 12),
	PRESERVE("Preserve", PrayerGroup.UTILITY, 1f, 1f, 2),
	SMITE("Smite", PrayerGroup.UTILITY, 1f, 1f, 18),
	REDEMPTION("Redemption", PrayerGroup.UTILITY, 1f, 1f, 6),
	RETRIBUTION("Retribution", PrayerGroup.UTILITY, 1f, 1f, 3),
	PROTECT_ITEM("Protect Item", PrayerGroup.UTILITY, 1f, 1f, 2),
	RAPID_HEAL("Rapid Heal", PrayerGroup.UTILITY, 1f, 1f, 2),
	RAPID_RESTORE("Rapid Restore", PrayerGroup.UTILITY, 1f, 1f, 1),
	STEEL_SKIN("Steel Skin", PrayerGroup.UTILITY, 1f, 1f, 12),
	ROCK_SKIN("Rock Skin", PrayerGroup.UTILITY, 1f, 1f, 6),
	THICK_SKIN("Thick Skin", PrayerGroup.UTILITY, 1f, 1f, 3),
	;

	public enum PrayerGroup
	{
		OFFENSE,
		UTILITY,
		;
	}

	public static final List<Prayer> OFFENSE =
			Arrays.stream(Prayer.values())
					.filter(p -> p.getPrayerGroup() == PrayerGroup.OFFENSE)
					.collect(Collectors.toList());

	public static final List<Prayer> UTILITY =
			Arrays.stream(Prayer.values())
					.filter(p -> p.getPrayerGroup() == PrayerGroup.UTILITY)
					.collect(Collectors.toList());

	private final String displayName;
	private final PrayerGroup prayerGroup;
	private final float attackMod;
	private final float strengthMod;
	private final int drainRate;

}
