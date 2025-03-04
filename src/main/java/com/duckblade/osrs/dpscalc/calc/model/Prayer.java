package com.duckblade.osrs.dpscalc.calc.model;

import static com.duckblade.osrs.dpscalc.calc.model.Factor.of;
import java.util.EnumSet;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Prayer
{

	BURST_OF_STRENGTH(
		net.runelite.api.Prayer.BURST_OF_STRENGTH,
		"Burst of Strength",
		1,
		PrayerCombatStyle.MELEE,
		null,
		of(105, 100),
		null,
		0
	),
	CLARITY_OF_THOUGHT(
		net.runelite.api.Prayer.CLARITY_OF_THOUGHT,
		"Clarity of Thought",
		1,
		PrayerCombatStyle.MELEE,
		of(105, 100),
		null,
		null,
		0
	),
	SHARP_EYE(
		net.runelite.api.Prayer.SHARP_EYE,
		"Sharp Eye",
		1,
		PrayerCombatStyle.RANGED,
		of(105, 100),
		of(105, 100),
		null,
		0
	),
	MYSTIC_WILL(
		net.runelite.api.Prayer.MYSTIC_WILL,
		"Mystic Will",
		1,
		PrayerCombatStyle.MAGIC,
		of(105, 100),
		null,
		of(105, 100),
		0
	),

	SUPERHUMAN_STRENGTH(
		net.runelite.api.Prayer.SUPERHUMAN_STRENGTH,
		"Superhuman Strength",
		6,
		PrayerCombatStyle.MELEE,
		null,
		of(110, 100),
		null,
		0
	),
	IMPROVED_REFLEXES(
		net.runelite.api.Prayer.IMPROVED_REFLEXES,
		"Improved Reflexes",
		6,
		PrayerCombatStyle.MELEE,
		of(110, 100),
		null,
		null,
		0
	),
	HAWK_EYE(
		net.runelite.api.Prayer.HAWK_EYE,
		"Hawk Eye",
		6,
		PrayerCombatStyle.RANGED,
		of(110, 100),
		of(110, 100),
		null,
		0
	),
	MYSTIC_LORE(
		net.runelite.api.Prayer.MYSTIC_LORE,
		"Mystic Lore",
		6,
		PrayerCombatStyle.MAGIC,
		of(110, 100),
		null,
		of(110, 100),
		0
	),
	ULTIMATE_STRENGTH(
		net.runelite.api.Prayer.ULTIMATE_STRENGTH,
		"Ultimate Strength",
		12,
		PrayerCombatStyle.MELEE,
		of(115, 100),
		null,
		null,
		0
	),
	INCREDIBLE_REFLEXES(
		net.runelite.api.Prayer.INCREDIBLE_REFLEXES,
		"Incredible Reflexes",
		12,
		PrayerCombatStyle.MELEE,
		null,
		of(115, 100),
		null,
		0
	),
	EAGLE_EYE(
		net.runelite.api.Prayer.EAGLE_EYE,
		"Eagle Eye",
		12,
		PrayerCombatStyle.RANGED,
		of(115, 100),
		of(115, 100),
		null,
		0
	),
	MYSTIC_MIGHT(
		net.runelite.api.Prayer.MYSTIC_MIGHT,
		"Mystic Might",
		12,
		PrayerCombatStyle.MAGIC,
		of(115, 100),
		null,
		of(115, 100),
		0
	),
	CHIVALRY(
		net.runelite.api.Prayer.CHIVALRY,
		"Chivalry",
		24,
		PrayerCombatStyle.MELEE,
		of(115, 100),
		of(118, 100),
		of(120, 100),
		0
	),
	PIETY(
		net.runelite.api.Prayer.PIETY,
		"Piety",
		24,
		PrayerCombatStyle.MELEE,
		of(120, 100),
		of(123, 100),
		of(125, 100),
		0
	),
	RIGOUR(
		net.runelite.api.Prayer.RIGOUR,
		"Rigour",
		24,
		PrayerCombatStyle.RANGED,
		of(120, 100),
		of(123, 100),
		of(125, 100),
		0
	),
	AUGURY(
		net.runelite.api.Prayer.AUGURY,
		"Augury",
		24,
		PrayerCombatStyle.MAGIC,
		of(125, 100),
		of(125, 100),
		of(125, 100),
		40
	),
	THICK_SKIN(
		net.runelite.api.Prayer.THICK_SKIN,
		"Thick Skin",
		1,
		PrayerCombatStyle.DEFENSIVE_ONLY,
		null,
		null,
		of(105, 100),
		0
	),
	ROCK_SKIN(
		net.runelite.api.Prayer.ROCK_SKIN,
		"Rock Skin",
		6,
		PrayerCombatStyle.DEFENSIVE_ONLY,
		null,
		null,
		of(110, 100),
		0
	),
	STEEL_SKIN(
		net.runelite.api.Prayer.STEEL_SKIN,
		"Steel Skin",
		12,
		PrayerCombatStyle.DEFENSIVE_ONLY,
		null,
		null,
		of(115, 100),
		0
	),
	DEADEYE(
		net.runelite.api.Prayer.DEADEYE,
		"Deadeye",
		1,
		PrayerCombatStyle.RANGED,
		of(118, 100),
		of(118, 100),
		of(105, 100),
		0
	),
	MYSTIC_VIGOUR(
		net.runelite.api.Prayer.MYSTIC_VIGOUR,
		"Mystic Vigour",
		1,
		PrayerCombatStyle.MAGIC,
		of(118, 100),
		null,
		of(105, 100),
		30
	),
	;

	public static Set<Prayer> DEFENSIVE_PRAYERS = EnumSet.of(
		Prayer.THICK_SKIN, Prayer.ROCK_SKIN, Prayer.STEEL_SKIN,
		Prayer.CHIVALRY, Prayer.PIETY, Prayer.RIGOUR, Prayer.AUGURY
	);

	public static Set<Prayer> OFFENSIVE_PRAYERS = EnumSet.of(
		Prayer.BURST_OF_STRENGTH, Prayer.CLARITY_OF_THOUGHT, Prayer.SHARP_EYE, Prayer.MYSTIC_WILL, Prayer.SUPERHUMAN_STRENGTH,
		Prayer.IMPROVED_REFLEXES, Prayer.HAWK_EYE, Prayer.MYSTIC_LORE, Prayer.ULTIMATE_STRENGTH, Prayer.INCREDIBLE_REFLEXES,
		Prayer.EAGLE_EYE, Prayer.MYSTIC_MIGHT, Prayer.DEADEYE, Prayer.MYSTIC_VIGOUR, Prayer.CHIVALRY, Prayer.PIETY, Prayer.RIGOUR, Prayer.AUGURY
	);

	public static Set<Prayer> BRAIN_PRAYERS = EnumSet.of(
		Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES
	);

	public static Set<Prayer> ARM_PRAYERS = EnumSet.of(
		Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH
	);

	private final net.runelite.api.Prayer rlPrayer;
	private final String name;
	private final int drainRate;
	private final PrayerCombatStyle combatStyle;
	private final Factor factorAccuracy;
	private final Factor factorStrength;
	private final Factor factorDefence;
	private final int magicDamageBonus;

}
