package com.duckblade.osrs.dpscalc.model;

import com.duckblade.osrs.dpscalc.calc.EquipmentRequirement;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Data
@Builder
public class EquipmentStats
{

	private final int accuracyStab;
	private final int accuracySlash;
	private final int accuracyCrush;
	private final int accuracyMagic;
	private final int accuracyRanged;

	private final int strengthMelee;
	private final int strengthRanged;
	private final int strengthMagic;

	private final int prayer;
	private final int speed;

	private static void addValues(EquipmentStatsBuilder builder, ItemStats i)
	{
		if (i == null)
			return;

		builder.accuracyStab += i.getAccuracyStab();
		builder.accuracySlash += i.getAccuracySlash();
		builder.accuracyCrush += i.getAccuracyCrush();
		builder.accuracyMagic += i.getAccuracyMagic();
		builder.accuracyRanged += i.getAccuracyRanged();
		builder.strengthMelee += i.getStrengthMelee();
		builder.strengthRanged += i.getStrengthRanged();
		builder.strengthMagic += i.getStrengthMagic();
		builder.prayer += i.getPrayer();
	}
	
	public static EquipmentStats fromMap(Map<EquipmentInventorySlot, ItemStats> slotMap, WeaponMode weaponMode, ItemStats tbpDarts)
	{
		ItemStats weapon = slotMap.get(EquipmentInventorySlot.WEAPON);
		EquipmentStatsBuilder b = EquipmentStats.builder();
		slotMap.values().forEach(i -> addValues(b, i));
		
		b.speed = weapon == null ? 4 : weapon.getSpeed();
		if (weaponMode != null && weaponMode.getCombatFocus() == CombatFocus.RAPID)
			b.speed -= 1;
		
		if (weapon != null && weapon.getItemId() == ItemID.TOXIC_BLOWPIPE)
			addValues(b, tbpDarts);
		
		if (EquipmentRequirement.AMULET_DAMNED.isSatisfied(slotMap) && EquipmentRequirement.VERACS.isSatisfied(slotMap))
			b.prayer += 7;
		
		return b.build();
	}

}
