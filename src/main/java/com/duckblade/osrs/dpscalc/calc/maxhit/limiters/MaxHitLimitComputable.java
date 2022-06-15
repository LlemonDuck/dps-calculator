package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
import java.util.Comparator;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MaxHitLimitComputable implements Computable<MaxHitLimit>
{

	public static final ComputeOutput<Boolean> LIMIT_APPLIED = ComputeOutput.of("MaxHitLimitApplied");

	private final Set<MaxHitLimiter> maxHitLimiters;

	@Override
	public MaxHitLimit compute(ComputeContext context)
	{
		context.put(LIMIT_APPLIED, false);
		return maxHitLimiters.stream()
			.filter(mhl -> mhl.isApplicable(context))
			.map(context::get)
			.min(Comparator.comparing(MaxHitLimit::getLimit))
			.orElse(MaxHitLimit.UNLIMITED);
	}

	public int coerce(int maxHit, ComputeContext context)
	{
		MaxHitLimit limit = context.get(this);
		if (limit.getLimit() < maxHit)
		{
			if (!context.get(LIMIT_APPLIED))
			{
				context.put(LIMIT_APPLIED, true);
				context.warn(limit.getWarning());
			}
			return limit.getLimit();
		}

		return maxHit;
	}
}
