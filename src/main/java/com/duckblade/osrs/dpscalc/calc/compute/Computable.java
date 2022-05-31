package com.duckblade.osrs.dpscalc.calc.compute;

public interface Computable<T>
{

	default String key()
	{
		String key = getClass().getSimpleName();
		if (key.endsWith("Computable"))
		{
			key = key.substring(0, key.length() - 10);
		}

		return key;
	}

	T compute(ComputeContext context);

}
