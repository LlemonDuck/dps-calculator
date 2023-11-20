package com.duckblade.osrs.dpscalc.calc3.core.standard.magicmaxhit;

import com.duckblade.osrs.dpscalc.calc3.core.EquipmentStats;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MagicDamageBonus implements ContextValue<Integer>
{
	
	private final EquipmentStats equipmentStats;

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int equipped = ctx.get(equipmentStats).getStrengthMagic();
		return null;
	}
}
