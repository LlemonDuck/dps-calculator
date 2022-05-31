package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.VoidLevelComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.VoidLevel;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class VoidGearBonus implements GearBonusComputable
{

	private final VoidLevelComputable voidLevelComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		switch (context.get(voidLevelComputable))
		{
			case ELITE:
			case REGULAR:
				return true;

			default:
				return false;
		}
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		boolean eliteVoid = context.get(voidLevelComputable) == VoidLevel.ELITE;
		switch (context.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MAGIC:
				return GearBonuses.of(1.45, eliteVoid ? 1.025 : 1.0);

			case RANGED:
				return GearBonuses.of(1.1, eliteVoid ? 1.125 : 1.1);

			default:
				return GearBonuses.symmetric(1.1);
		}
	}

}
