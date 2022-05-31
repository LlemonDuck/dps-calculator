package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.LEAFY;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LeafyGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private LeafyGearBonus leafyGearBonus;

	@Test
	void isApplicableWhenFightingLeafyEnemies()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(LEAFY);

		assertTrue(leafyGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotFightingLeafyEnemies()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertFalse(leafyGearBonus.isApplicable(context));
	}

	@Test
	void providesAppropriateBonusWhenUsingMeleeWithoutBattleaxe()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.LEAFBLADED_SWORD),
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.LEAFBLADED_SPEAR)
		);

		assertEquals(GearBonuses.EMPTY, leafyGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, leafyGearBonus.compute(context));
	}

	@Test
	void providesAppropriateBonusWhenUsingMeleeWithBattleaxe()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.LEAFBLADED_BATTLEAXE)
		);

		assertEquals(GearBonuses.symmetric(1.175), leafyGearBonus.compute(context));
	}

	@Test
	void zerosCalculationWhenUsingMeleeWithoutLeafBladedWeapon()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.SCYTHE_OF_VITUR)
		);

		assertEquals(GearBonuses.symmetric(0), leafyGearBonus.compute(context));
		verify(context, times(1)).warn("Leafy creatures are immune to melee unless using a leaf-bladed weapon.");
	}

	@Test
	void providesFlatBonusWhenUsingRangedWithBroadAmmo()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMMO, ItemID.BROAD_ARROWS),
			Collections.singletonMap(EquipmentInventorySlot.AMMO, ItemID.BROAD_BOLTS)
		);

		assertEquals(GearBonuses.EMPTY, leafyGearBonus.compute(context));
		assertEquals(GearBonuses.EMPTY, leafyGearBonus.compute(context));
	}

	@Test
	void zerosCalculationWhenUsingRangedWithoutBroadAmmo()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.AMMO, ItemID.DRAGON_ARROW)
		);

		assertEquals(GearBonuses.symmetric(0), leafyGearBonus.compute(context));
		verify(context, times(1)).warn("Leafy creatures are immune to ranged unless using broad bolts/arrows.");
	}

	@Test
	void providesFlatBonusWhenUsingMagicWithMagicDart()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.MAGIC_DART);

		assertEquals(GearBonuses.EMPTY, leafyGearBonus.compute(context));
	}

	@Test
	void zerosCalculationWhenUsingMagicWithoutMagicDart()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.FIRE_SURGE);

		assertEquals(GearBonuses.symmetric(0), leafyGearBonus.compute(context));
		verify(context, times(1)).warn("Leafy creatures are immune to magic spells other than Magic Dart.");
	}

}