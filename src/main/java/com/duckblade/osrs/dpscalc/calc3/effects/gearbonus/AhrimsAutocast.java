package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.EquipmentIds;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor
public class AhrimsAutocast implements GearBonusOperation
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

	private final EquipmentIds equipmentIds;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		AttackStyle attackStyle = ctx.get(ComputeInputs.ATTACK_STYLE);
		if (attackStyle.getAttackType() != AttackType.MAGIC)
		{
			return false;
		}

		Map<EquipmentInventorySlot, Integer> equipment = ctx.get(equipmentIds);
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
			ctx.warn("Ahrim's with amulet of the damned only provides 30% damage bonus when autocasting.");
			return false;
		}

		return true;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		return new GearBonus(null, new Multiplication(13, 10));
	}

}
