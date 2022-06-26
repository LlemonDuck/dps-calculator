package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.gearbonus.AggregateGearBonusesComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.StrengthBonusComputable;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MageMaxHitComputable implements Computable<Integer>
{

	private final Set<MagicMaxHitComputable> maxHitComputables;
	private final StrengthBonusComputable strengthBonusComputable;
	private final AggregateGearBonusesComputable aggregateGearBonusesComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		int weaponMaxHit = maxHitComputables.stream()
			.filter(mmhc -> mmhc.isApplicable(context))
			.mapToInt(context::get)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No magic max hit provider for inputs"));

		double magDmgBonus = 1 + context.get(strengthBonusComputable) / 100.0;
		double gearBonus = context.get(aggregateGearBonusesComputable).getStrengthBonus();
		double bonus = magDmgBonus * gearBonus;
		return (int) (weaponMaxHit * bonus);
	}
}
