package com.duckblade.osrs.dpscalc.calc3.meta.context;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class FirstContextValue<T> implements ContextValue<T>
{

	private final List<ContextValue<T>> providers;
	private final ContextValue<T> fallback;

	@Override
	public T compute(ComputeContext ctx)
	{
		for (ContextValue<T> provider : providers)
		{
			if (ctx.isApplicable(provider))
			{
				return ctx.get(provider);
			}
		}

		return ctx.get(fallback);
	}

}
