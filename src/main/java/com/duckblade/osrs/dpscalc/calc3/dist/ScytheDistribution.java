package com.duckblade.osrs.dpscalc.calc3.dist;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ScytheDistribution implements ContextValue<List<HitDistribution>>
{

	private static final Set<Integer> SCYTHE_IDS = ImmutableSet.of(
		ItemID.SCYTHE_OF_VITUR,
		ItemID.SCYTHE_OF_VITUR_UNCHARGED, // still works, but has reduced stats which is baked into the item stats
		ItemID.SCYTHE_OF_VITUR_22664, // no clue what this is tbh https://chisel.weirdgloop.org/moid/item_id.html#22664
		ItemID.HOLY_SCYTHE_OF_VITUR,
		ItemID.HOLY_SCYTHE_OF_VITUR_UNCHARGED,
		ItemID.SANGUINE_SCYTHE_OF_VITUR,
		ItemID.SANGUINE_SCYTHE_OF_VITUR_UNCHARGED
	);

	private final Weapon weapon;
	private final Accuracy accuracy;
	private final MaxHit maxHit;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return SCYTHE_IDS.contains(ctx.get(weapon).getItemId());
	}

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		double acc = ctx.get(accuracy);
		int max = ctx.get(maxHit);

		return Arrays.asList(
			LinearDistribution.of(acc, max),
			LinearDistribution.of(acc, max / 2),
			LinearDistribution.of(acc, max / 4)
		);
	}
}
