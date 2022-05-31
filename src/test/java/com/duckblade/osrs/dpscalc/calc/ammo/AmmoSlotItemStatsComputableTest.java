package com.duckblade.osrs.dpscalc.calc.ammo;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AmmoSlotItemStatsComputableTest
{

	@Mock
	private AmmoItemStatsComputable ammoItemStatsComputable1, ammoItemStatsComputable2;

	@Mock
	private ComputeContext context;

	private AmmoSlotItemStatsComputable ammoSlotItemStatsComputable;

	@BeforeEach
	void setUp()
	{
		ammoSlotItemStatsComputable = new AmmoSlotItemStatsComputable(ImmutableSet.of(
			ammoItemStatsComputable1,
			ammoItemStatsComputable2
		));
	}

	@Test
	void defaultsToEmptyStatsIfNoneProvided()
	{
		when(ammoItemStatsComputable1.isApplicable(context)).thenReturn(false);
		when(ammoItemStatsComputable2.isApplicable(context)).thenReturn(false);
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.WEAPON, ofItemId(ItemID.SCYTHE_OF_VITUR)
		));

		assertEquals(ItemStats.EMPTY, ammoSlotItemStatsComputable.compute(context));
	}

	@Test
	void defaultsToAmmoSlotStatsIfNoSubcomponentsAreApplicable()
	{
		ItemStats ammo = ofItemId(ItemID.SCYTHE_OF_VITUR);
		when(ammoItemStatsComputable1.isApplicable(context)).thenReturn(false);
		when(ammoItemStatsComputable2.isApplicable(context)).thenReturn(false);
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.AMMO, ammo
		));

		assertEquals(ammo, ammoSlotItemStatsComputable.compute(context));
	}

	@Test
	void usesApplicableSubcomponent()
	{
		ItemStats ammo = ofItemId(ItemID.SCYTHE_OF_VITUR);
		when(ammoItemStatsComputable1.isApplicable(context)).thenReturn(false);
		when(ammoItemStatsComputable2.isApplicable(context)).thenReturn(true);
		when(context.get(ammoItemStatsComputable2)).thenReturn(ammo);

		assertEquals(ammo, ammoSlotItemStatsComputable.compute(context));
	}

}