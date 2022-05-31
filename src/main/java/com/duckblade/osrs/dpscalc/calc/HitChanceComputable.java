package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.attack.AttackRollComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HitChanceComputable implements Computable<Double>
{

	private final AttackRollComputable attackRollComputable;
	private final DefenseRollComputable defenseRollComputable;

	@Override
	public Double compute(ComputeContext context)
	{
		int attRoll = context.get(attackRollComputable);
		int defRoll = context.get(defenseRollComputable);

		if (attRoll > defRoll)
		{
			return 1.0 - ((defRoll + 2.0) / (2.0 * (attRoll + 1.0)));
		}
		else
		{
			return attRoll / (2.0 * (defRoll + 1.0));
		}
	}
}
