package com.duckblade.osrs.dpscalc.calc3.dist.transformers;

import com.duckblade.osrs.dpscalc.calc3.dist.BaseHitDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.Optional;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class TransformedHitDistribution implements ContextValue<List<HitDistribution>>
{

	public static final String TRANSFORMERS = "HitDistributionTransformers";

	public interface Transformer extends Optional
	{
		List<HitDistribution> apply(ComputeContext ctx, List<HitDistribution> previous);
	}

	private final BaseHitDistribution baseDistribution;
	private final List<Transformer> transformers;

	@Inject
	public TransformedHitDistribution(
		BaseHitDistribution baseDistribution,
		@Named(TRANSFORMERS) List<Transformer> transformers
	)
	{
		this.baseDistribution = baseDistribution;
		this.transformers = transformers;
	}

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		List<HitDistribution> dist = ctx.get(baseDistribution);

		for (Transformer transformer : transformers)
		{
			if (transformer.isApplicable(ctx))
			{
				dist = transformer.apply(ctx, dist);
			}
		}

		return dist;
	}
}
