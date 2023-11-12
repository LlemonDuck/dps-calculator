package com.duckblade.osrs.dpscalc.calc3.meta.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Value;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@Value
public class AttackOutcome
{

	private final double probability;
	private final List<Integer> hits;

	public AttackOutcome(double probability, List<Integer> hits)
	{
		this.probability = probability;
		this.hits = hits;
	}

	public int dimension()
	{
		return this.hits.size();
	}

	public AttackOutcome scale(double probabilityFactor)
	{
		return new AttackOutcome(
			this.probability * probabilityFactor,
			this.hits
		);
	}

	public AttackOutcome zip(AttackOutcome other)
	{
		List<Integer> newHits = new ArrayList<>(this.hits);
		newHits.addAll(other.hits);

		return new AttackOutcome(
			this.probability * other.probability,
			newHits
		);
	}

	Pair<AttackOutcome, AttackOutcome> shift()
	{
		assert this.hits.size() > 1;
		return new ImmutablePair<>(
			new AttackOutcome(
				this.probability,
				Collections.singletonList(this.hits.get(0))
			),
			new AttackOutcome(
				1.0,
				this.hits.subList(1, this.hits.size())
			)
		);
	}

	HitDistribution transform(HitTransformer t)
	{
		if (dimension() == 1)
		{
			HitDistribution d = new HitDistribution();
			for (AttackOutcome o : t.transform(this.hits.get(0)))
			{
				d.addOutcome(o.scale(this.probability));
			}
			return d;
		}

		// recursive subproblem
		Pair<AttackOutcome, AttackOutcome> shifted = shift();
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
