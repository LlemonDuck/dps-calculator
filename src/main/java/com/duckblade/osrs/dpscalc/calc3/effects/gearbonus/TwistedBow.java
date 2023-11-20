package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.core.DefenderStats;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.model.GearBonuses;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TwistedBow implements GearBonusOperation
{

	private static final Set<Integer> TBOW_IDS = ImmutableSet.of(
		ItemID.TWISTED_BOW
	);

	private static final int LOW_MAGIC_WARN_THRESHOLD = 100;

	private final Weapon weapon;
	private final DefenderStats defenderStats;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return TBOW_IDS.contains(ctx.get(weapon).getItemId());
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		int scalingLimit = ctx.get(ComputeInputs.SCENARIO).getCoxPartySize() > 0 ? 350 : 250;

		int defenderMagicLevel = ctx.get(defenderStats).getLevelMagic();
		int defenderMagicAccuracy = ctx.get(defenderStats).getAccuracyMagic();
		int scalingComponent = Math.max(defenderMagicLevel, defenderMagicAccuracy);

		int magic = Math.min(scalingLimit, scalingComponent);
		if (magic < LOW_MAGIC_WARN_THRESHOLD)
		{
			ctx.warn("Using the twisted bow against low-magic targets incurs negative bonuses.");
		}

		return new GearBonus(
			Multiplication.ofPercent((int) Math.floor(100 * tbowFormula(magic, true)) / 100),
			Multiplication.ofPercent((int) Math.floor(100 * tbowFormula(magic, false)) / 100)
		);
	}

	// formulas are the same shape with different static values
	@VisibleForTesting
	static double tbowFormula(int magic, boolean accuracy)
	{
		double base = accuracy ? 140.0 : 250.0;
		double sub = accuracy ? 10.0 : 14.0;

		double t2 = (3.0 * magic - sub) / 100.0;
		double t3 = Math.pow((3.0 * magic) / 10.0 - (10.0 * sub), 2.0) / 100.0;
		return (base + t2 - t3) / 100.0;
	}

}
