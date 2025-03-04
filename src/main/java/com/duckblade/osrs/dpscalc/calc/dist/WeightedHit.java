package com.duckblade.osrs.dpscalc.calc.dist;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@AllArgsConstructor
public class WeightedHit
{

	private final double probability;
	private final List<Hitsplat> hitsplats;

	public WeightedHit(double probability, Hitsplat... hitsplats)
	{
		this.probability = probability;
		this.hitsplats = Arrays.asList(hitsplats);
	}

	public WeightedHit scale(double factor)
	{
		return new WeightedHit(
			this.probability * factor,
			this.hitsplats
		);
	}

	public WeightedHit scaleDamage(int factor, int divisor)
	{
		return new WeightedHit(
			this.probability,
			this.hitsplats.stream()
				.map(s -> s.scaleDamage(factor, divisor))
				.collect(Collectors.toList())
		);
	}

	public WeightedHit zip(WeightedHit other)
	{
		return new WeightedHit(
			this.probability * other.probability,
			ImmutableList.<Hitsplat>builder()
				.addAll(this.hitsplats)
				.addAll(other.hitsplats)
				.build()
		);
	}

	public WeightedHit[] shift()
	{
		return new WeightedHit[]{
			new WeightedHit(this.probability, this.hitsplats.get(0)),
			new WeightedHit(1.0, this.hitsplats.subList(1, this.hitsplats.size()))
		};
	}

	public HitDistribution transform(HitTransformer t, TransformOpts opts)
	{
		if (this.hitsplats.size() == 1)
		{
			return this.hitsplats.get(0).transform(t, opts)
				.scaleProbability(this.probability);
		}

		WeightedHit[] pair = this.shift();
		WeightedHit head = pair[0];
		WeightedHit tail = pair[1];

		return head.transform(t, opts)
			.zip(tail.transform(t, opts));
	}

	@Getter(lazy = true)
	private final boolean anyAccurate = this.hitsplats.stream().anyMatch(Hitsplat::isAccurate);

	@Getter(lazy = true)
	private final int sum = this.hitsplats.stream().mapToInt(Hitsplat::getDamage).sum();

	public double getExpectedValue()
	{
		return this.probability * this.getSum();
	}

	public int getHash()
	{
		int acc = 0;
		for (Hitsplat h : this.hitsplats)
		{
			acc <<= 8;
			acc |= h.getDamage();
			acc <<= 1;
			acc |= h.isAccurate() ? 1 : 0;
		}

		return acc;
	}
}
