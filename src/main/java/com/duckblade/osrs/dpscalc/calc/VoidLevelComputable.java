package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.VoidLevel;
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
public class VoidLevelComputable implements Computable<VoidLevel>
{

	private static final Set<Integer> VOID_MELEE_HELMS = ImmutableSet.of(
		ItemID.VOID_MELEE_HELM,
		ItemID.VOID_MELEE_HELM_L,
		ItemID.VOID_MELEE_HELM_OR
	);

	private static final Set<Integer> VOID_RANGER_HELMS = ImmutableSet.of(
		ItemID.VOID_RANGER_HELM,
		ItemID.VOID_RANGER_HELM_L,
		ItemID.VOID_RANGER_HELM_OR
	);

	private static final Set<Integer> VOID_MAGE_HELMS = ImmutableSet.of(
		ItemID.VOID_MAGE_HELM,
		ItemID.VOID_MAGE_HELM_L,
		ItemID.VOID_MAGE_HELM_OR
	);

	private static final Set<Integer> VOID_KNIGHT_TOPS = ImmutableSet.of(
		ItemID.VOID_KNIGHT_TOP,
		ItemID.VOID_KNIGHT_TOP_L,
		ItemID.VOID_KNIGHT_TOP_OR
	);

	private static final Set<Integer> ELITE_VOID_TOPS = ImmutableSet.of(
		ItemID.ELITE_VOID_TOP,
		ItemID.ELITE_VOID_TOP_L,
		ItemID.ELITE_VOID_TOP_OR
	);

	private static final Set<Integer> VOID_KNIGHT_ROBES = ImmutableSet.of(
		ItemID.VOID_KNIGHT_ROBE,
		ItemID.VOID_KNIGHT_ROBE_L,
		ItemID.VOID_KNIGHT_ROBE_OR
	);

	private static final Set<Integer> ELITE_VOID_ROBES = ImmutableSet.of(
		ItemID.ELITE_VOID_ROBE,
		ItemID.ELITE_VOID_ROBE_L,
		ItemID.ELITE_VOID_ROBE_OR
	);

	private static final Set<Integer> VOID_KNIGHT_GLOVES = ImmutableSet.of(
		ItemID.VOID_KNIGHT_GLOVES,
		ItemID.VOID_KNIGHT_GLOVES_L,
		ItemID.VOID_KNIGHT_GLOVES_OR
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public VoidLevel compute(ComputeContext context)
	{
		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		int helm = equipment.get(EquipmentInventorySlot.HEAD);
		int body = equipment.get(EquipmentInventorySlot.BODY);
		int legs = equipment.get(EquipmentInventorySlot.LEGS);
		int gloves = equipment.get(EquipmentInventorySlot.GLOVES);

		boolean wearingHelm = VOID_MELEE_HELMS.contains(helm) || VOID_RANGER_HELMS.contains(helm) || VOID_MAGE_HELMS.contains(helm);
		int topVoidLevel = ELITE_VOID_TOPS.contains(body) ? 2 : VOID_KNIGHT_TOPS.contains(body) ? 1 : 0;
		int bottomVoidLevel = ELITE_VOID_ROBES.contains(legs) ? 2 : VOID_KNIGHT_ROBES.contains(legs) ? 1 : 0;
		boolean wearingGloves = VOID_KNIGHT_GLOVES.contains(gloves);

		boolean anyVoid = wearingHelm || topVoidLevel > 0 || bottomVoidLevel > 0 || wearingGloves;
		if (!anyVoid)
		{
			return VoidLevel.NONE;
		}

		boolean fullVoid = wearingHelm && topVoidLevel > 0 && bottomVoidLevel > 0 && wearingGloves;
		if (!fullVoid)
		{
			context.warn("Wearing incomplete void equipment provides no offensive bonuses.");
			return VoidLevel.NONE;
		}

		AttackType attackMode = context.get(ComputeInputs.ATTACK_STYLE).getAttackType();
		if (!combatModeMatch(attackMode, helm))
		{
			context.warn("Void helm does not matach attack style.");
			return VoidLevel.NONE;
		}

		boolean elite = topVoidLevel == 2 && bottomVoidLevel == 2;
		if (elite)
		{
			return VoidLevel.ELITE;
		}
		return VoidLevel.REGULAR;
	}

	private static boolean combatModeMatch(AttackType attackMode, int helm)
	{
		switch (attackMode)
		{
			case MAGIC:
				return VOID_MAGE_HELMS.contains(helm);

			case RANGED:
				return VOID_RANGER_HELMS.contains(helm);

			default:
				return VOID_MELEE_HELMS.contains(helm);
		}
	}

}
