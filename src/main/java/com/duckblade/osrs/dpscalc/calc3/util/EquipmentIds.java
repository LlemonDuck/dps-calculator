package com.duckblade.osrs.dpscalc.calc3.util;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class EquipmentIds implements ContextValue<Map<EquipmentInventorySlot, Integer>>
{

	@Override
	public Map<EquipmentInventorySlot, Integer> compute(ComputeContext ctx)
	{
		Map<EquipmentInventorySlot, ItemStats> inputs = ctx.get(ComputeInputs.ATTACKER_ITEMS);

		Map<EquipmentInventorySlot, Integer> idMap = new HashMap<>();
		for (EquipmentInventorySlot slot : EquipmentInventorySlot.values())
		{
			idMap.put(slot, inputs.getOrDefault(slot, ItemStats.EMPTY).getItemId());
		}

		return idMap;
	}

}
