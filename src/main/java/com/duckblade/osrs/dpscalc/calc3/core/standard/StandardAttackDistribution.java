package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.AttackDistribution;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardAttackDistribution implements ContextValue<AttackDistribution>
{

	private final StandardHitDistribution standardHitDistribution;

	@Override
	public AttackDistribution compute(ComputeContext ctx)
	{
		return new AttackDistribution(Collections.singletonList(ctx.get(standardHitDistribution)));
	}

}
