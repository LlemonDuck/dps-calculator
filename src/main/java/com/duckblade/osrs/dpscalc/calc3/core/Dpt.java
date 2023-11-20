package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Dpt implements ContextValue<Double>
{

	private final Weapon weapon;
	private final AttackDist attackDist;

	@Override
	public Double compute(ComputeContext ctx)
	{
		int speed = ctx.get(weapon).getSpeed();
		double expectedHit = ctx.get(attackDist).expectedDamage();

		return expectedHit / speed;
	}

}
