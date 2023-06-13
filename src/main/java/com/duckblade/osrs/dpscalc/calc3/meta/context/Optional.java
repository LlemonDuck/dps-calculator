package com.duckblade.osrs.dpscalc.calc3.meta.context;

public interface Optional
{

	default boolean isApplicable(ComputeContext ctx)
	{
		return true;
	}

}
