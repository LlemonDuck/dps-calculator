package com.duckblade.osrs.dpscalc.calc.util;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import java.time.Duration;

public abstract class TicksToDurationComputable implements Computable<Duration>
{

	public static final long MILLIS_PER_TICK = 600;

	protected abstract int getTicks(ComputeContext context);

	@Override
	public Duration compute(ComputeContext context)
	{
		int ticks = getTicks(context);
		if (ticks < 0)
		{
			return null;
		}

		return Duration.ofMillis(ticks * MILLIS_PER_TICK);
	}
}
