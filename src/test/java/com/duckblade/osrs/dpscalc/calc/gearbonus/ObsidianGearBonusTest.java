package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableMap;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObsidianGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private ObsidianGearBonus obsidianGearBonus;

	@Test
	void isApplicableWhenUsingObsidianWeaponAndFullSet()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			AttackStyle.builder().attackType(AttackType.STAB).build()
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILAK)
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build()
		);
		assertTrue(obsidianGearBonus.isApplicable(context));
		assertTrue(obsidianGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutObsidianWeapons()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			AttackStyle.builder().attackType(AttackType.RANGED).build()
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.MAGIC_SHORTBOW)
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build()
		);
		assertFalse(obsidianGearBonus.isApplicable(context));
		assertFalse(obsidianGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenCasting()
	{
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILAK)
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build()
		);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.MANUAL_CAST);
		assertFalse(obsidianGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableForPartialSet()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.builder().attackType(AttackType.STAB).build());
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
				.put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
				.build(),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
				.build()
		);

		for (int test = 0; test < 7; test++)
		{
			assertFalse(obsidianGearBonus.isApplicable(context));
		}
	}

	@Test
	void grantsExpectedBonus()
	{
		assertEquals(GearBonuses.of(1.1, 1.1), obsidianGearBonus.compute(context));
	}

}
