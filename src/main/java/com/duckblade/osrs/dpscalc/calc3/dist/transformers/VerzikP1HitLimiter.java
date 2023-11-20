package com.duckblade.osrs.dpscalc.calc3.dist.transformers;

import com.duckblade.osrs.dpscalc.calc3.dist.DragonClawsDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.HitDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.HitTransformer;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VerzikP1HitLimiter implements ContextValue<List<HitDistribution>>
{

	@Inject
	private DragonClawsDistribution scythe;

	// we flatten to 10 first to limit the zipping width on multi-hits
	private static final HitTransformer FLATTENER = new HitTransformer.LimiterFlat(10);
	private static final HitTransformer LIMITER = new HitTransformer.LimiterLinearMin(10);

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return true;
	}

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		List<HitDistribution> previous = ctx.get(scythe);
		return previous.parallelStream()
			.map(d -> d.transform(FLATTENER).flattenDuplicates().transform(LIMITER))
			.collect(Collectors.toList());
	}

}
