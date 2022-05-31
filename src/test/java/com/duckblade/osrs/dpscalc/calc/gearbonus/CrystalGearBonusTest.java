package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableMap;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CrystalGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private CrystalGearBonus crystalGearBonus;

	@Test
	void isApplicableWhenUsingCrystalBows()
	{
		//noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.WEAPON, ItemID.CRYSTAL_BOW),
			singletonMap(EquipmentInventorySlot.WEAPON, ItemID.BOW_OF_FAERDHINEN),
			singletonMap(EquipmentInventorySlot.WEAPON, ItemID.BOW_OF_FAERDHINEN_C)
		);
		assertTrue(crystalGearBonus.isApplicable(context));
		assertTrue(crystalGearBonus.isApplicable(context));
		assertTrue(crystalGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutCrystalBow()
	{
		//noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			emptyMap(),
			singletonMap(EquipmentInventorySlot.WEAPON, ItemID.MAGIC_SHORTBOW)
		);
		assertFalse(crystalGearBonus.isApplicable(context));
		assertFalse(crystalGearBonus.isApplicable(context));
	}

	@Test
	void grantsBonusForHelm()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.HEAD, ItemID.CRYSTAL_HELM)
		);
		assertEquals(GearBonuses.of(1.05, 1.025), crystalGearBonus.compute(context));
	}

	@Test
	void grantsBonusForBody()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.BODY, ItemID.CRYSTAL_BODY)
		);
		assertEquals(GearBonuses.of(1.15, 1.075), crystalGearBonus.compute(context));
	}

	@Test
	void grantsBonusForLegs()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.LEGS, ItemID.CRYSTAL_LEGS)
		);
		assertEquals(GearBonuses.of(1.10, 1.05), crystalGearBonus.compute(context));
	}

	@Test
	void grantsBonusForFullSet()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.<EquipmentInventorySlot, Integer>builder()
			.put(EquipmentInventorySlot.HEAD, ItemID.CRYSTAL_HELM)
			.put(EquipmentInventorySlot.BODY, ItemID.CRYSTAL_BODY)
			.put(EquipmentInventorySlot.LEGS, ItemID.CRYSTAL_LEGS)
			.build()
		);
		assertEquals(GearBonuses.of(1.3, 1.15), crystalGearBonus.compute(context));
	}

}