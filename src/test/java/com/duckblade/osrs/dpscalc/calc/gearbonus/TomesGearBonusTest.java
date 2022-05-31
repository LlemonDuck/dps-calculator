package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
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
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TomesGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private TomesGearBonus tomesGearBonus;

	@Test
	void isApplicableWhenUsingMagicWithATome()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_FIRE),
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_WATER)
		);

		assertTrue(tomesGearBonus.isApplicable(context));
		assertTrue(tomesGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenUsingMagicWithoutATome()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_FIRE_EMPTY),
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_WATER_EMPTY),
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.DRAGON_DEFENDER)
		);

		assertFalse(tomesGearBonus.isApplicable(context));
		assertFalse(tomesGearBonus.isApplicable(context));
		assertFalse(tomesGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.RANGED)
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_FIRE),
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_WATER)
		);

		assertFalse(tomesGearBonus.isApplicable(context));
		assertFalse(tomesGearBonus.isApplicable(context));
	}

	@Test
	void grantsBonusWhenUsingFireSpellsWithTomeOfFire()
	{
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.FIRE_STRIKE,
			Spell.FIRE_BOLT,
			Spell.FIRE_BLAST,
			Spell.FIRE_WAVE,
			Spell.FIRE_SURGE
		);
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_FIRE)
		);

		assertEquals(GearBonuses.of(1, 1.5), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.of(1, 1.5), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.of(1, 1.5), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.of(1, 1.5), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.of(1, 1.5), tomesGearBonus.compute(context));
	}

	@Test
	void grantsBonusWhenUsingWaterSpellsWithTomeOfWater()
	{
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.WATER_STRIKE,
			Spell.WATER_BOLT,
			Spell.WATER_BLAST,
			Spell.WATER_WAVE,
			Spell.WATER_SURGE
		);
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_WATER)
		);

		assertEquals(GearBonuses.symmetric(1.2), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(1.2), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(1.2), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(1.2), tomesGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(1.2), tomesGearBonus.compute(context));
	}

	@Test
	void grantsNoBonusWhenUsingSpellsThatDontMatchTome()
	{
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.WATER_STRIKE,
			Spell.FIRE_SURGE,
			Spell.ICE_BARRAGE
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_FIRE),
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_WATER),
			Collections.singletonMap(EquipmentInventorySlot.SHIELD, ItemID.TOME_OF_WATER)
		);

		assertEquals(GearBonuses.EMPTY, tomesGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, tomesGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, tomesGearBonus.compute(context));
	}

}