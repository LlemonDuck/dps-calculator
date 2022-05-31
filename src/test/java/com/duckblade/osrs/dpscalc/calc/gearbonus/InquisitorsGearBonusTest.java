package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InquisitorsGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private InquisitorsGearBonus inquisitorsGearBonus;

	@Test
	void isApplicableWhenWearingAnyInquisitors()
	{
		//noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.HEAD, ItemID.INQUISITORS_GREAT_HELM),
			singletonMap(EquipmentInventorySlot.BODY, ItemID.INQUISITORS_HAUBERK),
			singletonMap(EquipmentInventorySlot.LEGS, ItemID.INQUISITORS_PLATESKIRT),
			ImmutableMap.<EquipmentInventorySlot, Integer>builder()
				.put(EquipmentInventorySlot.HEAD, ItemID.INQUISITORS_GREAT_HELM)
				.put(EquipmentInventorySlot.BODY, ItemID.INQUISITORS_HAUBERK)
				.put(EquipmentInventorySlot.LEGS, ItemID.INQUISITORS_PLATESKIRT)
				.build()
		);
		assertTrue(inquisitorsGearBonus.isApplicable(context));
		assertTrue(inquisitorsGearBonus.isApplicable(context));
		assertTrue(inquisitorsGearBonus.isApplicable(context));
		assertTrue(inquisitorsGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutInquisitors()
	{
		//noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			emptyMap(),
			singletonMap(EquipmentInventorySlot.BODY, ItemID.RUNE_PLATEBODY)
		);
		assertFalse(inquisitorsGearBonus.isApplicable(context));
		assertFalse(inquisitorsGearBonus.isApplicable(context));
	}

	@Test
	void warnsForNonCrush()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.SLASH),
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.RANGED),
			ofAttackType(AttackType.MAGIC)
		);

		assertEquals(GearBonuses.EMPTY, inquisitorsGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, inquisitorsGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, inquisitorsGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, inquisitorsGearBonus.compute(context));
		verify(context, times(4)).warn("Wearing inquisitor's armour without attacking on crush provides no bonuses.");
	}

	@Test
	void grantsBonusForHelm()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.HEAD, ItemID.INQUISITORS_GREAT_HELM)
		);

		assertEquals(GearBonuses.symmetric(1.005), inquisitorsGearBonus.compute(context));
	}

	@Test
	void grantsBonusForBody()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.BODY, ItemID.INQUISITORS_HAUBERK)
		);

		assertEquals(GearBonuses.symmetric(1.005), inquisitorsGearBonus.compute(context));
	}

	@Test
	void grantsBonusForLegs()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			singletonMap(EquipmentInventorySlot.LEGS, ItemID.INQUISITORS_PLATESKIRT)
		);

		assertEquals(GearBonuses.symmetric(1.005), inquisitorsGearBonus.compute(context));
	}

	@Test
	void grantsBonusForFullSet()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.<EquipmentInventorySlot, Integer>builder()
			.put(EquipmentInventorySlot.HEAD, ItemID.INQUISITORS_GREAT_HELM)
			.put(EquipmentInventorySlot.BODY, ItemID.INQUISITORS_HAUBERK)
			.put(EquipmentInventorySlot.LEGS, ItemID.INQUISITORS_PLATESKIRT)
			.build()
		);

		assertEquals(GearBonuses.symmetric(1.025), inquisitorsGearBonus.compute(context));
	}

}