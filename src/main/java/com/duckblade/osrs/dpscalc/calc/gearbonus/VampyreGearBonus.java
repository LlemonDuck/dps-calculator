package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

// TODO
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class VampyreGearBonus implements GearBonusComputable
{

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		if (context.get(ComputeInputs.DEFENDER_ATTRIBUTES).isVampyre())
		{
			context.warn("Bonuses for vampyre immunities / vampyrebane is not yet implemented.");
			return true;
		}

		return false;
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		return GearBonuses.EMPTY;
	}
}
