package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.dist.transformers.TransformedHitDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Dpt implements ContextValue<Double>
{

	private final TransformedHitDistribution hitDistribution;
	private final Weapon weapon;

	@Override
	public Double compute(ComputeContext ctx)
	{
		int speed = ctx.get(weapon).getSpeed();

		double dpt = 0;
		for (HitDistribution splat : ctx.get(hitDistribution))
		{
			dpt += splat.getAverageHit() / speed;
		}

		return dpt;
	}

}
