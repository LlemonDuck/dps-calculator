package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.maxhit.MaxHitComputable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BaseHitDptComputable implements Computable<Double>
{

	private static final double SECONDS_PER_TICK = 0.6;

	private final HitChanceComputable hitChanceComputable;
	private final MaxHitComputable maxHitComputable;
	private final AttackSpeedComputable attackSpeedComputable;

	@Override
	public Double compute(ComputeContext context)
	{
		double hitChance = context.get(hitChanceComputable);
		int maxHit = context.get(maxHitComputable);
		int attackSpeed = context.get(attackSpeedComputable);

		return byComponents(hitChance, maxHit, attackSpeed);
	}

	public static double byComponents(double hitChance, int maxHit, int attackSpeed)
	{
		return (hitChance * maxHit) / (2.0 * attackSpeed);
	}

}
