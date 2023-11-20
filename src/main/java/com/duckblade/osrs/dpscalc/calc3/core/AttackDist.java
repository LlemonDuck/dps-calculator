package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardAttackDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.AttackDistribution;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class AttackDist extends FirstContextValue<AttackDistribution>
{

	public static final String ATTACK_DIST_PROVIDERS = "HitDistProviders";

	public static final ComputeOutput<Integer> DIST_MAX = ComputeOutput.of("DistMaxHit");

	@Inject
	public AttackDist(
		@Named(ATTACK_DIST_PROVIDERS) List<ContextValue<AttackDistribution>> providers,
		StandardAttackDistribution standardAttackDistribution
	)
	{
		super(providers, standardAttackDistribution);
	}

	@Override
	public AttackDistribution compute(ComputeContext ctx)
	{
		AttackDistribution dist = super.compute(ctx);
		ctx.put(DIST_MAX, dist.getMax());
		return dist;
	}
}
