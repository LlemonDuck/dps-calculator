package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc.maxhit.PreLimitBaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
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
public class KarilsDptComputable implements MultiHitDptComputable
{

	public static final ComputeOutput<Integer> KARILS_MAX_HIT = ComputeOutput.of("MaxHit-Karils");

	private static final Set<Integer> KARILS_CROSSBOW_IDS = ImmutableSet.of(
		ItemID.KARILS_CROSSBOW,
		ItemID.KARILS_CROSSBOW_100,
		ItemID.KARILS_CROSSBOW_75,
		ItemID.KARILS_CROSSBOW_50,
		ItemID.KARILS_CROSSBOW_25
	);

	private static final Set<Integer> KARILS_COIF_IDS = ImmutableSet.of(
		ItemID.KARILS_COIF,
		ItemID.KARILS_COIF_100,
		ItemID.KARILS_COIF_75,
		ItemID.KARILS_COIF_50,
		ItemID.KARILS_COIF_25
	);

	private static final Set<Integer> KARILS_LEATHERTOP_IDS = ImmutableSet.of(
		ItemID.KARILS_LEATHERTOP,
		ItemID.KARILS_LEATHERTOP_100,
		ItemID.KARILS_LEATHERTOP_75,
		ItemID.KARILS_LEATHERTOP_50,
		ItemID.KARILS_LEATHERTOP_25
	);

	private static final Set<Integer> KARILS_LEATHERSKIRT_IDS = ImmutableSet.of(
		ItemID.KARILS_LEATHERSKIRT,
		ItemID.KARILS_LEATHERSKIRT_100,
		ItemID.KARILS_LEATHERSKIRT_75,
		ItemID.KARILS_LEATHERSKIRT_50,
		ItemID.KARILS_LEATHERSKIRT_25
	);

	private static final Set<Integer> AMULET_OF_THE_DAMNED = ImmutableSet.of(
		ItemID.AMULET_OF_THE_DAMNED,
		ItemID.AMULET_OF_THE_DAMNED_FULL
	);

	private final BaseHitDptComputable baseHitDptComputable;
	private final EquipmentItemIdsComputable equipmentItemIdsComputable;
	private final HitChanceComputable hitChanceComputable;
	private final PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;
	private final MaxHitLimitComputable maxHitLimitComputable;
	private final AttackSpeedComputable attackSpeedComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		if (context.get(ComputeInputs.ATTACK_STYLE).getAttackType() != AttackType.RANGED)
		{
			return false;
		}

		// karils effect also requires amulet of the damned
		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		boolean amulet = AMULET_OF_THE_DAMNED.contains(equipment.get(EquipmentInventorySlot.AMULET));
		boolean fullKarils = KARILS_CROSSBOW_IDS.contains(equipment.get(EquipmentInventorySlot.WEAPON)) &&
			KARILS_COIF_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD)) &&
			KARILS_LEATHERTOP_IDS.contains(equipment.get(EquipmentInventorySlot.BODY)) &&
			KARILS_LEATHERSKIRT_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS));

		if (!amulet && fullKarils)
		{
			context.warn("Karil's equipment only provides a beneficial set effect with the Amulet of the damned.");
		}
		return amulet && fullKarils;
	}

	@Override
	public Double compute(ComputeContext context)
	{
		double baseDps = context.get(baseHitDptComputable);

		int effectMaxHit = maxHitLimitComputable.coerce((int) (1.5 * context.get(preLimitBaseMaxHitComputable)), context);
		context.put(KARILS_MAX_HIT, effectMaxHit);

		double hitChance = context.get(hitChanceComputable);
		int attackSpeed = context.get(attackSpeedComputable);

		// 25% chance to deal 1.5x damage
		double specialDps = BaseHitDptComputable.byComponents(hitChance, effectMaxHit, attackSpeed);
		return 0.75 * baseDps + 0.25 * specialDps;
	}

}
