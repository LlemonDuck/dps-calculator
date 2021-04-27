package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.CombatFocus;
import com.duckblade.osrs.dpscalc.model.Prayer;
import javax.inject.Singleton;
import net.runelite.api.Skill;

import static com.duckblade.osrs.dpscalc.calc.CalcUtil.*;

@Singleton
// https://oldschool.runescape.wiki/w/Damage_per_second/Melee
public class MeleeDpsCalc extends AbstractCalc
{

	private float gearBonus(CalcInput input)
	{
		int salveLevel = salveLevel(input);
		if (salveLevel == 2)
			return 6f / 5f;
		else if (salveLevel == 1 || blackMask(input))
			return 7f / 6f;
		else
			return 1f;
	}

	private int effectiveStrengthLevel(CalcInput input)
	{
		int str = input.getPlayerSkills().get(Skill.STRENGTH);
		str += input.getPlayerBoosts().get(Skill.STRENGTH);

		Prayer offensivePrayer = input.getOffensivePrayer();
		if (offensivePrayer != null)
			str = (int) (str * offensivePrayer.getStrengthMod());
		
		if (input.getWeaponMode().getCombatFocus() == CombatFocus.AGGRESSIVE)
			str += 3;
		else if (input.getWeaponMode().getCombatFocus() == CombatFocus.CONTROLLED)
			str += 1;
		str += 8;

		if (voidLevel(input) != 0)
			str = (int) (str * 1.1f);

		if (dragonHunter(input))
			str = (int) (str * 1.2f);

		str = (int) (str * leafyMod(input));

		if (obsidianArmour(input))
			str = (int) (str * 1.1f);

		if (obsidianNecklace(input))
			str = (int) (str * 1.2f); // obisidian bonuses stack
		
		int demonbane = demonbaneLevel(input);
		if (demonbane == 2)
			str = (int) (str * 1.7f);
		else if (demonbane == 1)
			str = (int) (str * 1.6f);

		str = (int) (str * inquisitorsMod(input));

		return str;
	}

	private int effectiveAttackLevel(CalcInput input)
	{
		int att = input.getPlayerSkills().get(Skill.ATTACK);
		att += input.getPlayerBoosts().get(Skill.ATTACK);

		Prayer offensivePrayer = input.getOffensivePrayer();
		if (offensivePrayer != null)
			att = (int) (att * offensivePrayer.getAttackMod());

		if (input.getWeaponMode().getCombatFocus() == CombatFocus.ACCURATE)
			att += 3;
		else if (input.getWeaponMode().getCombatFocus() == CombatFocus.CONTROLLED)
			att += 1;
		att += 8;

		if (voidLevel(input) != 0)
			att = (int) (att * 1.1f);

		if (dragonHunter(input))
			att = (int) (att * 1.2f);

		att = (int) (att * leafyMod(input));

		if (obsidianArmour(input))
			att = (int) (att * 1.1f); // no necklace, accuracy penalty is done in item stats

		att = (int) (att * inquisitorsMod(input));

		if (demonbaneLevel(input) == 2)
			att = (int) (att * 1.7f);

		return att;
	}

	public int maxHit(CalcInput input)
	{
		int maxHit = effectiveStrengthLevel(input) * (input.getEquipmentStats().getStrengthMelee() + 64);
		maxHit += 320;
		maxHit /= 640;

		return (int) (maxHit * gearBonus(input));
	}

	public int attackRoll(CalcInput input)
	{
		int attRoll = effectiveAttackLevel(input);
		switch (input.getWeaponMode().getMeleeStyle())
		{
			case STAB:
				attRoll *= input.getEquipmentStats().getAccuracyStab() + 64;
				break;
			case SLASH:
				attRoll *= input.getEquipmentStats().getAccuracySlash() + 64;
				break;
			case CRUSH:
				attRoll *= input.getEquipmentStats().getAccuracyCrush() + 64;
				break;
			default:
				throw new IllegalArgumentException("Invalid melee attack style");
		}

		return (int) (attRoll * gearBonus(input));
	}

	public int defenseRoll(CalcInput input)
	{
		int defLevel = input.getNpcTarget().getLevelDefense() + 9;
		switch (input.getWeaponMode().getMeleeStyle())
		{
			case STAB:
				return defLevel * (input.getNpcTarget().getDefenseStab() + 64);
			case SLASH:
				return defLevel * (input.getNpcTarget().getDefenseSlash() + 64);
			case CRUSH:
				return defLevel * (input.getNpcTarget().getDefenseCrush() + 64);
			default:
				throw new IllegalArgumentException("Invalid melee attack style");
		}
	}

}
