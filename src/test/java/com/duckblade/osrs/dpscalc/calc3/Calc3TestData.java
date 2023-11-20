package com.duckblade.osrs.dpscalc.calc3;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc3.model.DefensiveStats;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc3.model.Skills;
import com.duckblade.osrs.dpscalc.calc3.model.WeaponCategory;
import com.google.common.collect.ImmutableMap;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;

public class Calc3TestData
{

	public static void maxMelee(ComputeContext ctx, ItemStats weapon)
	{
		ctx.put(ComputeInputs.ATTACKER_SKILLS, Skills.builder()
			.level(Skill.ATTACK, 99)
			.level(Skill.STRENGTH, 99)
			.boost(Skill.ATTACK, 19)
			.boost(Skill.STRENGTH, 19)
			.build());

		ImmutableMap.Builder<EquipmentInventorySlot, ItemStats> builder = ImmutableMap.<EquipmentInventorySlot, ItemStats>builder()
			.put(EquipmentInventorySlot.HEAD, ItemStats.builder()
				.name("Torva full helm")
				.itemId(ItemID.TORVA_FULL_HELM)
				.accuracyMagic(-5)
				.accuracyRanged(-5)
				.strengthMelee(8)
				.prayer(1)
				.build())
			.put(EquipmentInventorySlot.CAPE, ItemStats.builder()
				.name("Infernal cape")
				.itemId(ItemID.INFERNAL_CAPE)
				.accuracyStab(4)
				.accuracySlash(4)
				.accuracyCrush(4)
				.accuracyRanged(1)
				.accuracyMagic(1)
				.strengthMelee(8)
				.prayer(2)
				.build())
			.put(EquipmentInventorySlot.AMULET, ItemStats.builder()
				.name("Amulet of torture")
				.itemId(ItemID.AMULET_OF_TORTURE)
				.accuracyStab(15)
				.accuracySlash(15)
				.accuracyCrush(15)
				.strengthMelee(10)
				.prayer(2)
				.build())
			.put(EquipmentInventorySlot.AMMO, ItemStats.builder()
				.name("Rada's blessing 4")
				.itemId(ItemID.RADAS_BLESSING_4)
				.prayer(2)
				.build())
			.put(EquipmentInventorySlot.BODY, ItemStats.builder()
				.name("Torva platebody")
				.itemId(ItemID.TORVA_PLATEBODY)
				.accuracyMagic(-18)
				.accuracyRanged(-14)
				.strengthMelee(6)
				.prayer(1)
				.build())
			.put(EquipmentInventorySlot.LEGS, ItemStats.builder()
				.name("Torva platelegs")
				.itemId(ItemID.TORVA_PLATELEGS)
				.accuracyMagic(-24)
				.accuracyRanged(-11)
				.strengthMelee(4)
				.prayer(1)
				.build())
			.put(EquipmentInventorySlot.GLOVES, ItemStats.builder()
				.name("Ferocious gloves")
				.itemId(ItemID.FEROCIOUS_GLOVES)
				.accuracyStab(16)
				.accuracySlash(16)
				.accuracyCrush(16)
				.accuracyMagic(-16)
				.accuracyRanged(-16)
				.strengthMelee(14)
				.build())
			.put(EquipmentInventorySlot.BOOTS, ItemStats.builder()
				.name("Primordial boots")
				.itemId(ItemID.PRIMORDIAL_BOOTS)
				.accuracyStab(2)
				.accuracySlash(2)
				.accuracyCrush(2)
				.accuracyMagic(-4)
				.accuracyRanged(-1)
				.strengthMelee(5)
				.build())
			.put(EquipmentInventorySlot.RING, ItemStats.builder()
				.name("Ultor ring")
				.itemId(ItemID.ULTOR_RING)
				.strengthMelee(12)
				.build())
			.put(EquipmentInventorySlot.WEAPON, weapon);

		if (!weapon.is2h())
		{
			builder.put(EquipmentInventorySlot.SHIELD, ItemStats.builder()
				.name("Avernic defender")
				.itemId(ItemID.AVERNIC_DEFENDER)
				.accuracyStab(30)
				.accuracySlash(29)
				.accuracyCrush(28)
				.accuracyMagic(-5)
				.accuracyRanged(-4)
				.strengthMelee(8)
				.build());
		}

		ctx.put(ComputeInputs.ATTACKER_ITEMS, builder.build());
		ctx.put(ComputeInputs.ATTACK_STYLE, weapon.getWeaponCategory().getAttackStyles().get(2));
//		ctx.put(ComputeInputs.ATTACKER_PRAYERS, Collections.singleton(Prayer.PIETY));
	}

	public static ItemStats kerisPartisanBreaching()
	{
		return ItemStats.builder()
			.name("Keris partisan of breaching")
			.itemId(ItemID.KERIS_PARTISAN_OF_BREACHING)
			.accuracyStab(58)
			.accuracySlash(-2)
			.accuracyCrush(57)
			.accuracyMagic(2)
			.strengthMelee(45)
			.prayer(3)
			.weaponCategory(WeaponCategory.PARTISAN)
			.speed(4)
			.build();
	}

	public static void kalphiteQueen(ComputeContext ctx)
	{
		ctx.put(ComputeInputs.DEFENDER_ATTRIBUTES, DefenderAttributes.builder()
			.isKalphite(true)
			.name("Kalphite Queen")
			.npcId(NpcID.KALPHITE_QUEEN_963)
			.build());
		ctx.put(ComputeInputs.DEFENDER_STATS, DefensiveStats.builder()
			.levelDefence(300)
			.levelMagic(150)
			.defenceStab(50)
			.defenceSlash(50)
			.defenceCrush(10)
			.defenceMagic(100)
			.defenceRanged(100)
			.accuracyMagic(0)
			.build());
	}

}
