package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;

public class Accuracy implements ContextValue<Double>
{

	// todo
//	private final AttackRoll attackRoll;
//	private final DefenseRoll defenseRoll;

	@Override
	public Double compute(ComputeContext ctx)
	{
//		int baseAttackRoll = ctx.get(attackRoll);
//		int baseDefenseRoll = ctx.get(defenseRoll);
		double baseAttackRoll = 18618;
		double baseDefenseRoll = 640;

		return baseAttackRoll > baseDefenseRoll
			? 1 - ((baseDefenseRoll + 2) / (2 * (baseAttackRoll + 1)))
			: baseAttackRoll / (2 * (baseDefenseRoll + 1));
	}

}
