package com.duckblade.osrs.dpscalc.calc3.meta.context;

public interface ContextValue<T> extends Optional
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

	T compute(ComputeContext ctx);

}
