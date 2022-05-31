package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.MaxHitComputable;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Singleton;
import net.runelite.api.NpcID;

@Singleton
public class ZulrahMaxHitLimiter implements MaxHitLimiter
{

	private static final Set<Integer> ZULRAH_IDS = ImmutableSet.of(
		NpcID.ZULRAH,
		NpcID.ZULRAH_2043,
		NpcID.ZULRAH_2044
	);

	private static final int ZULRAH_MAX_HIT = 50;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		int currentMaxHit = context.get(MaxHitComputable.PRE_LIMIT_MAX_HIT);
		int targetNpc = context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getNpcId();
		if (currentMaxHit > ZULRAH_MAX_HIT && ZULRAH_IDS.contains(targetNpc))
		{
			context.warn("Zulrah has a max hit limiter of " + ZULRAH_MAX_HIT + ".");
			return true;
		}

		return false;
	}

	@Override
	public Integer compute(ComputeContext context)
	{
		return ZULRAH_MAX_HIT;
	}
}
