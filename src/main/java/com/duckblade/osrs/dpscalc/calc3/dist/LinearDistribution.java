package com.duckblade.osrs.dpscalc.calc3.dist;

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
public class LinearDistribution implements ContextValue<List<HitDistribution>>
{

	private final Accuracy accuracy;
	private final MaxHit maxHit;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return true;
	}

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		double acc = ctx.get(accuracy);
		int max = ctx.get(maxHit);

		return Collections.singletonList(of(acc, max));
	}

	public static HitDistribution of(double accuracy, int maxHit)
	{
		HitDistribution dist = new HitDistribution();
		for (int i = 0; i <= maxHit; i++)
		{
			dist.put(i, accuracy / (maxHit + 1));
		}

		dist.put(0, 1 - accuracy);
		return dist;
	}

}
