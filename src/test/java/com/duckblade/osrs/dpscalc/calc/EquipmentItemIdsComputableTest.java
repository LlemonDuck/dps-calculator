package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EquipmentItemIdsComputableTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Test
	void mapsItemStatsToItemIDs()
	{
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.HEAD, ofItemId(ItemID.TORVA_FULL_HELM),
			EquipmentInventorySlot.BODY, ofItemId(ItemID.TORVA_PLATEBODY),
			EquipmentInventorySlot.LEGS, ofItemId(ItemID.TORVA_PLATELEGS),
			EquipmentInventorySlot.WEAPON, ofItemId(ItemID.SCYTHE_OF_VITUR),
			EquipmentInventorySlot.RING, ofItemId(ItemID.BERSERKER_RING)
		));

		// also should fill unspecified slots with default value
		int defaultId = ItemStats.EMPTY.getItemId();
		Map<EquipmentInventorySlot, Integer> expected = ImmutableMap.<EquipmentInventorySlot, Integer>builder()
			.put(EquipmentInventorySlot.HEAD, ItemID.TORVA_FULL_HELM)
			.put(EquipmentInventorySlot.BODY, ItemID.TORVA_PLATEBODY)
			.put(EquipmentInventorySlot.LEGS, ItemID.TORVA_PLATELEGS)
			.put(EquipmentInventorySlot.WEAPON, ItemID.SCYTHE_OF_VITUR)
			.put(EquipmentInventorySlot.RING, ItemID.BERSERKER_RING)
			.put(EquipmentInventorySlot.CAPE, defaultId)
			.put(EquipmentInventorySlot.AMMO, defaultId)
			.put(EquipmentInventorySlot.BOOTS, defaultId)
			.put(EquipmentInventorySlot.AMULET, defaultId)
			.put(EquipmentInventorySlot.GLOVES, defaultId)
			.put(EquipmentInventorySlot.SHIELD, defaultId)
			.build();
		assertEquals(expected, equipmentItemIdsComputable.compute(context));
	}

}