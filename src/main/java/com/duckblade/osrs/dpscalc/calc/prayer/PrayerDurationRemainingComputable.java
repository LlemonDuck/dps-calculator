package com.duckblade.osrs.dpscalc.calc.prayer;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.util.TicksToDurationComputable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PrayerDurationRemainingComputable extends TicksToDurationComputable
{

	private final AttackerItemStatsComputable attackerItemStatsComputable;
	private final PrayerDrainComputable prayerDrainComputable;

	@Override
	public int getTicks(ComputeContext context)
	{
		int drain = context.get(prayerDrainComputable);
		if (drain == 0)
		{
			return -1;
		}

		int points = context.get(ComputeInputs.ATTACKER_SKILLS).getTotals().get(Skill.PRAYER);
		int resistance = 2 * context.get(attackerItemStatsComputable).getPrayer() + 60;
		int buffer = points * resistance;

		// always round up, so we cast to double to force decimal precision
		return (int) Math.ceil((double) buffer / drain);
	}

}
