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
public class BerserkerNecklaceGearBonus implements GearBonusComputable
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

	private static final Set<Integer> BERSERKER_NECKLACES = ImmutableSet.of(
		ItemID.BERSERKER_NECKLACE,
		ItemID.BERSERKER_NECKLACE_OR
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		// Obsidian armour bonuses only apply when attacking i.e. manual casting is forbidden
		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		boolean castingSpell = context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC;
		return OBSIDIAN_WEAPONS.contains(equipment.get(EquipmentInventorySlot.WEAPON)) &&
			BERSERKER_NECKLACES.contains(equipment.get(EquipmentInventorySlot.AMULET)) &&
			!castingSpell;
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		return GearBonuses.of(1.0, 1.2);
	}

}


