package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.maxhit.MeleeMaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MaxHit implements ContextValue<Integer>
{

	private final MeleeMaxHit meleeMaxHit;

	@Override
	public Integer compute(ComputeContext ctx)
	{
		switch (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MELEE:
				return ctx.get(meleeMaxHit);

			default:
				throw new RuntimeException("Not implemented");
		}
	}

}
