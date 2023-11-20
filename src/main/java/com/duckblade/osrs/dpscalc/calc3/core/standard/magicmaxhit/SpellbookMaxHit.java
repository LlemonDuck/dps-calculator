package com.duckblade.osrs.dpscalc.calc3.core.standard.magicmaxhit;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.CombatStyle;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class SpellbookMaxHit implements ContextValue<Integer>
{

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		AttackStyle attackStyle = ctx.get(ComputeInputs.ATTACK_STYLE);
		if (attackStyle.getAttackType() != AttackType.MAGIC)
		{
			return false;
		}

		return attackStyle.isManualCast()
			|| attackStyle.getCombatStyle() == CombatStyle.AUTOCAST;
	}

	@Override
	public Integer compute(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.SPELL).getBaseMaxHit();
	}

}
