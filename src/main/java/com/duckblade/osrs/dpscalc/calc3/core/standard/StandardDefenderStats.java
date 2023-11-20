package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.DefensiveStats;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardDefenderStats implements ContextValue<DefensiveStats>
{

	@Override
	public DefensiveStats compute(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.DEFENDER_STATS);
	}

}
