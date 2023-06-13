package com.duckblade.osrs.dpscalc.calc3.model;

import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import static com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication.ofPercent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Prayer
{

	THICK_SKIN("Thick Skin", null, null, null, 3, net.runelite.api.Prayer.THICK_SKIN),
	BURST_OF_STRENGTH("Burst of Strength", AttackType.MELEE, null, ofPercent(5), 3, net.runelite.api.Prayer.BURST_OF_STRENGTH),
	CLARITY_OF_THOUGHT("Clarity of Thought", AttackType.MELEE, ofPercent(5), null, 3, net.runelite.api.Prayer.CLARITY_OF_THOUGHT),
	SHARP_EYE("Sharp Eye", AttackType.RANGED, ofPercent(5), ofPercent(5), 3, net.runelite.api.Prayer.SHARP_EYE),
	MYSTIC_WILL("Mystic Will", AttackType.MAGIC, ofPercent(5), null, 3, net.runelite.api.Prayer.MYSTIC_WILL),
	ROCK_SKIN("Rock Skin", null, null, null, 6, net.runelite.api.Prayer.ROCK_SKIN),
	SUPERHUMAN_STRENGTH("Superhuman Strength", AttackType.MELEE, null, ofPercent(10), 6, net.runelite.api.Prayer.SUPERHUMAN_STRENGTH),
	IMPROVED_REFLEXES("Improved Reflexes", AttackType.MELEE, ofPercent(10), null, 6, net.runelite.api.Prayer.IMPROVED_REFLEXES),
	RAPID_RESTORE("Rapid Restore", null, null, null, 1, net.runelite.api.Prayer.RAPID_RESTORE),
	RAPID_HEAL("Rapid Heal", null, null, null, 2, net.runelite.api.Prayer.RAPID_HEAL),
	PROTECT_ITEM("Protect Item", null, null, null, 2, net.runelite.api.Prayer.PROTECT_ITEM),
	HAWK_EYE("Hawk Eye", AttackType.RANGED, ofPercent(10), ofPercent(10), 6, net.runelite.api.Prayer.HAWK_EYE),
	MYSTIC_LORE("Mystic Lore", AttackType.MAGIC, ofPercent(10), null, 6, net.runelite.api.Prayer.MYSTIC_LORE),
	STEEL_SKIN("Steel Skin", null, null, null, 12, net.runelite.api.Prayer.STEEL_SKIN),
	ULTIMATE_STRENGTH("Ultimate Strength", AttackType.MELEE, null, ofPercent(15), 12, net.runelite.api.Prayer.ULTIMATE_STRENGTH),
	INCREDIBLE_REFLEXES("Incredible Reflexes", AttackType.MELEE, ofPercent(15), null, 12, net.runelite.api.Prayer.INCREDIBLE_REFLEXES),
	PROTECT_FROM_MAGIC("Protect from Magic", null, null, null, 12, net.runelite.api.Prayer.PROTECT_FROM_MAGIC),
	PROTECT_FROM_MISSILES("Protect from Missiles", null, null, null, 12, net.runelite.api.Prayer.PROTECT_FROM_MISSILES),
	PROTECT_FROM_MELEE("Protect from Melee", null, null, null, 12, net.runelite.api.Prayer.PROTECT_FROM_MELEE),
	EAGLE_EYE("Eagle Eye", AttackType.RANGED, ofPercent(15), ofPercent(15), 12, net.runelite.api.Prayer.EAGLE_EYE),
	MYSTIC_MIGHT("Mystic Might", AttackType.MAGIC, ofPercent(15), null, 12, net.runelite.api.Prayer.MYSTIC_MIGHT),
	RETRIBUTION("Retribution", null, null, null, 3, net.runelite.api.Prayer.RETRIBUTION),
	REDEMPTION("Redemption", null, null, null, 6, net.runelite.api.Prayer.REDEMPTION),
	SMITE("Smite", null, null, null, 18, net.runelite.api.Prayer.SMITE),
	PRESERVE("Preserve", null, null, null, 2, net.runelite.api.Prayer.PRESERVE),
	CHIVALRY("Chivalry", AttackType.MELEE, ofPercent(15), ofPercent(18), 24, net.runelite.api.Prayer.CHIVALRY),
	PIETY("Piety", AttackType.MELEE, ofPercent(20), ofPercent(23), 24, net.runelite.api.Prayer.PIETY),
	RIGOUR("Rigour", AttackType.RANGED, ofPercent(20), ofPercent(23), 24, net.runelite.api.Prayer.RIGOUR),
	AUGURY("Augury", AttackType.MAGIC, ofPercent(25), null, 24, net.runelite.api.Prayer.AUGURY),
	;

	private final String displayName;
	private final AttackType attackType;
	private final Multiplication accuracyMod;
	private final Multiplication strengthMod;
	private final int drainRate;
	private final net.runelite.api.Prayer rlPrayer;

}
