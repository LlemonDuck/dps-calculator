package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
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

	private static final MaxHitLimit ZULRAH_MAX_HIT = MaxHitLimit.builder()
		.limit(50)
		.warning("Zulrah has a max hit limiter of 50.")
		.build();

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return ZULRAH_IDS.contains(context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getNpcId());
	}

	@Override
	public MaxHitLimit compute(ComputeContext context)
	{
		return ZULRAH_MAX_HIT;
	}
}
