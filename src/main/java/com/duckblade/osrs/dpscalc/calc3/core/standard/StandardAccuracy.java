package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.core.AttackRoll;
import com.duckblade.osrs.dpscalc.calc3.core.DefenceRoll;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardAccuracy implements ContextValue<Double>
{

	private final AttackRoll attackRoll;
	private final DefenceRoll defenceRoll;

	@Override
	public Double compute(ComputeContext ctx)
	{
		int att = ctx.get(attackRoll);
		int def = ctx.get(defenceRoll);

		return (double) (att > def
			? 1 - ((def + 2) / (2 * (att + 1)))
			: att / (2 * (def + 1)));
	}

}
