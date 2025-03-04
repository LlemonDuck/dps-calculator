package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.dist.AttackDistribution;
import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DpsResultCache
{

	@Getter
	private final DpsCalc dpsCalc;

	@Getter(lazy = true)
	private final MinMax minMax = dpsCalc.getMinAndMax();

	@Getter(lazy = true)
	private final int attackRoll = dpsCalc.getMaxAttackRoll();

	@Getter(lazy = true)
	private final int defenceRoll = dpsCalc.getNPCDefenceRoll();

	@Getter(lazy = true)
	private final double dps = dpsCalc.getDps();

	@Getter(lazy = true)
	private final double hitChance = dpsCalc.getHitChance();

	@Getter(lazy = true)
	private final AttackDistribution distribution = dpsCalc.getDistribution();

	@Getter(lazy = true)
	private final int attackSpeed = dpsCalc.getAttackSpeed();

	public Duration getTtkP50()
	{
		// todo
		return null;
	}

	@Getter(lazy = true)
	private final Duration prayerDuration = dpsCalc.getPrayerDuration();
}

