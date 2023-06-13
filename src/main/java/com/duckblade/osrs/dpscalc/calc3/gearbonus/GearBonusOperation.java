package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;

public interface GearBonusOperation extends ContextValue<GearBonus>
{

	default int applyAccuracy(ComputeContext ctx, int previous)
	{
		GearBonus gb;
		if (this.isApplicable(ctx) && (gb = ctx.get(this)) != null)
		{
			return gb.applyAccuracy(previous);
		}

		return previous;
	}

	default int applyStrength(ComputeContext ctx, int previous)
	{
		GearBonus gb;
		if (this.isApplicable(ctx) && (gb = ctx.get(this)) != null)
		{
			return gb.applyStrength(previous);
		}

		return previous;
	}

}
