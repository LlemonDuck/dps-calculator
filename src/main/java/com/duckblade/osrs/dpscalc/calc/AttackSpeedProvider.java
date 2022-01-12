package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.CombatMode;
import com.duckblade.osrs.dpscalc.model.Spell;
import javax.inject.Singleton;

@Singleton
public class AttackSpeedProvider
{

	public int getWeaponSpeed(CalcInput input)
	{
		if (input.getCombatMode() == CombatMode.MAGE)
		{
			return getMagicSpeed(input);
		}

		return input.getEquipmentStats().getSpeed();
	}

	private int getMagicSpeed(CalcInput input)
	{
		if (input.getSpell().getSpellbook() == Spell.Spellbook.POWERED_STAVES)
		{
			return input.getEquipmentStats().getSpeed();
		}

		if (EquipmentRequirement.HARM_STAFF.isSatisfied(input))
		{
			return 4;
		}

		return 5;
	}

}