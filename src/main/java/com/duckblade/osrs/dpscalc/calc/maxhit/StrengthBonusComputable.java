package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class StrengthBonusComputable implements Computable<Double>
{

	private final AttackerItemStatsComputable attackerItemStatsComputable;

	@Override
	public Double compute(ComputeContext context)
	{
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		ItemStats itemStats = context.get(attackerItemStatsComputable);
		switch (attackStyle.getAttackType())
		{
			case MAGIC:
				return itemStats.getStrengthMagic();

			case RANGED:
				return (double) itemStats.getStrengthRanged();

			default:
				return (double) itemStats.getStrengthMelee();
		}
	}
}
