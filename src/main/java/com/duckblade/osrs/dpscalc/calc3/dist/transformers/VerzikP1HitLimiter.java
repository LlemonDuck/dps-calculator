package com.duckblade.osrs.dpscalc.calc3.dist.transformers;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VerzikP1HitLimiter implements TransformedHitDistribution.Transformer
{

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		// todo
		return true;
	}

	@Override
	public List<HitDistribution> apply(ComputeContext ctx, List<HitDistribution> previous)
	{
		return previous.stream()
			.map(this::flatten)
			.collect(Collectors.toList());
	}

	private HitDistribution flatten(HitDistribution previous)
	{
		HitDistribution dist = new HitDistribution();
		double accFactor = 1.0 / 11.0;
		for (Map.Entry<List<Integer>, Double> entry : previous.getEntries())
		{
			List<Integer> hit = entry.getKey();
			double prob = entry.getValue();

			for (int i = 0; i <= 10; i++)
			{
				dist.put(Math.min(i, hit), prob * accFactor);
			
		}

		return dist;
	}

}
