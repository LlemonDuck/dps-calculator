package com.duckblade.osrs.dpscalc.calc3.dist;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class BaseHitDistribution implements ContextValue<List<HitDistribution>>
{

	public static final String WEAPON_HIT_DISTRIBUTIONS = "WeaponHitDistributions";

	private final List<ContextValue<List<HitDistribution>>> weaponDistributions;

	@Inject
	public BaseHitDistribution(
		@Named(WEAPON_HIT_DISTRIBUTIONS) List<ContextValue<List<HitDistribution>>> weaponDistributions
	)
	{
		this.weaponDistributions = weaponDistributions;
	}

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		for (ContextValue<List<HitDistribution>> weaponDistribution : weaponDistributions)
		{
			if (weaponDistribution.isApplicable(ctx))
			{
				return ctx.get(weaponDistribution);
			}
		}

		throw new IllegalStateException("Failed to find a weapon hit distribution, is LinearDistribution in the Guice module?");
	}

}
