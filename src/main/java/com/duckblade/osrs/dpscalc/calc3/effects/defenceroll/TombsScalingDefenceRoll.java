package com.duckblade.osrs.dpscalc.calc3.effects.defenceroll;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardDefenceRoll;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Operation;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TombsScalingDefenceRoll implements ContextValue<Integer>
{

	private final StandardDefenceRoll standardDefenceRoll;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.SCENARIO).getToaScale() != -1;
	}

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int scale = ctx.get(ComputeInputs.SCENARIO).getToaScale();
		Operation scaling = new Multiplication(250 + scale, 250);

		return scaling.apply(ctx.get(standardDefenceRoll));
	}

}
