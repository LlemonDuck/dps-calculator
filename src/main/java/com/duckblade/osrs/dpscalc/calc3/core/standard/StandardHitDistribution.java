package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.HitDistribution;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardHitDistribution implements ContextValue<HitDistribution>
{

	private final Accuracy accuracy;
	private final MaxHit maxHit;

	@Override
	public HitDistribution compute(ComputeContext ctx)
	{
		return HitDistribution.linear(
			ctx.get(accuracy),
			ctx.get(maxHit)
		);
	}

}
