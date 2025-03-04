package com.duckblade.osrs.dpscalc.calc.dist;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AttackDistribution
{

	@Singular
	private final List<HitDistribution> dists;

	public AttackDistribution(HitDistribution... dists)
	{
		this.dists = Arrays.asList(dists);
	}

	@Getter(lazy = true)
	private final HitDistribution zipped = dists.stream()
		.reduce(HitDistribution::zip)
		.get();

	@Getter(lazy = true)
	private final HitDistribution singleHitsplat = getZipped().cumulative();

	public AttackDistribution transform(HitTransformer t, TransformOpts opts)
	{
		return map(d -> d.transform(t, opts));
	}

	public AttackDistribution transform(HitTransformer t)
	{
		return transform(t, TransformOpts.builder().build());
	}

	public AttackDistribution flatten()
	{
		return map(HitDistribution::flatten);
	}

	public AttackDistribution scaleProbability(double factor)
	{
		return map(d -> d.scaleProbability(factor));
	}

	public AttackDistribution scaleDamage(int factor, int divisor)
	{
		return map(d -> d.scaleDamage(factor, divisor));
	}

	public int getMin()
	{
		return dists.stream()
			.mapToInt(HitDistribution::getMin)
			.sum();
	}

	public int getMax()
	{
		return dists.stream()
			.mapToInt(HitDistribution::getMax)
			.sum();
	}

	public double getExpectedDamage()
	{
		return dists.stream()
			.mapToDouble(HitDistribution::getExpectedHit)
			.sum();
	}

	private AttackDistribution map(Function<HitDistribution, HitDistribution> mapper)
	{
		return new AttackDistribution(
			dists.stream()
				.map(mapper)
				.collect(Collectors.toList())
		);
	}

}
