package com.duckblade.osrs.dpscalc.calc3.meta.math;

import java.math.BigInteger;
import lombok.RequiredArgsConstructor;

/**
 * This class represents a multiplication by rationals to be applied to a DPS calculation.
 * OSRS works exclusively with ints during computation, so floats or doubles cannot be used.
 */
@RequiredArgsConstructor
public class Multiplication implements Operation
{

	private final int numerator;
	private final int denominator;

	public Multiplication(int factor)
	{
		this(factor, 1);
	}

	@Override
	public int apply(int previous)
	{
		return previous * numerator / denominator;
	}

	public static Multiplication ofPercent(int percent)
	{
		int gcd = BigInteger.valueOf(100 + percent).gcd(BigInteger.valueOf(100)).intValue();
		return new Multiplication((100 + percent) / gcd, 100 / gcd);
	}

	@Override
	public String toString()
	{
		return "*" + numerator + "/" + denominator;
	}

}
