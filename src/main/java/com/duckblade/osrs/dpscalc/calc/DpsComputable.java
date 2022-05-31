package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.util.TicksToDurationComputable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DpsComputable implements Computable<Double>
{

	private static final double SECONDS_PER_TICK = (TicksToDurationComputable.MILLIS_PER_TICK / 1000.0);

	private final DptComputable dptComputable;

	@Override
	public Double compute(ComputeContext context)
	{
		return context.get(dptComputable) / SECONDS_PER_TICK;
	}

}
