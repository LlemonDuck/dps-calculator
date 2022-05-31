package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EffectiveAttackLevelComputable implements Computable<Integer>
{

	private final MeleeEffectiveAttackLevelComputable meleeComputable;
	private final RangedEffectiveAttackLevelComputable rangedComputable;
	private final MageEffectiveAttackLevelComputable mageComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		switch (attackStyle.getAttackType())
		{
			case MAGIC:
				return context.get(mageComputable);

			case RANGED:
				return context.get(rangedComputable);

			default:
				return context.get(meleeComputable);
		}
	}
}
