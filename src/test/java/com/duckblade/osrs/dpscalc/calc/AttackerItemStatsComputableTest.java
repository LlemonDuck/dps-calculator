package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.ammo.AmmoSlotItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
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
class AttackerItemStatsComputableTest
{

	private static final ItemStats WEAPON = ItemStats.builder()
		.itemId(123)
		.name("MockWeapon")
		.accuracyStab(1)
		.accuracySlash(2)
		.accuracyCrush(3)
		.accuracyRanged(4)
		.accuracyMagic(5)
		.strengthMelee(6)
		.strengthRanged(7)
		.strengthMagic(8)
		.prayer(9)
		.speed(10)
		.slot(EquipmentInventorySlot.WEAPON.getSlotIdx())
		.is2h(true)
		.weaponCategory(WeaponCategory.BLUDGEON)
		.build();

	private static final ItemStats NON_WEAPON_1 = ItemStats.builder()
		.itemId(321)
		.name("MockHelmet")
		.accuracyStab(11)
		.accuracySlash(12)
		.accuracyCrush(13)
		.accuracyRanged(14)
		.accuracyMagic(15)
		.strengthMelee(16)
		.strengthRanged(17)
		.strengthMagic(18)
		.prayer(19)
		.slot(EquipmentInventorySlot.HEAD.getSlotIdx())
		.build();

	private static final ItemStats NON_WEAPON_2 = ItemStats.builder()
		.itemId(213)
		.name("MockTorso")
		.accuracyStab(21)
		.accuracySlash(22)
		.accuracyCrush(23)
		.accuracyRanged(24)
		.accuracyMagic(25)
		.strengthMelee(26)
		.strengthRanged(27)
		.strengthMagic(28)
		.prayer(29)
		.slot(EquipmentInventorySlot.BODY.getSlotIdx())
		.build();

	private static final ItemStats AMMO_SLOT = ItemStats.builder()
		.itemId(987)
		.name("MockAmmo")
		.accuracyStab(51)
		.accuracySlash(52)
		.accuracyCrush(53)
		.accuracyRanged(54)
		.accuracyMagic(55)
		.strengthMelee(56)
		.strengthRanged(57)
		.strengthMagic(58)
		.prayer(59)
		.slot(EquipmentInventorySlot.AMMO.getSlotIdx())
		.build();

	private static final ItemStats SHADOW = ItemStats.builder()
		.itemId(ItemID.TUMEKENS_SHADOW)
		.name("MockShadow")
		.accuracyStab(61)
		.accuracySlash(62)
		.accuracyCrush(63)
		.accuracyRanged(64)
		.accuracyMagic(65)
		.strengthMelee(66)
		.strengthRanged(67)
		.strengthMagic(68)
		.prayer(69)
		.slot(EquipmentInventorySlot.WEAPON.getSlotIdx())
		.build();

	@Mock
	private ComputeContext context;

	@Mock
	private AmmoSlotItemStatsComputable ammoSlotItemStatsComputable;

	@InjectMocks
	private AttackerItemStatsComputable attackerItemStatsComputable;

	@Test
	void mergesStatsKeepingWeaponStatsFromWeaponSlotIfFirst()
	{
		ItemStats expected = ItemStats.builder()
			.accuracyStab(12)
			.accuracySlash(14)
			.accuracyCrush(16)
			.accuracyRanged(18)
			.accuracyMagic(20)
			.strengthMelee(22)
			.strengthRanged(24)
			.strengthMagic(26)
			.prayer(28)
			.speed(10)
			.slot(EquipmentInventorySlot.WEAPON.getSlotIdx())
			.is2h(true)
			.weaponCategory(WeaponCategory.BLUDGEON)
			.build();

		assertEquals(expected, AttackerItemStatsComputable.reduce(WEAPON, NON_WEAPON_1));
	}

	@Test
	void mergesStatsKeepingWeaponStatsFromWeaponSlotIfSecond()
	{
		ItemStats expected = ItemStats.builder()
			.accuracyStab(12)
			.accuracySlash(14)
			.accuracyCrush(16)
			.accuracyRanged(18)
			.accuracyMagic(20)
			.strengthMelee(22)
			.strengthRanged(24)
			.strengthMagic(26)
			.prayer(28)
			.speed(10)
			.slot(EquipmentInventorySlot.WEAPON.getSlotIdx())
			.is2h(true)
			.weaponCategory(WeaponCategory.BLUDGEON)
			.build();

		assertEquals(expected, AttackerItemStatsComputable.reduce(NON_WEAPON_1, WEAPON));
	}

	@Test
	void mergesStatsUsingUnarmedWeaponStatsWithoutWeaponSlot()
	{
		ItemStats expected = ItemStats.builder()
			.accuracyStab(32)
			.accuracySlash(34)
			.accuracyCrush(36)
			.accuracyRanged(38)
			.accuracyMagic(40)
			.strengthMelee(42)
			.strengthRanged(44)
			.strengthMagic(46)
			.prayer(48)
			.slot(-1)
			.build();

		assertEquals(expected, AttackerItemStatsComputable.reduce(NON_WEAPON_1, NON_WEAPON_2));
	}

	@Test
	void combinesStatsOfMultipleItems()
	{
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.HEAD, NON_WEAPON_1,
			EquipmentInventorySlot.GLOVES, NON_WEAPON_2
		));
		when(context.get(ammoSlotItemStatsComputable)).thenReturn(ItemStats.EMPTY);

		assertEquals(
			AttackerItemStatsComputable.reduce(NON_WEAPON_1, NON_WEAPON_2),
			attackerItemStatsComputable.compute(context)
		);
	}

	@Test
	void defersAmmoSlotItemToAmmoSlotItemStatsComputable()
	{
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.HEAD, NON_WEAPON_1,
			EquipmentInventorySlot.AMMO, WEAPON
		));
		when(context.get(ammoSlotItemStatsComputable)).thenReturn(AMMO_SLOT);

		assertEquals(
			AttackerItemStatsComputable.reduce(NON_WEAPON_1, AMMO_SLOT),
			attackerItemStatsComputable.compute(context)
		);
	}

	@Test
	void returnsEmptyStatsForEmptyInput()
	{
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenAnswer(ignored -> Collections.emptyMap());
		when(context.get(ammoSlotItemStatsComputable)).thenReturn(ItemStats.EMPTY);

		assertEquals(ItemStats.EMPTY, attackerItemStatsComputable.compute(context));
	}

	@Test
	void appliesTumekensShadowBonusWhenApplicable()
	{
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenReturn(Collections.singletonMap(EquipmentInventorySlot.WEAPON, SHADOW));
		when(context.get(ammoSlotItemStatsComputable)).thenReturn(ItemStats.EMPTY);

		ItemStats expected = SHADOW.toBuilder()
			.itemId(-1)
			.name(null)
			.accuracyMagic(3 * SHADOW.getAccuracyMagic())
			.strengthMagic(3 * SHADOW.getStrengthMagic())
			.build();

		assertEquals(expected, attackerItemStatsComputable.compute(context));
	}

}