package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.magic.MageMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

// this needs to be split out from BaseMaxHitComputable 
// so that future multihit computables can source their max hit accurately
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PreLimitBaseMaxHitComputable implements Computable<Integer>
{

	private final MeleeRangedMaxHitComputable meleeRangedMaxHitComputable;
	private final MageMaxHitComputable mageMaxHitComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		if (context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC)
		{
			return context.get(mageMaxHitComputable);
		}
		return context.get(meleeRangedMaxHitComputable);
	}
}
