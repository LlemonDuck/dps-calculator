package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import net.runelite.api.NpcID;

@Singleton
public class CombatStyleImmunityMaxHitLimiter implements MaxHitLimiter
{

	private static final Set<Integer> MELEE_IMMUNE = ImmutableSet.of(
		NpcID.AVIANSIE,
		NpcID.AVIANSIE_3170,
		NpcID.AVIANSIE_3171,
		NpcID.AVIANSIE_3172,
		NpcID.AVIANSIE_3173,
		NpcID.AVIANSIE_3174,
		NpcID.AVIANSIE_3175,
		NpcID.AVIANSIE_3176,
		NpcID.AVIANSIE_3177,
		NpcID.AVIANSIE_3178,
		NpcID.AVIANSIE_3179,
		NpcID.AVIANSIE_3180,
		NpcID.AVIANSIE_3181,
		NpcID.AVIANSIE_3182,
		NpcID.AVIANSIE_3183,

		NpcID.KREEARRA,
		NpcID.FLIGHT_KILISA,
		NpcID.WINGMAN_SKREE,
		NpcID.FLOCKLEADER_GEERIN,

		NpcID.ZULRAH,
		NpcID.ZULRAH_2043,
		NpcID.ZULRAH_2044
	);

	private static final Set<Integer> RANGED_IMMUNE = ImmutableSet.of();

	private static final Set<Integer> MAGE_IMMUNE = ImmutableSet.of(
		NpcID.CALLISTO
	);

	private static final Map<AttackType, Set<Integer>> IMMUNITY_MAP = ImmutableMap.of(
		AttackType.STAB, MELEE_IMMUNE,
		AttackType.SLASH, MELEE_IMMUNE,
		AttackType.CRUSH, MELEE_IMMUNE,
		AttackType.RANGED, RANGED_IMMUNE,
		AttackType.MAGIC, MAGE_IMMUNE
	);

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return IMMUNITY_MAP.get(context.get(ComputeInputs.ATTACK_STYLE).getAttackType())
			.contains(context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getNpcId());
	}

	@Override
	public MaxHitLimit compute(ComputeContext context)
	{
		String npcName = context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getName();
		AttackType attackType = context.get(ComputeInputs.ATTACK_STYLE).getAttackType();
		return MaxHitLimit.builder()
			.limit(0)
			.warning(npcName + " cannot be hit by " + attackType.name().toLowerCase() + " attacks.")
			.build();
	}
}
