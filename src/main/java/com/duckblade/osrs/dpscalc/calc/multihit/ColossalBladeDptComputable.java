package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc.maxhit.PreLimitBaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ColossalBladeDptComputable implements MultiHitDptComputable
{

	public static final ComputeOutput<Integer> COLOSSAL_BLADE_MAX_HIT = ComputeOutput.of("MaxHit-ColossalBlade");

	private static final Set<Integer> COLOSSAL_BLADE_IDS = ImmutableSet.of(
		ItemID.COLOSSAL_BLADE
	);

	private final WeaponComputable weaponComputable;
	private final HitChanceComputable hitChanceComputable;
	private final AttackSpeedComputable attackSpeedComputable;
	private final PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;
	private final MaxHitLimitComputable maxHitLimitComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee() &&
			COLOSSAL_BLADE_IDS.contains(context.get(weaponComputable).getItemId());
	}

	@Override
	public Double compute(ComputeContext context)
	{
		double hitChance = context.get(hitChanceComputable);
		int attackSpeed = context.get(attackSpeedComputable);

		int size = Math.max(1, Math.min(5, context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getSize()));
		int effectMaxHit = maxHitLimitComputable.coerce(2 * size + context.get(preLimitBaseMaxHitComputable), context);
		context.put(COLOSSAL_BLADE_MAX_HIT, effectMaxHit);

		return BaseHitDptComputable.byComponents(hitChance, effectMaxHit, attackSpeed);
	}

}
