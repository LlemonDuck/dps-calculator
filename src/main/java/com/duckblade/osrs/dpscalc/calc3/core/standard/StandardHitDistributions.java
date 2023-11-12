package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardHitDistributions implements ContextValue<List<HitDistribution>>
{

	private final Accuracy accuracy;
	private final MaxHit maxHit;

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		return Collections.singletonList(
			HitDistribution.linear(
				ctx.get(accuracy),
				ctx.get(maxHit)
			)
		);
	}

}
