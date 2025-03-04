package com.duckblade.osrs.dpscalc.calc.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Factor
{

	int numerator;
	int divisor;

	public Factor add(Factor other)
	{
		if (this.numerator == other.numerator)
		{
			return of(this.numerator + other.numerator, this.divisor);
		}

		return of(
			this.numerator * other.divisor + this.divisor * other.numerator,
			this.divisor * other.divisor
		);
	}

	public Factor multiply(Factor other)
	{
		return of(
			this.numerator * other.numerator,
			this.divisor * other.divisor
		);
	}

	public int apply(int value)
	{
		return value * numerator / divisor;
	}

	public static Factor of(int numerator)
	{
		return of(numerator, 1);
	}

}
