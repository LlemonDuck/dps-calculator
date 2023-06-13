package com.duckblade.osrs.dpscalc.calc3.meta.math;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;

public interface ContextOperation extends ContextValue<Operation>
{

	default int apply(ComputeContext ctx, int previous)
	{
		Operation op;
		if (this.isApplicable(ctx) && (op = ctx.get(this)) != null)
		{
			return op.apply(previous);
		}

		return previous;
	}

}
