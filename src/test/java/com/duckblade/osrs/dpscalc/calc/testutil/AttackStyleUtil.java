package com.duckblade.osrs.dpscalc.calc.testutil;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;

public class AttackStyleUtil
{

	public static AttackStyle ofAttackType(AttackType attackType)
	{
		return AttackStyle.builder()
			.attackType(attackType)
			.build();
	}

}
