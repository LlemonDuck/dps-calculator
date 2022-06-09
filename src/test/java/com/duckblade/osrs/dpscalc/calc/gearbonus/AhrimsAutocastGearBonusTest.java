package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.google.common.collect.ImmutableMap;
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
class AhrimsAutocastGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private AhrimsAutocastGearBonus ahrimsAutocastGearBonus;

	@Test
	void isNotApplicableWhenNotUsingMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertFalse(ahrimsAutocastGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutAhrimsAndDamned()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.AHRIMS_STAFF,
				EquipmentInventorySlot.HEAD, ItemID.AHRIMS_HOOD,
				EquipmentInventorySlot.BODY, ItemID.AHRIMS_ROBETOP,
				EquipmentInventorySlot.LEGS, ItemID.AHRIMS_ROBESKIRT
			),
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.AHRIMS_STAFF,
				EquipmentInventorySlot.HEAD, ItemID.AHRIMS_HOOD_0,
				EquipmentInventorySlot.BODY, ItemID.AHRIMS_ROBETOP,
				EquipmentInventorySlot.LEGS, ItemID.AHRIMS_ROBESKIRT,
				EquipmentInventorySlot.AMULET, ItemID.AMULET_OF_THE_DAMNED_FULL
			),
			Collections.emptyMap()
		);

		assertFalse(ahrimsAutocastGearBonus.isApplicable(context));
		assertFalse(ahrimsAutocastGearBonus.isApplicable(context));
		assertFalse(ahrimsAutocastGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenManualCasting()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.builder()
			.attackType(AttackType.MAGIC)
			.isManualCast(true)
			.build());
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.WEAPON, ItemID.AHRIMS_STAFF,
			EquipmentInventorySlot.HEAD, ItemID.AHRIMS_HOOD,
			EquipmentInventorySlot.BODY, ItemID.AHRIMS_ROBETOP,
			EquipmentInventorySlot.LEGS, ItemID.AHRIMS_ROBESKIRT,
			EquipmentInventorySlot.AMULET, ItemID.AMULET_OF_THE_DAMNED_FULL
		));

		assertFalse(ahrimsAutocastGearBonus.isApplicable(context));
		verify(context, times(1)).warn("Ahrim's with amulet of the damned only provides 30% damage bonus when autocasting.");
	}

	@Test
	void isApplicableWithAhrimsAndDamned()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.builder()
			.attackType(AttackType.MAGIC)
			.isManualCast(false)
			.build());
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.AHRIMS_STAFF_75,
				EquipmentInventorySlot.HEAD, ItemID.AHRIMS_HOOD_50,
				EquipmentInventorySlot.BODY, ItemID.AHRIMS_ROBETOP_25,
				EquipmentInventorySlot.LEGS, ItemID.AHRIMS_ROBESKIRT,
				EquipmentInventorySlot.AMULET, ItemID.AMULET_OF_THE_DAMNED_FULL
			)
		);

		assertTrue(ahrimsAutocastGearBonus.isApplicable(context));
	}

	@Test
	void providesCorrectBonus()
	{
		assertEquals(GearBonuses.of(1.0, 1.3), ahrimsAutocastGearBonus.compute(context));
	}
}