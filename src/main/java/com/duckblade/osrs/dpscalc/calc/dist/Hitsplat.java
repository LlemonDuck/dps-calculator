package com.duckblade.osrs.dpscalc.calc.dist;

import lombok.Value;

@Value
public class Hitsplat
{

	public static Hitsplat INACCURATE = new Hitsplat(0, false);

	private final int damage;
	private final boolean accurate;

	public Hitsplat(int damage, boolean accurate)
	{
		this.damage = damage;
		this.accurate = accurate;
	}

	public Hitsplat(int damage)
	{
		this(damage, true);
	}

	public HitDistribution transform(HitTransformer t, TransformOpts opts)
	{
		if (!this.accurate && !opts.isTransformInaccurate())
		{
			return new HitDistribution(new WeightedHit(1.0, this));
		}

		return t.transform(this);
	}

	public Hitsplat scaleDamage(int factor, int divisor)
	{
		return new Hitsplat(
			damage * factor / divisor,
			accurate
		);
	}
}
