package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardHitDistributions;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class HitDistributions extends FirstContextValue<List<HitDistribution>>
{

	public static final String HIT_DIST_PROVIDERS = "HitDistProviders";

	@Inject
	public HitDistributions(
		@Named(HIT_DIST_PROVIDERS) List<ContextValue<List<HitDistribution>>> providers,
		StandardHitDistributions standardHitDistributions
	)
	{
		super(providers, standardHitDistributions);
	}

}
