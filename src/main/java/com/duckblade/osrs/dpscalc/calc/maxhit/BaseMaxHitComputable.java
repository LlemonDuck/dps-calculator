package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.magic.MageMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BaseMaxHitComputable implements Computable<Integer>
{

	public static final ComputeOutput<Integer> PRE_LIMIT_MAX_HIT = ComputeOutput.of("PreLimitMaxHit");

	private final MeleeRangedMaxHitComputable meleeRangedMaxHitComputable;
	private final MageMaxHitComputable mageMaxHitComputable;
	private final MaxHitLimitComputable maxHitLimitComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		int baseMaxHit = attackStyle.getAttackType() == AttackType.MAGIC ? context.get(mageMaxHitComputable) : context.get(meleeRangedMaxHitComputable);

		context.put(PRE_LIMIT_MAX_HIT, baseMaxHit);
		return maxHitLimitComputable.coerce(baseMaxHit, context);
	}

}
