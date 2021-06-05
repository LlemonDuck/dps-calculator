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

	TWO_HANDED_SWORD(10, Arrays.asList(
			new WeaponMode(0, "Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode(1, "Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(2, "Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	AXE(1, Arrays.asList(
			new WeaponMode(0, "Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode(1, "Hack (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(2, "Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	BANNER(24, Arrays.asList(
			new WeaponMode(0, "Lunge (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode(1, "Swipe (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(2, "Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	BLADED_STAFF(21, Arrays.asList(
			new WeaponMode(0, "Jab (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode(1, "Swipe (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(3, "Fend (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH),
			new WeaponMode(4, "Spell (Magic/Autocast)", CombatMode.MAGE, CombatFocus.AUTOCAST, null)
	)),
	BLUDGEON(26, Arrays.asList(
			new WeaponMode(0, "Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(1, "Pummel (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(2, "Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH)
	)),
	BLUNT(2, Arrays.asList(
			new WeaponMode(0, "Pound (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode(1, "Pummel (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	BOW(3, Arrays.asList(
			new WeaponMode(0, "Accurate (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode(1, "Rapid (Ranged/Rapid)", CombatMode.RANGED, CombatFocus.RAPID, null),
			new WeaponMode(3, "Longrange (Ranged/Longrange)", CombatMode.RANGED, CombatFocus.LONGRANGE, null)
	)),
	BULWARK(27, Collections.singletonList(
			new WeaponMode(0, "Pummel (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH)
	)),
	CHINCHOMPAS(7, Collections.emptyList()),
	CLAW(4, Arrays.asList(
			new WeaponMode(0, "Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode(1, "Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(2, "Lunge (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode(3, "Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	CROSSBOW(5, Arrays.asList(
			new WeaponMode(0, "Accurate (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode(1, "Rapid (Ranged/Rapid)", CombatMode.RANGED, CombatFocus.RAPID, null),
			new WeaponMode(3, "Longrange (Ranged/Longrange)", CombatMode.RANGED, CombatFocus.LONGRANGE, null)
	)),
	PICKAXE(11, Arrays.asList(
			new WeaponMode(0, "Spike (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode(1, "Impale (Stab/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.STAB),
			new WeaponMode(2, "Smash (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	POLEARM(12, Arrays.asList(
			new WeaponMode(0, "Jab (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode(1, "Swipe (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(3, "Fend (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	POLESTAFF(18, Arrays.asList(
			new WeaponMode(0, "Bash (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode(1, "Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	POWERED_STAFF(23, Arrays.asList(
			new WeaponMode(0, "Accurate (Magic/Accurate)", CombatMode.MAGE, CombatFocus.ACCURATE, null),
			new WeaponMode(1, "Accurate (Magic/Accurate)", CombatMode.MAGE, CombatFocus.ACCURATE, null),
			new WeaponMode(3, "Longrange (Magic/Longrange)", CombatMode.MAGE, CombatFocus.LONGRANGE, null)
	)),
	SALAMANDER(6, Arrays.asList(
			new WeaponMode(0, "Scorch (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(1, "Flare (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode(2, "Blaze (Magic/Defensive)", CombatMode.MAGE, CombatFocus.DEFENSIVE, null)
	)),
	SCYTHE(14, Arrays.asList(
			new WeaponMode(0, "Reap (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode(1, "Chop (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(2, "Jab (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	SLASH_SWORD(9, Arrays.asList(
			new WeaponMode(0, "Chop (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode(1, "Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(2, "Lunge (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode(3, "Block (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	SPEAR(15, Arrays.asList(
			new WeaponMode(0, "Lunge (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode(1, "Swipe (Slash/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.SLASH),
			new WeaponMode(2, "Pound (Crush/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	SPIKED(16, Arrays.asList(
			new WeaponMode(0, "Pound (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode(1, "Pummel (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(2, "Spike (Stab/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.STAB),
			new WeaponMode(3, "Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	STAB_SWORD(17, Arrays.asList(
			new WeaponMode(0, "Stab (Stab/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.STAB),
			new WeaponMode(1, "Lunge (Stab/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.STAB),
			new WeaponMode(2, "Slash (Slash/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.SLASH),
			new WeaponMode(3, "Block (Stab/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.STAB)
	)),
	STAFF(18, Arrays.asList(
			new WeaponMode(0, "Bash (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode(1, "Pound (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Focus (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH),
			new WeaponMode(4, "Spell (Magic/Autocast)", CombatMode.MAGE, CombatFocus.AUTOCAST, null)
	)),
	THROWN(19, Arrays.asList(
			new WeaponMode(0, "Accurate (Ranged/Accurate)", CombatMode.RANGED, CombatFocus.ACCURATE, null),
			new WeaponMode(1, "Rapid (Ranged/Rapid)", CombatMode.RANGED, CombatFocus.RAPID, null),
			new WeaponMode(3, "Longrange (Ranged/Longrange)", CombatMode.RANGED, CombatFocus.LONGRANGE, null)
	)),
	UNARMED(0, Arrays.asList(
			new WeaponMode(0, "Punch (Crush/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.CRUSH),
			new WeaponMode(1, "Kick (Crush/Aggressive)", CombatMode.MELEE, CombatFocus.AGGRESSIVE, MeleeStyle.CRUSH),
			new WeaponMode(3, "Block (Crush/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.CRUSH)
	)),
	WHIP(20, Arrays.asList(
			new WeaponMode(0, "Flick (Slash/Accurate)", CombatMode.MELEE, CombatFocus.ACCURATE, MeleeStyle.SLASH),
			new WeaponMode(1, "Lash (Slash/Controlled)", CombatMode.MELEE, CombatFocus.CONTROLLED, MeleeStyle.SLASH),
			new WeaponMode(3, "Deflect (Slash/Defensive)", CombatMode.MELEE, CombatFocus.DEFENSIVE, MeleeStyle.SLASH)
	)),
	;

	@Getter
	private final int varbValue;
	
	@Getter
	private final List<WeaponMode> weaponModes;
	
	public static WeaponType forVarb(int varbValue)
	{
		for (WeaponType type : values())
		{
			if (type.getVarbValue() == varbValue)
				return type;
		}
		
		return null;
	}

}
