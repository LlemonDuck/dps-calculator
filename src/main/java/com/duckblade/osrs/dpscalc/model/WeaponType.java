package com.duckblade.osrs.dpscalc.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// https://oldschool.runescape.wiki/w/Module:CombatStyles
public enum WeaponType
{

	TWO_HANDED_SWORD(Arrays.asList(
			new WeaponMode("Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode("Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	AXE(Arrays.asList(
			new WeaponMode("Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode("Hack (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	BANNER(Arrays.asList(
			new WeaponMode("Lunge (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode("Swipe (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	BLADED_STAFF(Arrays.asList(
			new WeaponMode("Jab (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode("Swipe (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Fend (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Spell (Magic/Autocast)", CombatMode.MAGE, CombatFocus.AUTOCAST, null)
	)),
	BLUDGEON(Arrays.asList(
			new WeaponMode("Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Pummel (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH)
	)),
	BLUNT(Arrays.asList(
			new WeaponMode("Pound (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode("Pummel (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	BOW(Arrays.asList(
			new WeaponMode("Accurate (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode("Rapid (Ranged/Rapid)", CombatMode.RANGED, CombatFocus.RAPID, null),
			new WeaponMode("Longrange (Ranged/Longrange)", CombatMode.RANGED, CombatFocus.LONGRANGE, null)
	)),
	BULWARK(Collections.singletonList(
			new WeaponMode("Pummel (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH)
	)),
	CHINCHOMPAS(Collections.emptyList()),
	CLAW(Arrays.asList(
			new WeaponMode("Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode("Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Lunge (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode("Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	CROSSBOW(Arrays.asList(
			new WeaponMode("Accurate (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode("Rapid (Ranged/Rapid)", CombatMode.RANGED, CombatFocus.RAPID, null),
			new WeaponMode("Longrange (Ranged/Longrange)", CombatMode.RANGED, CombatFocus.LONGRANGE, null)
	)),
	PICKAXE(Arrays.asList(
			new WeaponMode("Spike (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode("Impale (Stab/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.STAB),
			new WeaponMode("Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	POLEARM(Arrays.asList(
			new WeaponMode("Jab (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode("Swipe (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Fend (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	POLESTAFF(Arrays.asList(
			new WeaponMode("Bash (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode("Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	POWERED_STAFF(Arrays.asList(
			new WeaponMode("Accurate (Magic/Accurate)", CombatMode.MAGE, CombatFocus.ACCURATE, null),
			new WeaponMode("Accurate (Magic/Accurate)", CombatMode.MAGE, CombatFocus.ACCURATE, null),
			new WeaponMode("Longrange (Magic/Longrange)", CombatMode.MAGE, CombatFocus.LONGRANGE, null)
	)),
	SALAMANDER(Arrays.asList(
			new WeaponMode("Scorch (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Flare (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode("Blaze (Magic/Defensive)", CombatMode.MAGE, CombatFocus.DEFENSIVE, null)
	)),
	SCYTHE(Arrays.asList(
			new WeaponMode("Reap (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode("Chop (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Jab (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	SLASH_SWORD(Arrays.asList(
			new WeaponMode("Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode("Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Lunge (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode("Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	SPEAR(Arrays.asList(
			new WeaponMode("Lunge (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode("Swipe (Slash/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.SLASH),
			new WeaponMode("Pound (Crush/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.CRUSH),
			new WeaponMode("Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	SPIKED(Arrays.asList(
			new WeaponMode("Pound (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode("Pummel (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Spike (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode("Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	STAB_SWORD(Arrays.asList(
			new WeaponMode("Stab (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode("Lunge (Stab/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.STAB),
			new WeaponMode("Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode("Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	STAFF(Arrays.asList(
			new WeaponMode("Bash (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode("Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Focus (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Spell (Magic/Autocast)", CombatMode.MAGE, CombatFocus.AUTOCAST, null)
	)),
	THROWN(Arrays.asList(
			new WeaponMode("Accurate (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode("Rapid (Ranged/Rapid)", CombatMode.RANGED, CombatFocus.RAPID, null),
			new WeaponMode("Longrange (Ranged/Longrange)", CombatMode.RANGED, CombatFocus.LONGRANGE, null)
	)),
	UNARMED(Arrays.asList(
			new WeaponMode("Punch (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode("Kick (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode("Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	WHIP(Arrays.asList(
			new WeaponMode("Flick (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode("Lash (Slash/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.SLASH),
			new WeaponMode("Deflect (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	;

	@Getter
	private final List<WeaponMode> weaponModes;

}
