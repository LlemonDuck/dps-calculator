package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.util.TicksToDurationComputable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TimeToKillComputable extends TicksToDurationComputable
{

	private final DptComputable dptComputable;

	@Override
	protected int getTicks(ComputeContext context)
	{
		double dpt = context.get(dptComputable);
		if (dpt <= 0)
		{
			return -1;
		}

		int targetHp = context.get(ComputeInputs.DEFENDER_SKILLS).getTotals().get(Skill.HITPOINTS);
		return (int) (Math.ceil((double) targetHp / dpt));
	}

}
