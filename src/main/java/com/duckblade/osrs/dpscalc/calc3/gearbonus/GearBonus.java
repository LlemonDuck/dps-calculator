package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Operation;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

/**
 * This class represents a GearBonus as is applied by a weapon or other worn equipment.
 * <p> 
 * It stores this bonus as a pair of multiplications, one for accuracy and one for strength,
 * since many gear bonuses grant both. This way, the complex applicability checks only need 
 * to be checked once.
 * <p> 
 * For information on why Multiplication requires two integers, see {@link Multiplication}.
 */
@RequiredArgsConstructor
public class GearBonus
{

	private final Operation accuracyBonus;
	private final Operation strengthBonus;

	public int applyAccuracy(int previous)
	{
		return accuracyBonus != null ? accuracyBonus.apply(previous) : previous;
	}

	public int applyStrength(int previous)
	{
		return strengthBonus != null ? strengthBonus.apply(previous) : previous;
	}

	public static GearBonus symmetric(Operation op)
	{
		return new GearBonus(op, op);
	}

	@Override
	public String toString()
	{
		return "GB(acc=" + accuracyBonus + ",str=" + strengthBonus + ")";
	}
}
