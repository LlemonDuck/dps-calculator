package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BerserkerNecklace implements GearBonusOperation
{

	// todo

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return false;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		return null;
	}

}
