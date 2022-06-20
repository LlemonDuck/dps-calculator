package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc.gearbonus.KerisGearBonus;
import com.duckblade.osrs.dpscalc.calc.maxhit.PreLimitBaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class KerisDptComputable implements MultiHitDptComputable
{

	public static final ComputeOutput<Integer> KERIS_MAX_HIT = ComputeOutput.of("MaxHit-Keris");

	private final WeaponComputable weaponComputable;
	private final BaseHitDptComputable baseHitDptComputable;
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

		return KerisGearBonus.KERIS_IDS.contains(context.get(weaponComputable).getItemId()) &&
			context.get(ComputeInputs.DEFENDER_ATTRIBUTES).isKalphite();
	}

	@Override
	public Double compute(ComputeContext context)
	{
		double baseDps = context.get(baseHitDptComputable);

		// 1/51 chance to deal triple damage
		double hitChance = context.get(hitChanceComputable);
		int effectMaxHit = maxHitLimitComputable.coerce(3 * context.get(preLimitBaseMaxHitComputable), context);
		int attackSpeed = context.get(attackSpeedComputable);

		double tripleHitDps = BaseHitDptComputable.byComponents(hitChance, effectMaxHit, attackSpeed);
		context.put(KERIS_MAX_HIT, effectMaxHit);

		return (50.0 / 51.0) * baseDps + (1.0 / 51.0) * tripleHitDps;
	}

}
