package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
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
class WeaponComputableTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private WeaponComputable weaponComputable;

	@Test
	void returnsWeaponSlotItemStats()
	{
		ItemStats expected = ofItemId(ItemID.SCYTHE_OF_VITUR);
		when(context.get(ComputeInputs.ATTACKER_EQUIPMENT)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.HEAD, ofItemId(ItemID.HELM_OF_NEITIZNOT),
			EquipmentInventorySlot.WEAPON, expected
		));

		assertEquals(expected, weaponComputable.compute(context));
	}

	@Test
	void returnsEmptyIfNoWeapon()
	{
		when(context.get(ComputeInputs.ATTACKER_EQUIPMENT)).thenReturn(Collections.emptyMap());

		assertEquals(ItemStats.EMPTY, weaponComputable.compute(context));
	}

}