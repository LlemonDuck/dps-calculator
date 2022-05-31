package com.duckblade.osrs.dpscalc.calc.ammo;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AmmoSlotItemStatsComputable implements Computable<ItemStats>
{

	private final Set<AmmoItemStatsComputable> ammoItemStatsComputables;

	@Override
	public ItemStats compute(ComputeContext context)
	{
		return ammoItemStatsComputables.stream()
			.filter(c -> c.isApplicable(context))
			.findAny()
			.map(context::get)
			.orElseGet(() -> getDefault(context));
	}

	private ItemStats getDefault(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACKER_ITEMS)
			.getOrDefault(EquipmentInventorySlot.AMMO, ItemStats.EMPTY);
	}
}
