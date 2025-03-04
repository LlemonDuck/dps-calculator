package com.duckblade.osrs.dpscalc.calc.dist;

import java.util.List;

@FunctionalInterface
public interface WeaponDelayProvider
{

	List<ProbabilisticDelay> provideDelays(WeightedHit wh);

}
