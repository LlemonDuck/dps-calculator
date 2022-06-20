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
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DharoksDptComputable implements MultiHitDptComputable
{

	public static final ComputeOutput<Integer> DHAROKS_MAX_HIT = ComputeOutput.of("MaxHit-Dharoks");

	private static final Set<Integer> DHAROKS_GREATAXE_IDS = ImmutableSet.of(
		ItemID.DHAROKS_GREATAXE,
		ItemID.DHAROKS_GREATAXE_100,
		ItemID.DHAROKS_GREATAXE_75,
		ItemID.DHAROKS_GREATAXE_50,
		ItemID.DHAROKS_GREATAXE_25
	);

	private static final Set<Integer> DHAROKS_HELM_IDS = ImmutableSet.of(
		ItemID.DHAROKS_HELM,
		ItemID.DHAROKS_HELM_100,
		ItemID.DHAROKS_HELM_75,
		ItemID.DHAROKS_HELM_50,
		ItemID.DHAROKS_HELM_25
	);

	private static final Set<Integer> DHAROKS_PLATEBODY_IDS = ImmutableSet.of(
		ItemID.DHAROKS_PLATEBODY,
		ItemID.DHAROKS_PLATEBODY_100,
		ItemID.DHAROKS_PLATEBODY_75,
		ItemID.DHAROKS_PLATEBODY_50,
		ItemID.DHAROKS_PLATEBODY_25
	);

	private static final Set<Integer> DHAROKS_PLATELEGS_IDS = ImmutableSet.of(
		ItemID.DHAROKS_PLATELEGS,
		ItemID.DHAROKS_PLATELEGS_100,
		ItemID.DHAROKS_PLATELEGS_75,
		ItemID.DHAROKS_PLATELEGS_50,
		ItemID.DHAROKS_PLATELEGS_25
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;
	private final HitChanceComputable hitChanceComputable;
	private final PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;
	private final MaxHitLimitComputable maxHitLimitComputable;
	private final AttackSpeedComputable attackSpeedComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		if (!context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee())
		{
			return false;
		}

		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		return DHAROKS_GREATAXE_IDS.contains(equipment.get(EquipmentInventorySlot.WEAPON)) &&
			DHAROKS_HELM_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD)) &&
			DHAROKS_PLATEBODY_IDS.contains(equipment.get(EquipmentInventorySlot.BODY)) &&
			DHAROKS_PLATELEGS_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS));
	}

	@Override
	public Double compute(ComputeContext context)
	{
		Skills attackerSkills = context.get(ComputeInputs.ATTACKER_SKILLS);
		int maxHp = attackerSkills.getLevels().getOrDefault(Skill.HITPOINTS, 99);
		int currentHp = attackerSkills.getTotals().getOrDefault(Skill.HITPOINTS, maxHp);
		double dharokMod = 1 + ((maxHp - currentHp) / 100.0) * (maxHp / 100.0);

		int effectMaxHit = maxHitLimitComputable.coerce((int) (context.get(preLimitBaseMaxHitComputable) * dharokMod), context);
		context.put(DHAROKS_MAX_HIT, effectMaxHit);

		double hitChance = context.get(hitChanceComputable);
		int attackSpeed = context.get(attackSpeedComputable);
		return BaseHitDptComputable.byComponents(hitChance, effectMaxHit, attackSpeed);
	}

}
