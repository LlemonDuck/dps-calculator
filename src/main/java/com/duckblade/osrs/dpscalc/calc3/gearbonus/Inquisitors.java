package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.MeleeAttackType;
import com.duckblade.osrs.dpscalc.calc3.util.EquipmentIds;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Inquisitors implements GearBonusOperation
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

	private final EquipmentIds equipmentIds;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		Map<EquipmentInventorySlot, Integer> equipment = ctx.get(equipmentIds);
		if (!INQ_HELM_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD)) ||
			!INQ_BODY_IDS.contains(equipment.get(EquipmentInventorySlot.BODY)) ||
			!INQ_LEGS_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS)))
		{
			return false;
		}

		if (ctx.get(ComputeInputs.ATTACK_STYLE).getMeleeAttackType() != MeleeAttackType.CRUSH)
		{
			ctx.warn("Wearing inquisitor's armour without using crush attack style does not provide the set bonus.");
			return false;
		}

		return true;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		Map<EquipmentInventorySlot, Integer> equipment = ctx.get(equipmentIds);
		boolean helm = INQ_HELM_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD));
		boolean body = INQ_BODY_IDS.contains(equipment.get(EquipmentInventorySlot.BODY));
		boolean legs = INQ_LEGS_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS));

		int inqCount = (helm ? 1 : 0) +
			(body ? 1 : 0) +
			(legs ? 1 : 0);

		if (inqCount == 3)
		{
			inqCount = 5;
		}

		return GearBonus.symmetric(new Multiplication(200 + inqCount, 200));
	}

}
