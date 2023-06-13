package com.duckblade.osrs.dpscalc.calc3.prayer;

import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Operation;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.Prayer;

public class PrayerBonus implements GearBonusOperation
{

	private static final ComputeOutput<Operation> ACCURACY_MOD = ComputeOutput.of("PrayerModAccuracy");
	private static final ComputeOutput<Operation> STRENGTH_MOD = ComputeOutput.of("PrayerModStrength");

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		AttackType at = ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType();
		Operation accMod = null;
		Operation strMod = null;
		for (Prayer p : ctx.get(ComputeInputs.ATTACKER_PRAYERS))
		{
			if (p.getAttackType() != at)
			{
				continue;
			}

			if (p.getAccuracyMod() != null)
			{
				accMod = p.getAccuracyMod();
			}
			if (p.getStrengthMod() != null)
			{
				strMod = p.getStrengthMod();
			}
		}

		ctx.put(ACCURACY_MOD, accMod);
		ctx.put(STRENGTH_MOD, strMod);
		return accMod != null || strMod != null;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		return new GearBonus(
			ctx.get(ACCURACY_MOD),
			ctx.get(STRENGTH_MOD)
		);
	}

}