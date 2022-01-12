package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.ItemStats;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

import static net.runelite.api.EquipmentInventorySlot.*;
import static net.runelite.api.EquipmentInventorySlot.LEGS;
import static net.runelite.api.ItemID.*;

@RequiredArgsConstructor
public enum EquipmentRequirement
{
	VOID_MAGE(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, GLOVES}, new int[][] {{VOID_MAGE_HELM}, {VOID_KNIGHT_TOP, ELITE_VOID_TOP}, {VOID_KNIGHT_ROBE, ELITE_VOID_ROBE}, {VOID_KNIGHT_GLOVES}}),
	VOID_MELEE(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, GLOVES}, new int[][] {{VOID_MELEE_HELM}, {VOID_KNIGHT_TOP, ELITE_VOID_TOP}, {VOID_KNIGHT_ROBE, ELITE_VOID_ROBE}, {VOID_KNIGHT_GLOVES}}),
	VOID_RANGED(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, GLOVES}, new int[][] {{VOID_RANGER_HELM}, {VOID_KNIGHT_TOP, ELITE_VOID_TOP}, {VOID_KNIGHT_ROBE, ELITE_VOID_ROBE}, {VOID_KNIGHT_GLOVES}}),
	VOID_ELITE(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, GLOVES}, new int[][] {{VOID_MAGE_HELM, VOID_MELEE_HELM, VOID_RANGER_HELM}, {ELITE_VOID_TOP}, {ELITE_VOID_ROBE}, {VOID_KNIGHT_GLOVES}}),
	
	BLACK_MASK_MELEE(new EquipmentInventorySlot[] {HEAD}, new int[][] {{BLACK_MASK, SLAYER_HELMET, BLACK_SLAYER_HELMET, GREEN_SLAYER_HELMET, RED_SLAYER_HELMET, PURPLE_SLAYER_HELMET, TURQUOISE_SLAYER_HELMET, HYDRA_SLAYER_HELMET, TWISTED_SLAYER_HELMET, TZKAL_SLAYER_HELMET, TZTOK_SLAYER_HELMET, VAMPYRIC_SLAYER_HELMET}}),
	BLACK_MASK_MAGE_RANGED(new EquipmentInventorySlot[] {HEAD}, new int[][] {{BLACK_MASK_I, BLACK_MASK_I_25276, SLAYER_HELMET_I, SLAYER_HELMET_I_25177, BLACK_SLAYER_HELMET_I, BLACK_SLAYER_HELMET_I_25179, GREEN_SLAYER_HELMET_I, GREEN_SLAYER_HELMET_I_25181, RED_SLAYER_HELMET_I, RED_SLAYER_HELMET_I_25183, PURPLE_SLAYER_HELMET_I, PURPLE_SLAYER_HELMET_I_25185, TURQUOISE_SLAYER_HELMET_I, TURQUOISE_SLAYER_HELMET_I_25187, HYDRA_SLAYER_HELMET_I, HYDRA_SLAYER_HELMET_I_25189, TWISTED_SLAYER_HELMET_I, TWISTED_SLAYER_HELMET_I_25191, TZKAL_SLAYER_HELMET_I, TZKAL_SLAYER_HELMET_I_25914, TZTOK_SLAYER_HELMET_I, TZTOK_SLAYER_HELMET_I_25902, VAMPYRIC_SLAYER_HELMET_I, VAMPYRIC_SLAYER_HELMET_I_25908}}),
	
	SALVE_MELEE(new EquipmentInventorySlot[] {AMULET}, new int[][] {{SALVE_AMULET, SALVE_AMULETI, SALVE_AMULET_E, SALVE_AMULETEI}}),
	SALVE_MAGE_RANGED(new EquipmentInventorySlot[] {AMULET}, new int[][] {{SALVE_AMULETI, SALVE_AMULETEI}}),
	SALVE_ENHANCED(new EquipmentInventorySlot[] {AMULET}, new int[][] {{SALVE_AMULET_E, SALVE_AMULETEI}}),

	AMULET_DAMNED(new EquipmentInventorySlot[] {AMULET}, new int[][] {{AMULET_OF_THE_DAMNED, AMULET_OF_THE_DAMNED_FULL}}),
	AHRIMS(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, WEAPON}, new int[][] {{AHRIMS_HOOD}, {AHRIMS_ROBETOP, AHRIMS_ROBETOP_20598}, {AHRIMS_ROBESKIRT, AHRIMS_ROBESKIRT_20599}, {AHRIMS_STAFF, AHRIMS_STAFF_23653}}),
	DHAROKS(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, WEAPON}, new int[][] {{DHAROKS_HELM}, {DHAROKS_PLATEBODY}, {DHAROKS_PLATELEGS}, {DHAROKS_GREATAXE}}),
	KARIL(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, WEAPON}, new int[][] {{KARILS_COIF}, {KARILS_LEATHERTOP}, {KARILS_LEATHERSKIRT}, {KARILS_CROSSBOW}}),
	VERACS(new EquipmentInventorySlot[] {HEAD, BODY, LEGS, WEAPON}, new int[][] {{VERACS_HELM}, {VERACS_BRASSARD}, {VERACS_PLATESKIRT}, {VERACS_FLAIL}}),
	
	MAGE_CHAOS_GAUNTLETS(new EquipmentInventorySlot[] {GLOVES}, new int[][] {{CHAOS_GAUNTLETS}}),
	FIRE_TOME(new EquipmentInventorySlot[] {SHIELD}, new int[][] {{TOME_OF_FIRE}}),
	
	DRAGON_HUNTER(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{DRAGON_HUNTER_LANCE, DRAGON_HUNTER_CROSSBOW}}),
	TBOW(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{TWISTED_BOW}}),
	SCYTHE(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{SCYTHE_OF_VITUR, HOLY_SCYTHE_OF_VITUR, SANGUINE_SCYTHE_OF_VITUR}}),
	
	LEAF_BLADED_MELEE(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{LEAFBLADED_BATTLEAXE, LEAFBLADED_SPEAR, LEAFBLADED_SWORD}}),
	LEAF_BLADED_BAXE(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{LEAFBLADED_BATTLEAXE}}),
	LEAF_BLADED_RANGED(new EquipmentInventorySlot[] {AMMO}, new int[][] {{BROAD_BOLTS, BROAD_ARROWS_4160, AMETHYST_BROAD_BOLTS}}),
	
	OBSIDIAN_WEAPON(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{TOKTZXILEK, TOKTZXILAK, TOKTZXILAK_20554, TZHAARKETEM, TZHAARKETOM, TZHAARKETOM_T}}),
	OBSIDIAN_ARMOUR(new EquipmentInventorySlot[] {HEAD, BODY, LEGS}, new int[][] {{OBSIDIAN_HELMET}, {OBSIDIAN_PLATEBODY}, {OBSIDIAN_PLATELEGS}}),
	OBSIDIAN_NECKLACE(new EquipmentInventorySlot[] {AMULET}, new int[][] {{BERSERKER_NECKLACE, BERSERKER_NECKLACE_OR}}),
	
	INQUISITOR_HELM(new EquipmentInventorySlot[] {HEAD}, new int[][] {{INQUISITORS_GREAT_HELM}}),
	INQUISITOR_BODY(new EquipmentInventorySlot[] {BODY}, new int[][] {{INQUISITORS_HAUBERK}}),
	INQUISITOR_LEGS(new EquipmentInventorySlot[] {LEGS}, new int[][] {{INQUISITORS_PLATESKIRT}}),
	INQUISITOR_FULL(new EquipmentInventorySlot[] {HEAD, BODY, LEGS}, new int[][] {{INQUISITORS_GREAT_HELM}, {INQUISITORS_HAUBERK}, {INQUISITORS_PLATESKIRT}}),
	
	DEMONBANE_SILVERLIGHT(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{SILVERLIGHT}}),
	DEMONBANE_DARKLIGHT(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{DARKLIGHT}}),
	DEMONBANE_ARCLIGHT(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{ARCLIGHT}}),
	
	CRYSTAL_BOW(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{ItemID.CRYSTAL_BOW, BOW_OF_FAERDHINEN, BOW_OF_FAERDHINEN_C, BOW_OF_FAERDHINEN_C_25869, BOW_OF_FAERDHINEN_C_25884, BOW_OF_FAERDHINEN_C_25886, BOW_OF_FAERDHINEN_C_25888, BOW_OF_FAERDHINEN_C_25890, BOW_OF_FAERDHINEN_C_25892, BOW_OF_FAERDHINEN_C_25894, BOW_OF_FAERDHINEN_C_25896}}),
	CRYSTAL_HEAD(new EquipmentInventorySlot[] {HEAD}, new int[][] {{ItemID.CRYSTAL_HELM}}),
	CRYSTAL_BODY(new EquipmentInventorySlot[] {BODY}, new int[][] {{ItemID.CRYSTAL_BODY}}),
	CRYSTAL_LEGS(new EquipmentInventorySlot[] {LEGS}, new int[][] {{ItemID.CRYSTAL_LEGS}}),
	
	WILDY_MELEE(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{VIGGORAS_CHAINMACE}}),
	WILDY_RANGED(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{CRAWS_BOW}}),
	WILDY_MAGIC(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{THAMMARONS_SCEPTRE}}),
	
	HARM_STAFF(new EquipmentInventorySlot[] {WEAPON}, new int[][] {{HARMONISED_NIGHTMARE_STAFF}}),
	;

	private final EquipmentInventorySlot[] slots;
	private final int[][] items;

	private static boolean arrayContains(int[] arr, int value)
	{
		// java really is missing this in its stdlib huh
		for (int a : arr)
			if (a == value)
				return true;
		return false;
	}

	public boolean isSatisfied(CalcInput input)
	{
		Map<EquipmentInventorySlot, ItemStats> equipment = input.getPlayerEquipment();
		return isSatisfied(equipment);
	}

	public boolean isSatisfied(Map<EquipmentInventorySlot, ItemStats> equipment)
	{
		for (int i = 0; i < slots.length; i++)
		{
			ItemStats slotItem = equipment.get(slots[i]);
			if (slotItem == null)
				return false;

			if (!arrayContains(items[i], slotItem.getItemId()))
				return false;
		}
		return true;
	}
}