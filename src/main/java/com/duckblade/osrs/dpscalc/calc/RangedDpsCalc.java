package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.CombatFocus;
import com.duckblade.osrs.dpscalc.model.Prayer;
import javax.inject.Singleton;
import net.runelite.api.Skill;

import static com.duckblade.osrs.dpscalc.calc.CalcUtil.*;

@Singleton
// https://oldschool.runescape.wiki/w/Damage_per_second/Ranged
public class RangedDpsCalc extends AbstractCalc
{

	private float gearBonus(CalcInput input)
	{
		int salveLevel = salveLevel(input);
		if (salveLevel == 2)
			return 6f / 5f;
		else if (salveLevel == 1)
			return 7f / 6f;
		else if (blackMask(input))
			return 1.15f;
		return 1f;
	}

	private int effectiveRangedStrength(CalcInput input)
	{
		int rngStrength = input.getPlayerSkills().get(Skill.RANGED) + input.getPlayerBoosts().get(Skill.RANGED);

		Prayer offensivePrayer = input.getOffensivePrayer();
		if (offensivePrayer != null)
			rngStrength = (int) (rngStrength * offensivePrayer.getStrengthMod());

		if (input.getWeaponMode().getCombatFocus() == CombatFocus.ACCURATE)
			rngStrength += 3;
		rngStrength += 8;

		float voidLevel = voidLevel(input);
		if (voidLevel == 2)
			rngStrength = (int) (rngStrength * 1.125f);
		else if (voidLevel == 1)
			rngStrength = (int) (rngStrength * 1.125f);

		if (dragonHunter(input))
			rngStrength = (int) (rngStrength * 1.3f);

		// unsure whether this should be here or in effectiveRangedStrength
		// unlike tbow accuracy, this one could make a difference
		if (EquipmentRequirement.TBOW.isSatisfied(input))
			rngStrength = (int) (rngStrength * tbowDmgModifier(input));

		return rngStrength;
	}

	public int maxHit(CalcInput input)
	{
		int maxHit = effectiveRangedStrength(input);
		maxHit *= (input.getEquipmentStats().getStrengthRanged() + 64);
		maxHit += 320;
		maxHit /= 640;

		maxHit = (int) (maxHit * gearBonus(input));

		maxHit = (int) (maxHit * leafyMod(input));

		if (input.getNpcTarget().getName().contains("Zulrah"))
			maxHit = Math.min(maxHit, 50);

		return maxHit;
	}

	private int effectiveRangedAttack(CalcInput input)
	{
		// note that this method is the same as effectiveRangedStrength,
		// other than the voidMod
		// prayer boost for rigour will also be different when implemented
		int rngAttack = input.getPlayerSkills().get(Skill.RANGED) + input.getPlayerBoosts().get(Skill.RANGED);

		Prayer offensivePrayer = input.getOffensivePrayer();
		if (offensivePrayer != null)
			rngAttack = (int) (rngAttack * offensivePrayer.getAttackMod());

		if (input.getWeaponMode().getCombatFocus() == CombatFocus.ACCURATE)
			rngAttack += 3;
		rngAttack += 8;

		if (voidLevel(input) != 0)
			rngAttack = (int) (rngAttack * 1.1f);

		if (dragonHunter(input))
			rngAttack = (int) (rngAttack * 1.3f);

		rngAttack = (int) (rngAttack * leafyMod(input));

		return rngAttack;
	}

	public int attackRoll(CalcInput input)
	{
		int attRoll = effectiveRangedAttack(input);
		attRoll *= (input.getEquipmentStats().getAccuracyRanged() + 64);
		attRoll = (int) (attRoll * gearBonus(input));

		// unsure whether this should be here or in effectiveRangedAttack
		// or even whether it matters (i suspect it doesn't besides minor rounding differences)
		if (EquipmentRequirement.TBOW.isSatisfied(input))
			attRoll = (int) (attRoll * tbowAttModifier(input));

		return attRoll;
	}

	public int defenseRoll(CalcInput input)
	{
		int defRoll = input.getNpcTarget().getLevelDefense() + 9;
		defRoll *= (input.getNpcTarget().getDefenseRanged() + 64);
		return defRoll;
	}

}
