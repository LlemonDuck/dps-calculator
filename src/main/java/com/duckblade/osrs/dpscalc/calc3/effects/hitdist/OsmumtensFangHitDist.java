package com.duckblade.osrs.dpscalc.calc3.effects.hitdist;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class OsmumtensFangHitDist implements ContextValue<List<HitDistribution>>
{

	private final Accuracy accuracy;
	private final MaxHit maxHit;

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		int max = ctx.get(maxHit);
		return Collections.singletonList(
			HitDistribution.linear(
				ctx.get(accuracy),
				max * 3 / 20,
				max * 17 / 20
			)
		);
	}
}
