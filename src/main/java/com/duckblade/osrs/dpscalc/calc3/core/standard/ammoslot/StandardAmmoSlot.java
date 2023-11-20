package com.duckblade.osrs.dpscalc.calc3.core.standard.ammoslot;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardAmmoSlot implements ContextValue<ItemStats>
{

	@Override
	public ItemStats compute(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.ATTACKER_ITEMS)
			.getOrDefault(EquipmentInventorySlot.AMMO, ItemStats.EMPTY);
	}

}
