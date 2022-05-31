package com.duckblade.osrs.dpscalc.calc.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// https://oldschool.runescape.wiki/w/Module:CombatStyles
@RequiredArgsConstructor
public enum WeaponCategory
{

	TWO_HANDED_SWORD(10, Arrays.asList(
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Slash (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	AXE(1, Arrays.asList(
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Hack (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	BANNER(24, Arrays.asList(
		new AttackStyle(0, "Lunge (Stab/Accurate)", AttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Swipe (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Pound (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	BLADED_STAFF(21, Arrays.asList(
		new AttackStyle(0, "Jab (Stab/Accurate)", AttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Swipe (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Fend (Crush/Defensive)", AttackType.CRUSH, CombatStyle.DEFENSIVE, false),
		new AttackStyle(4, "Spell (Magic/Autocast)", AttackType.MAGIC, CombatStyle.AUTOCAST, false)
	)),
	BLUDGEON(26, Arrays.asList(
		new AttackStyle(0, "Pound (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(1, "Pummel (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false)
	)),
	BLUNT(2, Arrays.asList(
		new AttackStyle(0, "Pound (Crush/Accurate)", AttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pummel (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	BOW(3, Arrays.asList(
		new AttackStyle(0, "Accurate (Ranged/Accurate)", AttackType.RANGED, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Rapid (Ranged/Rapid)", AttackType.RANGED, CombatStyle.RAPID, false),
		new AttackStyle(3, "Longrange (Ranged/Longrange)", AttackType.RANGED, CombatStyle.LONGRANGE, false)
	)),
	BULWARK(27, Collections.singletonList(
		new AttackStyle(0, "Pummel (Crush/Accurate)", AttackType.CRUSH, CombatStyle.ACCURATE, false)
	)),
	CHINCHOMPAS(7, Collections.emptyList()),
	CLAW(4, Arrays.asList(
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Slash (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Lunge (Stab/Controlled)", AttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	CROSSBOW(5, Arrays.asList(
		new AttackStyle(0, "Accurate (Ranged/Accurate)", AttackType.RANGED, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Rapid (Ranged/Rapid)", AttackType.RANGED, CombatStyle.RAPID, false),
		new AttackStyle(3, "Longrange (Ranged/Longrange)", AttackType.RANGED, CombatStyle.LONGRANGE, false)
	)),
	PARTISAN(29, Arrays.asList(
		new AttackStyle(0, "Stab (Stab/Accurate)", AttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(0, "Lunge (Stab/Aggressive)", AttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(0, "Pound (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(0, "Block (Stab/Defensive)", AttackType.STAB, CombatStyle.ACCURATE, false)
	)),
	PICKAXE(11, Arrays.asList(
		new AttackStyle(0, "Spike (Stab/Accurate)", AttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Impale (Stab/Aggressive)", AttackType.STAB, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	POLEARM(12, Arrays.asList(
		new AttackStyle(0, "Jab (Stab/Controlled)", AttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(1, "Swipe (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Fend (Stab/Defensive)", AttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	POLESTAFF(18, Arrays.asList(
		new AttackStyle(0, "Bash (Crush/Accurate)", AttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pound (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	POWERED_STAFF(23, Arrays.asList(
		new AttackStyle(0, "Accurate (Magic/Accurate)", AttackType.MAGIC, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Accurate (Magic/Accurate)", AttackType.MAGIC, CombatStyle.ACCURATE, false),
		new AttackStyle(3, "Longrange (Magic/Longrange)", AttackType.MAGIC, CombatStyle.LONGRANGE, false)
	)),
	SALAMANDER(6, Arrays.asList(
		new AttackStyle(0, "Scorch (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(1, "Flare (Ranged/Accurate)", AttackType.RANGED, CombatStyle.ACCURATE, false),
		new AttackStyle(2, "Blaze (Magic/Defensive)", AttackType.MAGIC, CombatStyle.DEFENSIVE, false)
	)),
	SCYTHE(14, Arrays.asList(
		new AttackStyle(0, "Reap (Slash/Accurate)", AttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Chop (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Jab (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	SLASH_SWORD(9, Arrays.asList(
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Slash (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Lunge (Stab/Controlled)", AttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	SPEAR(15, Arrays.asList(
		new AttackStyle(0, "Lunge (Stab/Controlled)", AttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(1, "Swipe (Slash/Controlled)", AttackType.SLASH, CombatStyle.CONTROLLED, false),
		new AttackStyle(2, "Pound (Crush/Controlled)", AttackType.CRUSH, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	SPIKED(16, Arrays.asList(
		new AttackStyle(0, "Pound (Crush/Accurate)", AttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pummel (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Spike (Stab/Controlled)", AttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	STAB_SWORD(17, Arrays.asList(
		new AttackStyle(0, "Stab (Stab/Accurate)", AttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Lunge (Stab/Aggressive)", AttackType.STAB, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Slash (Slash/Aggressive)", AttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	STAFF(18, Arrays.asList(
		new AttackStyle(0, "Bash (Crush/Accurate)", AttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pound (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Focus (Crush/Defensive)", AttackType.CRUSH, CombatStyle.DEFENSIVE, false),
		new AttackStyle(4, "Spell (Magic/Autocast)", AttackType.MAGIC, CombatStyle.AUTOCAST, false)
	)),
	THROWN(19, Arrays.asList(
		new AttackStyle(0, "Accurate (Ranged/Accurate)", AttackType.RANGED, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Rapid (Ranged/Rapid)", AttackType.RANGED, CombatStyle.RAPID, false),
		new AttackStyle(3, "Longrange (Ranged/Longrange)", AttackType.RANGED, CombatStyle.LONGRANGE, false)
	)),
	UNARMED(0, Arrays.asList(
		new AttackStyle(0, "Punch (Crush/Accurate)", AttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Kick (Crush/Aggressive)", AttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	WHIP(20, Arrays.asList(
		new AttackStyle(0, "Flick (Slash/Accurate)", AttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Lash (Slash/Controlled)", AttackType.SLASH, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Deflect (Slash/Defensive)", AttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	;

	@Getter
	private final int varbValue;

	@Getter
	private final List<AttackStyle> attackStyles;

}