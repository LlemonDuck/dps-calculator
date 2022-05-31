package com.duckblade.osrs.dpscalc.calc.compute;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class ComputeOutput<T> implements Computable<T>
{

	private final String key;

	@Override
	public String key()
	{
		return this.key;
	}

	@Override
	public T compute(ComputeContext context)
	{
		// outputs are placed manually into the context by other computations
		// missing outputs may be requested, we just always return a non-result
		return null;
	}

}
