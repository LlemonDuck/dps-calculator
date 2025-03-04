package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.runelite.api.EquipmentInventorySlot;

public class SlotMapping
{

	public static final BiMap<String, EquipmentInventorySlot> SLOT_NAMES = ImmutableBiMap.<String, EquipmentInventorySlot>builder()
		.put("head", EquipmentInventorySlot.HEAD)
		.put("cape", EquipmentInventorySlot.CAPE)
		.put("neck", EquipmentInventorySlot.AMULET)
		.put("ammo", EquipmentInventorySlot.AMMO)
		.put("weapon", EquipmentInventorySlot.WEAPON)
		.put("body", EquipmentInventorySlot.BODY)
		.put("shield", EquipmentInventorySlot.SHIELD)
		.put("legs", EquipmentInventorySlot.LEGS)
		.put("hands", EquipmentInventorySlot.GLOVES)
		.put("feet", EquipmentInventorySlot.BOOTS)
		.put("ring", EquipmentInventorySlot.RING)
		.build();

}
