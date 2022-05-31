package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.gearbonus.AggregateGearBonusesComputable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AttackRollComputable implements Computable<Integer>
{

	private final EffectiveAttackLevelComputable effectiveAttackLevelComputable;
	private final AttackBonusComputable attackBonusComputable;
	private final AggregateGearBonusesComputable aggregateGearBonusesComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		int effectiveAttack = context.get(effectiveAttackLevelComputable);
		int attackBonus = context.get(attackBonusComputable);
		double accuracyGearBonus = context.get(aggregateGearBonusesComputable).getAccuracyBonus();

		return (int) (effectiveAttack * (attackBonus + 64) * accuracyGearBonus);
	}
}
