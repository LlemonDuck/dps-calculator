package com.duckblade.osrs.dpscalc.graphing;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import java.util.HashMap;
import java.util.Map;

public class GraphingComputeContext extends ComputeContext
{

	private final Map<String, Computable<?>> instances = new HashMap<>();

	@Override
	public <T> T get(Computable<T> computable)
	{
		instances.put(computable.key(), computable);
		return super.get(computable);
	}

	Object getRaw(String key)
	{
		if (instances.containsKey(key))
		{
			return get(instances.get(key));
		}
		else
		{
			return null;
		}
	}

}
