package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.MaxHitComputable;
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
public class VeracsDptComputable implements MultiHitDptComputable
{

	private static final Set<Integer> VERACS_FLAIL_IDS = ImmutableSet.of(
		ItemID.VERACS_FLAIL,
		ItemID.VERACS_FLAIL_100,
		ItemID.VERACS_FLAIL_75,
		ItemID.VERACS_FLAIL_50,
		ItemID.VERACS_FLAIL_25
	);

	private static final Set<Integer> VERACS_HELM_IDS = ImmutableSet.of(
		ItemID.VERACS_HELM,
		ItemID.VERACS_HELM_100,
		ItemID.VERACS_HELM_75,
		ItemID.VERACS_HELM_50,
		ItemID.VERACS_HELM_25
	);

	private static final Set<Integer> VERACS_BRASSARD_IDS = ImmutableSet.of(
		ItemID.VERACS_BRASSARD,
		ItemID.VERACS_BRASSARD_100,
		ItemID.VERACS_BRASSARD_75,
		ItemID.VERACS_BRASSARD_50,
		ItemID.VERACS_BRASSARD_25
	);

	private static final Set<Integer> VERACS_PLATESKIRT_IDS = ImmutableSet.of(
		ItemID.VERACS_PLATESKIRT,
		ItemID.VERACS_PLATESKIRT_100,
		ItemID.VERACS_PLATESKIRT_75,
		ItemID.VERACS_PLATESKIRT_50,
		ItemID.VERACS_PLATESKIRT_25
	);

	private final BaseHitDptComputable baseHitDptComputable;
	private final EquipmentItemIdsComputable equipmentItemIdsComputable;
	private final MaxHitComputable maxHitComputable;
	private final AttackSpeedComputable attackSpeedComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		if (!context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee())
		{
			return false;
		}

		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		return VERACS_FLAIL_IDS.contains(equipment.get(EquipmentInventorySlot.WEAPON)) &&
			VERACS_HELM_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD)) &&
			VERACS_BRASSARD_IDS.contains(equipment.get(EquipmentInventorySlot.BODY)) &&
			VERACS_PLATESKIRT_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS));
	}

	@Override
	public Double compute(ComputeContext context)
	{
		double baseDps = context.get(baseHitDptComputable);

		int maxHit = context.get(maxHitComputable);
		int attackSpeed = context.get(attackSpeedComputable);

		// special effect is 25% chance to ignore defence and +1 damage to hit (we add 2 to overcome the mean division)
		double specialDps = BaseHitDptComputable.byComponents(1.0, maxHit + 2, attackSpeed);
		return 0.75 * baseDps + 0.25 * specialDps;
	}

}
