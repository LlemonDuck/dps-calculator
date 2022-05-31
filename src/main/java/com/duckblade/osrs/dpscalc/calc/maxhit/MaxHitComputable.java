package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimiter;
import com.duckblade.osrs.dpscalc.calc.maxhit.magic.MageMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MaxHitComputable implements Computable<Integer>
{

	public static final ComputeOutput<Integer> PRE_LIMIT_MAX_HIT = ComputeOutput.of("PreLimitMaxHit");

	private final MeleeRangedMaxHitComputable meleeRangedMaxHitComputable;
	private final MageMaxHitComputable mageMaxHitComputable;
	private final Set<MaxHitLimiter> maxHitLimiters;

	@Override
	public Integer compute(ComputeContext context)
	{
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		int maxHit = attackStyle.getAttackType() == AttackType.MAGIC ? context.get(mageMaxHitComputable) : context.get(meleeRangedMaxHitComputable);
		context.put(PRE_LIMIT_MAX_HIT, maxHit);

		int maxHitLimit = maxHitLimiters.stream()
			.filter(mhl -> mhl.isApplicable(context))
			.mapToInt(context::get)
			.min()
			.orElse(Integer.MAX_VALUE);

		return Math.min(maxHit, maxHitLimit);
	}

}
