package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AggregateGearBonusesComputable implements Computable<GearBonuses>
{

	private final Set<GearBonusComputable> gearBonusComputables;

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		return gearBonusComputables.stream()
			.filter(gbc -> gbc.isApplicable(context))
			.map(context::get)
			.reduce(GearBonuses::combine)
			.orElse(GearBonuses.EMPTY);
	}

}
