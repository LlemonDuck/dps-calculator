package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AhrimsAutocastGearBonus implements GearBonusComputable
{

	private static final Set<Integer> AHRIMS_STAFF_IDS = ImmutableSet.of(
		ItemID.AHRIMS_STAFF,
		ItemID.AHRIMS_STAFF_100,
		ItemID.AHRIMS_STAFF_75,
		ItemID.AHRIMS_STAFF_50,
		ItemID.AHRIMS_STAFF_25
	);

	private static final Set<Integer> AHRIMS_HOOD_IDS = ImmutableSet.of(
		ItemID.AHRIMS_HOOD,
		ItemID.AHRIMS_HOOD_100,
		ItemID.AHRIMS_HOOD_75,
		ItemID.AHRIMS_HOOD_50,
		ItemID.AHRIMS_HOOD_25
	);

	private static final Set<Integer> AHRIMS_ROBETOP_IDS = ImmutableSet.of(
		ItemID.AHRIMS_ROBETOP,
		ItemID.AHRIMS_ROBETOP_100,
		ItemID.AHRIMS_ROBETOP_75,
		ItemID.AHRIMS_ROBETOP_50,
		ItemID.AHRIMS_ROBETOP_25
	);

	private static final Set<Integer> AHRIMS_ROBESKIRT_IDS = ImmutableSet.of(
		ItemID.AHRIMS_ROBESKIRT,
		ItemID.AHRIMS_ROBESKIRT_100,
		ItemID.AHRIMS_ROBESKIRT_75,
		ItemID.AHRIMS_ROBESKIRT_50,
		ItemID.AHRIMS_ROBESKIRT_25
	);

	private static final Set<Integer> AMULET_OF_THE_DAMNED = ImmutableSet.of(
		ItemID.AMULET_OF_THE_DAMNED,
		ItemID.AMULET_OF_THE_DAMNED_FULL
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		if (attackStyle.getAttackType() != AttackType.MAGIC)
		{
			return false;
		}

		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		boolean ahrimsWithAmulet = AHRIMS_STAFF_IDS.contains(equipment.get(EquipmentInventorySlot.WEAPON)) &&
			AHRIMS_HOOD_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD)) &&
			AHRIMS_ROBETOP_IDS.contains(equipment.get(EquipmentInventorySlot.BODY)) &&
			AHRIMS_ROBESKIRT_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS)) &&
			AMULET_OF_THE_DAMNED.contains(equipment.get(EquipmentInventorySlot.AMULET));
		if (!ahrimsWithAmulet)
		{
			return false;
		}

		if (attackStyle.isManualCast())
		{
			context.warn("Ahrim's with amulet of the damned only provides 30% damage bonus when autocasting.");
			return false;
		}

		return true;
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		return GearBonuses.of(1.0, 1.3);
	}
}
