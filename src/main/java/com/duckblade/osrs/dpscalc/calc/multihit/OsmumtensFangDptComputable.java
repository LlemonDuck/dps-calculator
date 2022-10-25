package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class OsmumtensFangDptComputable implements MultiHitDptComputable
{

	private static final Set<Integer> FANG_IDS = ImmutableSet.of(
		ItemID.OSMUMTENS_FANG,
		ItemID.OSMUMTENS_FANG_OR
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;
	private final BaseMaxHitComputable baseMaxHitComputable;
	private final HitChanceComputable hitChanceComputable;
	private final AttackSpeedComputable attackSpeedComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee() &&
			FANG_IDS.contains(context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.WEAPON));
	}

	@Override
	public Double compute(ComputeContext context)
	{

		double baseHitChance = context.get(hitChanceComputable);
		double effectHitChance = 1 - Math.pow(1 - baseHitChance, 2);

		// fang clamps damage per hit between 10% and 90% of max
		// which keeps avg hit the same so no work needed to handle that
		// unless there is a max hit limiter in play
		int maxHit = context.get(baseMaxHitComputable);
		if (context.get(MaxHitLimitComputable.LIMIT_APPLIED))
		{
			context.warn("Max hit may be inaccurate due to conflicting effects of a max hit limiter and fang max hit clamping.");
		}

		int attackSpeed = context.get(attackSpeedComputable);
		return BaseHitDptComputable.byComponents(effectHitChance, maxHit, attackSpeed);
	}
}
