package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

// ignoring defensive stats for simplicity
public class ItemStatsConstants
{

	public static final ItemStats SCYTHE = ItemStats.builder()
		.accuracyStab(70)
		.accuracySlash(110)
		.accuracyCrush(30)
		.accuracyMagic(-6)
		.strengthMelee(75)
		.speed(5)
		.itemId(ItemID.SCYTHE_OF_VITUR)
		.weaponCategory(WeaponCategory.SCYTHE)
		.is2h(true)
		.slot(EquipmentInventorySlot.WEAPON.getSlotIdx())
		.build();

	public static final ItemStats BERSERKER_RING_I = ItemStats.builder()
		.strengthMelee(8)
		.slot(EquipmentInventorySlot.RING.getSlotIdx())
		.itemId(ItemID.BERSERKER_RING)
		.build();

	public static final ItemStats TORVA_FULL_HELM = ItemStats.builder()
		.accuracyMagic(-5)
		.accuracyRanged(-5)
		.prayer(1)
		.strengthMelee(8)
		.slot(EquipmentInventorySlot.HEAD.getSlotIdx())
		.itemId(ItemID.TORVA_FULL_HELM)
		.build();

	public static final ItemStats TORVA_BODY = ItemStats.builder()
		.accuracyMagic(-18)
		.accuracyRanged(-14)
		.prayer(1)
		.strengthMelee(6)
		.slot(EquipmentInventorySlot.BODY.getSlotIdx())
		.itemId(ItemID.TORVA_PLATEBODY)
		.build();

	public static final ItemStats TORVA_LEGS = ItemStats.builder()
		.accuracyMagic(-24)
		.accuracyRanged(-11)
		.prayer(1)
		.strengthMelee(4)
		.slot(EquipmentInventorySlot.LEGS.getSlotIdx())
		.itemId(ItemID.TORVA_PLATELEGS)
		.build();

	public static final ItemStats FEROCIOUS_GLOVES = ItemStats.builder()
		.accuracyStab(16)
		.accuracySlash(16)
		.accuracyCrush(16)
		.accuracyMagic(-16)
		.accuracyRanged(-16)
		.strengthMelee(14)
		.slot(EquipmentInventorySlot.GLOVES.getSlotIdx())
		.itemId(ItemID.FEROCIOUS_GLOVES)
		.build();

	public static final ItemStats PRIMS = ItemStats.builder()
		.accuracyStab(2)
		.accuracySlash(2)
		.accuracyCrush(2)
		.accuracyMagic(-4)
		.accuracyRanged(-1)
		.strengthMelee(5)
		.slot(EquipmentInventorySlot.BOOTS.getSlotIdx())
		.itemId(ItemID.PRIMORDIAL_BOOTS)
		.build();

	public static final ItemStats TORTURE = ItemStats.builder()
		.accuracyStab(15)
		.accuracySlash(15)
		.accuracyCrush(15)
		.strengthMelee(10)
		.prayer(2)
		.slot(EquipmentInventorySlot.AMULET.getSlotIdx())
		.itemId(ItemID.AMULET_OF_TORTURE)
		.build();

	public static final ItemStats INFERNAL_CAPE = ItemStats.builder()
		.accuracyStab(4)
		.accuracySlash(4)
		.accuracyCrush(4)
		.accuracyMagic(1)
		.accuracyRanged(1)
		.strengthMelee(8)
		.prayer(2)
		.slot(EquipmentInventorySlot.CAPE.getSlotIdx())
		.itemId(ItemID.INFERNAL_CAPE)
		.build();

	public static final ItemStats RADAS_BLESSING = ItemStats.builder()
		.prayer(2)
		.slot(EquipmentInventorySlot.AMMO.getSlotIdx())
		.itemId(ItemID.RADAS_BLESSING_4)
		.build();

	public static final ItemStats AVERNIC_DEFENDER = ItemStats.builder()
		.accuracyStab(30)
		.accuracySlash(29)
		.accuracyCrush(28)
		.accuracyMagic(-5)
		.accuracyRanged(-4)
		.strengthMelee(8)
		.slot(EquipmentInventorySlot.AMMO.getSlotIdx())
		.itemId(ItemID.DRAGON_DEFENDER)
		.build();

	public static Map<EquipmentInventorySlot, ItemStats> maxMelee(ItemStats weapon)
	{
		ImmutableMap.Builder<EquipmentInventorySlot, ItemStats> builder = ImmutableMap.<EquipmentInventorySlot, ItemStats>builder()
			.put(EquipmentInventorySlot.HEAD, TORVA_FULL_HELM)
			.put(EquipmentInventorySlot.CAPE, INFERNAL_CAPE)
			.put(EquipmentInventorySlot.AMULET, TORTURE)
			.put(EquipmentInventorySlot.AMMO, RADAS_BLESSING)
			.put(EquipmentInventorySlot.BODY, TORVA_BODY)
			.put(EquipmentInventorySlot.LEGS, TORVA_LEGS)
			.put(EquipmentInventorySlot.GLOVES, FEROCIOUS_GLOVES)
			.put(EquipmentInventorySlot.BOOTS, PRIMS)
			.put(EquipmentInventorySlot.RING, BERSERKER_RING_I);

		if (weapon != null)
		{
			builder.put(EquipmentInventorySlot.WEAPON, weapon);
		}

		if (weapon == null || !weapon.is2h())
		{
			builder.put(EquipmentInventorySlot.SHIELD, AVERNIC_DEFENDER);
		}
		return builder.build();
	}

}
