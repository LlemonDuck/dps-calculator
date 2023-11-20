package com.duckblade.osrs.dpscalc.calc3.effects.hitdist;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.AttackDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.HitDistribution;
import java.util.List;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class OsmumtensFangDistribution implements ContextValue<AttackDistribution>
{

	private final Accuracy accuracy;
	private final MaxHit maxHit;

	@Override
	public AttackDistribution compute(ComputeContext ctx)
	{
		int max = ctx.get(maxHit);
		return new AttackDistribution(List.of(
			HitDistribution.linear(
				ctx.get(accuracy),
				max * 3 / 20,
				max * 17 / 20
			)
		));
	}
}
