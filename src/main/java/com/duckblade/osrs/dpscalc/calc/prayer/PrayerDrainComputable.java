package com.duckblade.osrs.dpscalc.calc.prayer;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PrayerDrainComputable implements Computable<Integer>
{

	@Override
	public Integer compute(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACKER_PRAYERS).stream()
			.mapToInt(Prayer::getDrainRate)
			.sum();
	}

}
