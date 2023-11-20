package com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes;

import java.util.Collections;
import lombok.RequiredArgsConstructor;

public abstract class HitTransformer
{

	public abstract HitDistribution transform(int hitsplat);

	@RequiredArgsConstructor
	public static class LimiterFlat extends HitTransformer
	{

		private final int limit;

		@Override
		public HitDistribution transform(int hitsplat)
		{
			return HitDistribution.single(1.0, Math.min(hitsplat, this.limit));
		}
	}

	@RequiredArgsConstructor
	public static class LimiterLinearMin extends HitTransformer
	{

		private final int maxLimit;

		@Override
		public HitDistribution transform(int hitsplat)
		{
			double constProb = 1.0 / (maxLimit + 1);

			HitDistribution d = new HitDistribution();
			for (int limit = 0; limit <= maxLimit; limit++)
			{
				d.addOutcome(new WeightedHit(
					constProb,
					Collections.singletonList(Math.min(hitsplat, limit))
				));
			}
			return d.flattenDuplicates();
		}

	}

}
