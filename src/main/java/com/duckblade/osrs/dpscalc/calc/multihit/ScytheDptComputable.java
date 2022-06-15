package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ScytheDptComputable implements MultiHitDptComputable
{

	public static final ComputeOutput<Integer> SCY_MAX_HIT_2 = ComputeOutput.of("MaxHit-Scy2");
	public static final ComputeOutput<Integer> SCY_MAX_HIT_3 = ComputeOutput.of("MaxHit-Scy3");
	public static final ComputeOutput<Integer> SCY_MAX_HIT_SUM = ComputeOutput.of("MaxHit-ScySum");

	private static final Set<Integer> SCYTHE_IDS = ImmutableSet.of(
		ItemID.SCYTHE_OF_VITUR,
		ItemID.SCYTHE_OF_VITUR_UNCHARGED, // still works, but has reduced stats which is baked into the item stats
		ItemID.SCYTHE_OF_VITUR_22664, // no clue what this is tbh https://chisel.weirdgloop.org/moid/item_id.html#22664
		ItemID.HOLY_SCYTHE_OF_VITUR,
		ItemID.HOLY_SCYTHE_OF_VITUR_UNCHARGED,
		ItemID.SANGUINE_SCYTHE_OF_VITUR,
		ItemID.SANGUINE_SCYTHE_OF_VITUR_UNCHARGED
	);

	private final WeaponComputable weaponComputable;
	private final BaseHitDptComputable baseHitDptComputable;
	private final HitChanceComputable hitChanceComputable;
	private final BaseMaxHitComputable baseMaxHitComputable;
	private final MaxHitLimitComputable maxHitLimitComputable;
	private final AttackSpeedComputable attackSpeedComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee() &&
			SCYTHE_IDS.contains(context.get(weaponComputable).getItemId());
	}

	@Override
	public Double compute(ComputeContext context)
	{
		double baseDps = context.get(baseHitDptComputable);
		int npcSize = context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getSize();
		if (npcSize < 2)
		{
			context.warn("Using the Scythe of vitur against size 1 targets is inefficient unless hitting multiple enemies.");
			return baseDps;
		}

		double hitChance = context.get(hitChanceComputable);
		int attackSpeed = context.get(attackSpeedComputable);

		int maxHit = context.get(baseMaxHitComputable);
		int maxHitUnlimited = context.get(BaseMaxHitComputable.PRE_LIMIT_MAX_HIT);

		int maxHit2 = maxHitLimitComputable.coerce(maxHitUnlimited / 2, context);
		context.put(SCY_MAX_HIT_2, maxHit2);
		double secondHitDps = BaseHitDptComputable.byComponents(hitChance, maxHit2, attackSpeed);

		double thirdHitDps = 0;
		int maxHit3 = 0;
		if (npcSize > 2)
		{
			maxHit3 = maxHitLimitComputable.coerce(maxHitUnlimited / 4, context);
			context.put(SCY_MAX_HIT_3, maxHit3);
			thirdHitDps = BaseHitDptComputable.byComponents(hitChance, maxHit3, attackSpeed);
		}

		context.put(SCY_MAX_HIT_SUM, maxHit + maxHit2 + maxHit3);
		return baseDps + secondHitDps + thirdHitDps;
	}

}
