package com.duckblade.osrs.dpscalc.plugin.osdata.dev;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.duckblade.osrs.dpscalc.plugin.osdata.ItemStatsProvider;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
public class DevItemStatsProvider extends ItemStatsProvider
{

	private final Map<Integer, ItemStats> localMap = new HashMap<>();

	@Override
	public void load()
	{
		localMap.put(
			ItemID.TWISTED_BOW, ItemStats.builder()
				.itemId(ItemID.TWISTED_BOW)
				.name("Twisted bow")
				.accuracyRanged(70)
				.strengthRanged(20)
				.slot(EquipmentInventorySlot.WEAPON.getSlotIdx())
				.speed(6)
				.is2h(true)
				.weaponCategory(WeaponCategory.BOW)
				.build()
		);

		localMap.put(
			ItemID.DRAGON_ARROW, ItemStats.builder()
				.itemId(ItemID.DRAGON_ARROW)
				.name("Dragon arrow")
				.strengthRanged(60)
				.slot(EquipmentInventorySlot.AMMO.getSlotIdx())
				.build()
		);
	}

	@Override
	public Set<ItemStats> getAll()
	{
		return new HashSet<>(localMap.values());
	}

	@Override
	public ItemStats getById(int npcId)
	{
		return localMap.get(npcId);
	}
}
