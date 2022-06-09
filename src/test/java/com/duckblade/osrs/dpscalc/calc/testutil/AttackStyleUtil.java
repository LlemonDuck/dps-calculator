package com.duckblade.osrs.dpscalc.calc.testutil;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;

public class AttackStyleUtil
{

	public static AttackStyle ofAttackType(AttackType attackType)
	{
		return AttackStyle.builder()
			.attackType(attackType)
			.build();
	}

	public static AttackStyle ofCombatStyle(CombatStyle combatStyle)
	{
		return AttackStyle.builder()
			.combatStyle(combatStyle)
			.build();
	}

}
