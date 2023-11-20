package com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes;

import com.duckblade.osrs.dpscalc.calc3.meta.math.Operation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Value;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@Value
public class WeightedHit
{

	private final double probability;
	private final List<Integer> hits;

	public WeightedHit(double probability, List<Integer> hits)
	{
		this.probability = probability;
		this.hits = hits;
	}

	public int dimension()
	{
		return this.hits.size();
	}

	public WeightedHit scaleProbability(double probabilityFactor)
	{
		return new WeightedHit(
			this.probability * probabilityFactor,
			this.hits
		);
	}

	public WeightedHit scaleDamage(Operation op)
	{
		return new WeightedHit(
			this.probability,
			this.hits.stream()
				.map(op::apply)
				.collect(Collectors.toList())
		);
	}

	public WeightedHit zip(WeightedHit other)
	{
		List<Integer> newHits = new ArrayList<>(this.hits);
		newHits.addAll(other.hits);

		return new WeightedHit(
			this.probability * other.probability,
			newHits
		);
	}

	Pair<WeightedHit, WeightedHit> shift()
	{
		assert this.hits.size() > 1;
		return new ImmutablePair<>(
			new WeightedHit(
				this.probability,
				Collections.singletonList(this.hits.get(0))
			),
			new WeightedHit(
				1.0,
				this.hits.subList(1, this.hits.size())
			)
		);
	}

	HitDistribution transform(HitTransformer t)
	{
		if (dimension() == 1)
		{
			return t.transform(this.hits.get(0))
				.scaleProbability(this.probability);
		}

		// recursive subproblem
		Pair<WeightedHit, WeightedHit> shifted = shift();
		return shifted.getLeft().transform(t)
			.zip(shifted.getRight().transform(t));
	}

	public int getSum()
	{
		int sum = 0;
		for (Integer hit : hits)
		{
			sum += hit;
		}
		return sum;
	}

	public double getExpectedValue()
	{
		return this.probability * this.getSum();
	}

}
