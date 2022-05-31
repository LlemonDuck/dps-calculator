package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.UNDEAD;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import java.util.Collections;
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
class SalveAmuletGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private SalveAmuletGearBonus salveAmuletGearBonus;

	@Test
	void isApplicableWhenWearingSalveAmulet()
	{
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET_E),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETI),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETEI)
		);

		assertTrue(salveAmuletGearBonus.isApplicable(context));
		assertTrue(salveAmuletGearBonus.isApplicable(context));
		assertTrue(salveAmuletGearBonus.isApplicable(context));
		assertTrue(salveAmuletGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutSalveAmulet()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(Collections.emptyMap());

		assertFalse(salveAmuletGearBonus.isApplicable(context));
	}

	@Test
	void warnsWhenFightingNonUndeadTarget()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertEquals(GearBonuses.EMPTY, salveAmuletGearBonus.compute(context));
		verify(context, times(1)).warn("Salve amulet against a non-undead target provides no bonuses.");
	}

	@Test
	void warnsWhenUsingUnimbuedSalveForRanged()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(UNDEAD);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET_E)
		);

		assertEquals(GearBonuses.EMPTY, salveAmuletGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, salveAmuletGearBonus.compute(context));
		verify(context, times(2)).warn("Unimbued salve amulets provide no bonuses for mage/ranged.");
	}

	@Test
	void warnsWhenUsingUnimbuedSalveForMagic()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(UNDEAD);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET_E)
		);

		assertEquals(GearBonuses.EMPTY, salveAmuletGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, salveAmuletGearBonus.compute(context));
		verify(context, times(2)).warn("Unimbued salve amulets provide no bonuses for mage/ranged.");
	}

	@Test
	void providesCorrectBonusesForMelee()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(UNDEAD);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETI),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULET_E),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETEI)
		);

		assertEquals(GearBonuses.symmetric(7.0 / 6.0), salveAmuletGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(7.0 / 6.0), salveAmuletGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(6.0 / 5.0), salveAmuletGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(6.0 / 5.0), salveAmuletGearBonus.compute(context));
	}

	@Test
	void providesCorrectBonusesForRanged()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(UNDEAD);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETI),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETEI)
		);

		assertEquals(GearBonuses.symmetric(7.0 / 6.0), salveAmuletGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(6.0 / 5.0), salveAmuletGearBonus.compute(context));
	}

	@Test
	void providesCorrectBonusesForMage()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(UNDEAD);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETI),
			Collections.singletonMap(EquipmentInventorySlot.AMULET, ItemID.SALVE_AMULETEI)
		);

		assertEquals(GearBonuses.symmetric(1.15), salveAmuletGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(6.0 / 5.0), salveAmuletGearBonus.compute(context));
	}

}