package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Dps implements ContextValue<Double>
{

	private final Dpt dpt;

	@Override
	public Double compute(ComputeContext ctx)
	{
		return ctx.get(dpt) / 0.6;
	}

}
