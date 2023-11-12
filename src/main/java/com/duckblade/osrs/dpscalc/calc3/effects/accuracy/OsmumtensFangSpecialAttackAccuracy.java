package com.duckblade.osrs.dpscalc.calc3.effects.accuracy;

import com.duckblade.osrs.dpscalc.calc3.effects.accuracy.OsmumtensFangAccuracy;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class OsmumtensFangSpecialAttackAccuracy implements ContextValue<Integer>
{

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return OsmumtensFangAccuracy.OSMUMTENS_FANG.contains(ctx.get(weapon).getItemId()) &&
			ctx.get(ComputeInputs.SCENARIO).isSpecialAttack();
	}

	@Override
	public Integer compute(ComputeContext ctx)
	{
		// todo
		return null;
	}
}
