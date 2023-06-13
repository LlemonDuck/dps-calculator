package com.duckblade.osrs.dpscalc.calc3.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class GearBonuses
{

	public static final GearBonuses EMPTY = new GearBonuses(1.0, 1.0);

	private final double accuracyBonus;
	private final double strengthBonus;

	public static GearBonuses combine(GearBonuses a, GearBonuses b)
	{
		return new GearBonuses(a.accuracyBonus * b.accuracyBonus, a.strengthBonus * b.strengthBonus);
	}

	public static GearBonuses symmetric(double bonus)
	{
		return GearBonuses.of(bonus, bonus);
	}

}
