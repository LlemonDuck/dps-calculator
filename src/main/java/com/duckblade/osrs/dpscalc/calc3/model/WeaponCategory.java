package com.duckblade.osrs.dpscalc.calc3.model;

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
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Slash (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	AXE(1, Arrays.asList(
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Hack (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	BANNER(24, Arrays.asList(
		new AttackStyle(0, "Lunge (Stab/Accurate)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Swipe (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Pound (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	BLADED_STAFF(21, Arrays.asList(
		new AttackStyle(0, "Jab (Stab/Accurate)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Swipe (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Fend (Crush/Defensive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.DEFENSIVE, false),
		new AttackStyle(4, "Spell (Magic/Autocast)", AttackType.MAGIC, null, CombatStyle.AUTOCAST, false)
	)),
	BLUDGEON(26, Arrays.asList(
		new AttackStyle(0, "Pound (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(1, "Pummel (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false)
	)),
	BLUNT(2, Arrays.asList(
		new AttackStyle(0, "Pound (Crush/Accurate)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pummel (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	BOW(3, Arrays.asList(
		new AttackStyle(0, "Accurate (Ranged/Accurate)", AttackType.RANGED, null, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Rapid (Ranged/Rapid)", AttackType.RANGED, null, CombatStyle.RAPID, false),
		new AttackStyle(3, "Longrange (Ranged/Longrange)", AttackType.RANGED, null, CombatStyle.LONGRANGE, false)
	)),
	BULWARK(27, Collections.singletonList(
		new AttackStyle(0, "Pummel (Crush/Accurate)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.ACCURATE, false)
	)),
	CHINCHOMPAS(7, Arrays.asList(
		new AttackStyle(0, "Short fuse (Ranged/Accurate)", AttackType.RANGED, null, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Medium fuse (Ranged/Rapid)", AttackType.RANGED, null, CombatStyle.RAPID, false),
		new AttackStyle(3, "Long fuse (Ranged/Longrange)", AttackType.RANGED, null, CombatStyle.LONGRANGE, false)
	)),
	CLAW(4, Arrays.asList(
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Slash (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Lunge (Stab/Controlled)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	CROSSBOW(5, Arrays.asList(
		new AttackStyle(0, "Accurate (Ranged/Accurate)", AttackType.RANGED, null, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Rapid (Ranged/Rapid)", AttackType.RANGED, null, CombatStyle.RAPID, false),
		new AttackStyle(3, "Longrange (Ranged/Longrange)", AttackType.RANGED, null, CombatStyle.LONGRANGE, false)
	)),
	PARTISAN(29, Arrays.asList(
		new AttackStyle(0, "Stab (Stab/Accurate)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(0, "Lunge (Stab/Aggressive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(0, "Pound (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(0, "Block (Stab/Defensive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.ACCURATE, false)
	)),
	PICKAXE(11, Arrays.asList(
		new AttackStyle(0, "Spike (Stab/Accurate)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Impale (Stab/Aggressive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Smash (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	POLEARM(12, Arrays.asList(
		new AttackStyle(0, "Jab (Stab/Controlled)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(1, "Swipe (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Fend (Stab/Defensive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	POLESTAFF(18, Arrays.asList(
		new AttackStyle(0, "Bash (Crush/Accurate)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pound (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	POWERED_STAFF(23, Arrays.asList(
		new AttackStyle(0, "Accurate (Magic/Accurate)", AttackType.MAGIC, null, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Accurate (Magic/Accurate)", AttackType.MAGIC, null, CombatStyle.ACCURATE, false),
		new AttackStyle(3, "Longrange (Magic/Longrange)", AttackType.MAGIC, null, CombatStyle.LONGRANGE, false)
	)),
	SALAMANDER(6, Arrays.asList(
		new AttackStyle(0, "Scorch (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(1, "Flare (Ranged/Accurate)", AttackType.RANGED, null, CombatStyle.ACCURATE, false),
		new AttackStyle(2, "Blaze (Magic/Defensive)", AttackType.MAGIC, null, CombatStyle.DEFENSIVE, false)
	)),
	SCYTHE(14, Arrays.asList(
		new AttackStyle(0, "Reap (Slash/Accurate)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Chop (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Jab (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	SLASH_SWORD(9, Arrays.asList(
		new AttackStyle(0, "Chop (Slash/Accurate)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Slash (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Lunge (Stab/Controlled)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Slash/Defensive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	SPEAR(15, Arrays.asList(
		new AttackStyle(0, "Lunge (Stab/Controlled)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(1, "Swipe (Slash/Controlled)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.CONTROLLED, false),
		new AttackStyle(2, "Pound (Crush/Controlled)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	SPIKED(16, Arrays.asList(
		new AttackStyle(0, "Pound (Crush/Accurate)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pummel (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Spike (Stab/Controlled)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	STAB_SWORD(17, Arrays.asList(
		new AttackStyle(0, "Stab (Stab/Accurate)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Lunge (Stab/Aggressive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(2, "Slash (Slash/Aggressive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Stab/Defensive)", AttackType.MELEE, MeleeAttackType.STAB, CombatStyle.DEFENSIVE, false)
	)),
	STAFF(18, Arrays.asList(
		new AttackStyle(0, "Bash (Crush/Accurate)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Pound (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Focus (Crush/Defensive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.DEFENSIVE, false),
		new AttackStyle(4, "Spell (Magic/Autocast)", AttackType.MAGIC, null, CombatStyle.AUTOCAST, false)
	)),
	THROWN(19, Arrays.asList(
		new AttackStyle(0, "Accurate (Ranged/Accurate)", AttackType.RANGED, null, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Rapid (Ranged/Rapid)", AttackType.RANGED, null, CombatStyle.RAPID, false),
		new AttackStyle(3, "Longrange (Ranged/Longrange)", AttackType.RANGED, null, CombatStyle.LONGRANGE, false)
	)),
	UNARMED(0, Arrays.asList(
		new AttackStyle(0, "Punch (Crush/Accurate)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Kick (Crush/Aggressive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.AGGRESSIVE, false),
		new AttackStyle(3, "Block (Crush/Defensive)", AttackType.MELEE, MeleeAttackType.CRUSH, CombatStyle.DEFENSIVE, false)
	)),
	WHIP(20, Arrays.asList(
		new AttackStyle(0, "Flick (Slash/Accurate)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.ACCURATE, false),
		new AttackStyle(1, "Lash (Slash/Controlled)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.CONTROLLED, false),
		new AttackStyle(3, "Deflect (Slash/Defensive)", AttackType.MELEE, MeleeAttackType.SLASH, CombatStyle.DEFENSIVE, false)
	)),
	;

	@Getter
	private final int varbValue;

	@Getter
	private final List<AttackStyle> attackStyles;

}