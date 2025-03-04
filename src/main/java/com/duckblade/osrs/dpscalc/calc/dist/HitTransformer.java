package com.duckblade.osrs.dpscalc.calc.dist;

@FunctionalInterface
public interface HitTransformer
{

	HitDistribution transform(Hitsplat hitsplat);

	static HitTransformer flatLimitTransformer(int maximum, int minimum)
	{
		return h -> HitDistribution.single(
			1.0, new Hitsplat(Math.max(minimum, Math.min(h.getDamage(), maximum)), h.isAccurate())
		);
	}

	static HitTransformer flatLimitTransformer(int maximum)
	{
		return flatLimitTransformer(maximum, 0);
	}

	static HitTransformer linearMinTransformer(int maximum, int offset)
	{
		return h ->
		{
			HitDistribution.HitDistributionBuilder builder = HitDistribution.builder();
			double prob = 1.0 / (maximum + 1);
			for (int i = 0; i <= maximum; i++)
			{
				builder.hit(new WeightedHit(
					prob,
					new Hitsplat(Math.min(h.getDamage(), i + offset), h.isAccurate())
				));
			}

			return builder.build().flatten();
		};
	}

	static HitTransformer linearMinTransformer(int maximum)
	{
		return linearMinTransformer(maximum, 0);
	}

	static HitTransformer cappedRerollTransformer(int limit, int rollMax, int offset)
	{
		return h ->
		{
			if (h.getDamage() <= limit)
			{
				return HitDistribution.single(1.0, h);
			}

			HitDistribution.HitDistributionBuilder builder = HitDistribution.builder();
			double prob = 1.0 / (rollMax + 1);
			for (int i = 0; i <= rollMax; i++)
			{
				builder.hit(new WeightedHit(
					prob,
					new Hitsplat(i + offset, h.isAccurate())
				));
			}

			return builder.build().flatten();
		};
	}

	static HitTransformer cappedRerollTransformer(int limit, int rollMax)
	{
		return cappedRerollTransformer(limit, rollMax, 0);
	}

	static HitTransformer multiplyTransformer(int numerator, int divisor, int minimum)
	{
		return h ->
		{
			int dmg = h.getDamage() * numerator / divisor;
			if (minimum != 0)
			{
				if (h.getDamage() >= minimum)
				{
					dmg = Math.max(minimum, dmg);
				}
				else
				{
					dmg = Math.max(h.getDamage(), dmg);
				}
			}

			return HitDistribution.single(
				1.0, new Hitsplat(dmg, h.isAccurate())
			);
		};
	}

	static HitTransformer multiplyTransformer(int numerator, int divisor)
	{
		return multiplyTransformer(numerator, divisor, 0);
	}

	static HitTransformer multiplyTransformer(int numerator)
	{
		return multiplyTransformer(numerator, 1, 0);
	}

	static HitTransformer divisionTransformer(int divisor, int minimum)
	{
		return multiplyTransformer(1, divisor, minimum);
	}

	static HitTransformer divisionTransformer(int divisor)
	{
		return multiplyTransformer(1, divisor, 0);
	}

	static HitTransformer flatAddTransformer(int addend, int minimum)
	{
		return h -> HitDistribution.single(
			1.0, new Hitsplat(Math.max(minimum, h.getDamage() + addend), h.isAccurate())
		);
	}

	static HitTransformer flatAddTransformer(int addend)
	{
		return flatAddTransformer(addend, 0);
	}

}
