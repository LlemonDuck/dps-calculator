package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ObsidianGearBonus implements GearBonusComputable
{
	private static final Set<Integer> OBSIDIAN_WEAPONS = ImmutableSet.of(
		// Is the staff affected? Wiki is unclear. Will anybody ever use it? We'll find out.
		// ItemID.TOKTZMEJTAL,
		ItemID.TOKTZXILAK,
		ItemID.TOKTZXILEK,
		ItemID.TZHAARKETEM,
		ItemID.TZHAARKETOM,
		ItemID.TZHAARKETOM_T
	);

	private static final Set<Integer> OBSIDIAN_HELMETS = ImmutableSet.of(
		ItemID.OBSIDIAN_HELMET
	);

	private static final Set<Integer> OBSIDIAN_PLATEBODIES = ImmutableSet.of(
		ItemID.OBSIDIAN_PLATEBODY
	);

	private static final Set<Integer> OBSIDIAN_PLATELEGS = ImmutableSet.of(
		ItemID.OBSIDIAN_PLATELEGS
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		// Obsidian armour bonuses only apply when attacking i.e. manual casting is forbidden
		boolean castingSpell = context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC;
		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);

		return OBSIDIAN_WEAPONS.contains(equipment.get(EquipmentInventorySlot.WEAPON)) &&
			OBSIDIAN_HELMETS.contains(equipment.get(EquipmentInventorySlot.HEAD)) &&
			OBSIDIAN_PLATEBODIES.contains(equipment.get(EquipmentInventorySlot.BODY)) &&
			OBSIDIAN_PLATELEGS.contains(equipment.get(EquipmentInventorySlot.LEGS)) &&
			!castingSpell;
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		return GearBonuses.symmetric(1.1);
	}

}

