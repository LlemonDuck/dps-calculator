package com.duckblade.osrs.dpscalc.calc3.effects.accuracy;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardAccuracy;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class OsmumtensFangAccuracy implements ContextValue<Double>
{

	static final Set<Integer> OSMUMTENS_FANG = ImmutableSet.of(
		ItemID.OSMUMTENS_FANG,
		ItemID.OSMUMTENS_FANG_OR
	);

	private final StandardAccuracy standardAccuracy;
	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return OSMUMTENS_FANG.contains(ctx.get(weapon).getItemId()) &&
			!ctx.get(ComputeInputs.SCENARIO).isSpecialAttack();
	}

	@Override
	public Double compute(ComputeContext ctx)
	{
		if (ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isInToa())
		{
			// todo idk
			return 1.0;
		}

		return 1 - Math.pow(1 - ctx.get(standardAccuracy), 2);
	}
}
