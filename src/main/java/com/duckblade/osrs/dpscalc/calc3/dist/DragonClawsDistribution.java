package com.duckblade.osrs.dpscalc.calc3.dist;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.WeightedHit;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.HitDistribution;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class DragonClawsDistribution implements ContextValue<List<HitDistribution>>
{

	private final Accuracy accuracy;
	private final MaxHit maxHit;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return true;
	}

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		HitDistribution dist = new HitDistribution();

		double acc = ctx.get(accuracy);
		int max = ctx.get(maxHit);
		
		// attempt 1
		int low = max * 1 / 1;
		int high = max * 2 / 1 - 1;
		double partialAcc = (acc * Math.pow(1 - acc, 0)) / (high - low + 1);
		for (int hit = low; hit <= high; hit++)
		{
			dist.addOutcome(new WeightedHit(
				partialAcc,
				List.of(
					hit * 4 / 8,
					hit * 2 / 8,
					hit * 1 / 8,
					hit * 1 / 8 + 1
				)
			));
		}

		// attempt 2
		low = max * 3 / 4;
		high = max * 7 / 4;
		partialAcc = (acc * Math.pow(1 - acc, 1)) / (high - low + 1);
		for (int hit = low; hit <= high; hit++)
		{
			dist.addOutcome(new WeightedHit(
				partialAcc,
				List.of(
					hit * 2 / 4,
					hit * 1 / 4,
					hit * 1 / 4 + 1,
					0
				)
			));
		}

		// attempt 3
		low = max * 1 / 2;
		high = max * 3 / 2;
		partialAcc = (acc * Math.pow(1 - acc, 2)) / (high - low + 1);
		for (int hit = low; hit <= high; hit++)
		{
			dist.addOutcome(new WeightedHit(
				partialAcc,
				List.of(
					hit * 1 / 2,
					hit * 1 / 2 + 1,
					0,
					0
				)
			));
		}

		// attempt 4
		low = max * 1 / 4;
		high = max * 5 / 4;
		partialAcc = (acc * Math.pow(1 - acc, 3)) / (high - low + 1);
		for (int hit = low; hit <= high; hit++)
		{
			dist.addOutcome(new WeightedHit(
				partialAcc,
				List.of(
					hit + 1,
					0,
					0,
					0
				)
			));
		}

		double remainingAcc = acc * Math.pow(1 - acc, 4);
		dist.addOutcome(new WeightedHit(remainingAcc * 2 / 3, List.of(0, 0, 1, 1)));
		dist.addOutcome(new WeightedHit(remainingAcc * 1 / 3, List.of(0, 0, 0, 0)));

		return Collections.singletonList(dist);
	}

}
