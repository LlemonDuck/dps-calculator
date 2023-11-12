package com.duckblade.osrs.dpscalc.calc3.meta.context;

public interface ContextValue<T> extends Optional
{

	default String key()
	{
		String stripSuffix = "Computable";
		String key = getClass().getSimpleName();
		if (key.endsWith(stripSuffix))
		{
			key = key.substring(0, key.length() - stripSuffix.length());
		}

		return key;
	}

	T compute(ComputeContext ctx);

}
