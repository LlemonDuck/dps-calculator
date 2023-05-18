package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BerserkerNecklaceGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private BerserkerNecklaceGearBonus berserkerNecklaceGearBonus;

	@Test
	void isApplicableWhenUsingObsidianWeapons()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
				AttackStyle.builder().attackType(AttackType.STAB).build()
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
				singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILAK),
				singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILEK),
				singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETEM),
				singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM),
				singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
		);
		assertTrue(berserkerNecklaceGearBonus.isApplicable(context));
		assertTrue(berserkerNecklaceGearBonus.isApplicable(context));
		assertTrue(berserkerNecklaceGearBonus.isApplicable(context));
		assertTrue(berserkerNecklaceGearBonus.isApplicable(context));
		assertTrue(berserkerNecklaceGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutObsidianWeapons()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
				AttackStyle.builder().attackType(AttackType.RANGED).build()
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
				emptyMap(),
				singletonMap(EquipmentInventorySlot.WEAPON, ItemID.MAGIC_SHORTBOW)
		);
		assertFalse(berserkerNecklaceGearBonus.isApplicable(context));
		assertFalse(berserkerNecklaceGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenCasting()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(
				singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILAK)
		);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.MANUAL_CAST);
		assertFalse(berserkerNecklaceGearBonus.isApplicable(context));
	}

	@Test
	void grantsBonusForBerserkerNecklace()
	{
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
				singletonMap(EquipmentInventorySlot.AMULET, ItemID.BERSERKER_NECKLACE),
				singletonMap(EquipmentInventorySlot.AMULET, ItemID.BERSERKER_NECKLACE_OR)
		);
		assertEquals(GearBonuses.of(1.0, 1.2), berserkerNecklaceGearBonus.compute(context));
	}

}
