package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.*;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class OsmumtensFangDptComputable implements MultiHitDptComputable
{

	private final BaseMaxHitComputable baseMaxHitComputable;
	private final FangHitChanceComputable fangHitChanceComputable;
	private final AttackSpeedComputable attackSpeedComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return fangHitChanceComputable.isApplicable(context);
	}

	@Override
	public Double compute(ComputeContext context)
	{
		double effectHitChance = context.get(fangHitChanceComputable);

		// fang clamps damage per hit between 15% and 85% of max
		// which keeps avg hit the same so no work needed to handle that
		// unless there is a max hit limiter in play
		int maxHit = context.get(baseMaxHitComputable);
		if (context.get(MaxHitLimitComputable.LIMIT_APPLIED))
		{
			context.warn("Max hit may be inaccurate due to conflicting effects of a max hit limiter and fang max hit clamping.");
		}

		int attackSpeed = context.get(attackSpeedComputable);
		return BaseHitDptComputable.byComponents(effectHitChance, maxHit, attackSpeed);
	}
}
