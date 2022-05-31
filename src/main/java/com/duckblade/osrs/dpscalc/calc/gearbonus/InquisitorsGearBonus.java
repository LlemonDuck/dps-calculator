package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
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
public class InquisitorsGearBonus implements GearBonusComputable
{

	private static final Set<Integer> INQ_HELM_IDS = ImmutableSet.of(
		ItemID.INQUISITORS_GREAT_HELM
	);

	private static final Set<Integer> INQ_BODY_IDS = ImmutableSet.of(
		ItemID.INQUISITORS_HAUBERK
	);

	private static final Set<Integer> INQ_LEGS_IDS = ImmutableSet.of(
		ItemID.INQUISITORS_PLATESKIRT
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		return INQ_HELM_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD)) ||
			INQ_BODY_IDS.contains(equipment.get(EquipmentInventorySlot.BODY)) ||
			INQ_LEGS_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS));
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		if (context.get(ComputeInputs.ATTACK_STYLE).getAttackType() != AttackType.CRUSH)
		{
			context.warn("Wearing inquisitor's armour without attacking on crush provides no bonuses.");
			return GearBonuses.EMPTY;
		}

		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		boolean helm = INQ_HELM_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD));
		boolean body = INQ_BODY_IDS.contains(equipment.get(EquipmentInventorySlot.BODY));
		boolean legs = INQ_LEGS_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS));

		double inqMod = 1.0;
		if (helm && body && legs)
		{
			// full set gives extra bonus
			inqMod += 0.025;
		}
		else
		{
			inqMod += (helm ? 0.005 : 0.0) +
				(body ? 0.005 : 0.0) +
				(legs ? 0.005 : 0.0);
		}

		return GearBonuses.symmetric(inqMod);
	}

}
