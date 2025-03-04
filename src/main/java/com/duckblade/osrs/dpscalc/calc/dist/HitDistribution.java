package com.duckblade.osrs.dpscalc.calc.dist;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class HitDistribution
{


	@Singular
	private final List<WeightedHit> hits;

	public HitDistribution(WeightedHit... hits)
	{
		this.hits = Arrays.asList(hits);
	}

	public HitDistribution zip(HitDistribution other)
	{
		HitDistributionBuilder builder = HitDistribution.builder();
		for (WeightedHit hit : hits)
		{
			for (WeightedHit otherHit : other.hits)
			{
				builder.hit(hit.zip(otherHit));
			}
		}

		return builder.build();
	}

	public HitDistribution transform(HitTransformer t, TransformOpts opts)
	{
		HitDistributionBuilder builder = HitDistribution.builder();
		for (WeightedHit h : hits)
		{
			for (WeightedHit transformed : h.transform(t, opts).hits)
			{
				builder.hit(transformed);
			}
		}

		return builder.build()
			.flatten();
	}

	public HitDistribution scaleProbability(double factor)
	{
		return new HitDistribution(
			hits.stream()
				.map(h -> h.scale(factor))
				.collect(Collectors.toList())
		);
	}

	public HitDistribution scaleDamage(int factor, int divisor)
	{
		return new HitDistribution(
			hits.stream()
				.map(h -> h.scaleDamage(factor, divisor))
				.collect(Collectors.toList())
		);
	}

	public HitDistribution scaleDamage(int factor)
	{
		return scaleDamage(factor, 1);
	}

	public HitDistribution flatten()
	{
		Map<Integer, Double> acc = new HashMap<>();
		Map<Integer, List<Hitsplat>> hitLists = new HashMap<>();

		for (WeightedHit hit : hits)
		{
			int hash = hit.getHash();
			acc.compute(hash, (_hash, accuracy) -> accuracy == null ? hit.getProbability() : accuracy + hit.getProbability());
			hitLists.computeIfAbsent(hash, _hash -> hit.getHitsplats());
		}

		HitDistributionBuilder builder = HitDistribution.builder();
		acc.forEach((hash, accuracy) ->
		{
			if (accuracy > 0)
			{
				builder.hit(new WeightedHit(accuracy, hitLists.get(hash)));
			}
		});

		return builder.build();
	}

	public HitDistribution cumulative()
	{
		Map<Integer, Double> acc = new HashMap<>();
		for (WeightedHit hit : hits)
		{
			int k = hit.isAnyAccurate() ? hit.getSum() : ~hit.getSum();
			acc.compute(k, (_k, prob) -> prob == null ? hit.getProbability() : prob + hit.getProbability());
		}

		HitDistributionBuilder builder = HitDistribution.builder();
		acc.forEach((k, prob) ->
		{
			if (prob > 0)
			{
				boolean accurate = k >= 0;
				int dmg = accurate ? k : ~k;
				builder.hit(new WeightedHit(prob, new Hitsplat(dmg, accurate)));
			}
		});

		return builder.build();
	}

	@Getter(lazy = true)
	private final double expectedHit = this.hits.stream()
		.mapToDouble(WeightedHit::getExpectedValue)
		.sum();

	@Getter(lazy = true)
	private final int min = this.hits.stream()
		.mapToInt(WeightedHit::getSum)
		.min()
		.orElse(0);

	@Getter(lazy = true)
	private final int max = this.hits.stream()
		.mapToInt(WeightedHit::getSum)
		.max()
		.orElse(0);

	public static HitDistribution linear(double accuracy, int minimum, int maximum)
	{
		HitDistributionBuilder builder = HitDistribution.builder();

		double hitProb = accuracy / (maximum - minimum + 1);
		for (int i = minimum; i <= maximum; i++)
		{
			builder.hit(new WeightedHit(hitProb, new Hitsplat(i)));
		}
		if (accuracy != 1.0)
		{
			builder.hit(new WeightedHit(1 - accuracy, Hitsplat.INACCURATE));
		}

		return builder.build();
	}

	public static HitDistribution single(double accuracy, Hitsplat... hitsplats)
	{
		if (accuracy == 1.0)
		{
			return new HitDistribution(
				new WeightedHit(1.0, hitsplats)
			);
		}

		return new HitDistribution(
			new WeightedHit(accuracy, hitsplats),
			new WeightedHit(1 - accuracy, Hitsplat.INACCURATE)
		);
	}

}
