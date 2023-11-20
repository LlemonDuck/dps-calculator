package com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes;

import com.duckblade.osrs.dpscalc.calc3.meta.math.Operation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

public class HitDistribution implements Iterable<WeightedHit>
{

	List<WeightedHit> outcomes = new ArrayList<>();

	public void addOutcome(WeightedHit o)
	{
		this.outcomes.add(o);
	}

	public HitDistribution zip(HitDistribution other)
	{
		HitDistribution d = new HitDistribution();
		for (WeightedHit o : this)
		{
			for (WeightedHit p : other)
			{
				d.addOutcome(o.zip(p));
			}
		}

		return d;
	}

	public HitDistribution transform(HitTransformer t)
	{
		HitDistribution d = new HitDistribution();
		for (WeightedHit o : this)
		{
			for (WeightedHit p : o.transform(t))
			{
				d.addOutcome(p);
			}
		}

		return d;
	}

	public HitDistribution flattenDuplicates()
	{
		System.out.println("Start size " + outcomeCount());
		Map<List<Integer>, Double> accumulator = new HashMap<>();
		for (WeightedHit o : this)
		{
			accumulator.compute(o.getHits(), (_k, v) -> (v == null ? 0.0 : v) + o.getProbability());
		}

		HitDistribution flattened = new HitDistribution();
		accumulator.entrySet().stream()
			.map(e -> new WeightedHit(e.getValue(), e.getKey()))
			.forEach(flattened::addOutcome);
		System.out.println("End size " + flattened.outcomeCount());
		return flattened;
	}

	public static HitDistribution linear(double accuracy, int minInclusive, int maxInclusive)
	{
		HitDistribution d = new HitDistribution();
		double constProb = accuracy / (maxInclusive - minInclusive + 1);
		for (int hit = minInclusive; hit <= maxInclusive; hit++)
		{
			d.addOutcome(new WeightedHit(
				constProb,
				Collections.singletonList(hit)
			));
		}
		d.addOutcome(new WeightedHit(1.0 - accuracy, Collections.singletonList(0)));

		return d;
	}

	public static HitDistribution linear(double accuracy, int maxInclusive)
	{
		return linear(accuracy, 0, maxInclusive);
	}

	public static HitDistribution single(double accuracy, int hit)
	{
		HitDistribution d = new HitDistribution();
		d.addOutcome(new WeightedHit(accuracy, Collections.singletonList(hit)));
		if (accuracy != 1.0)
		{
			d.addOutcome(new WeightedHit(1 - accuracy, Collections.singletonList(0)));
		}
		return d;
	}

	@Nonnull
	@Override
	public Iterator<WeightedHit> iterator()
	{
		return outcomes.iterator();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Dist(expected=");
		sb.append(expectedHit());
		sb.append(", sumProb=");
		sb.append(sumProbabilities());
		sb.append(", outcomes=");
		sb.append(outcomeCount());
		sb.append(')');
		return sb.toString();
	}

	public double expectedHit()
	{
		return outcomes.stream()
			.mapToDouble(WeightedHit::getExpectedValue)
			.sum();
	}

	public HitDistribution scaleProbability(double probabilityFactor)
	{
		HitDistribution d = new HitDistribution();
		for (WeightedHit o : this)
		{
			d.addOutcome(o.scaleProbability(probabilityFactor));
		}

		return d;
	}

	public HitDistribution scaleDamage(Operation op)
	{
		HitDistribution d = new HitDistribution();
		for (WeightedHit o : this)
		{
			d.addOutcome(o.scaleDamage(op));
		}

		return d;
	}

	public int outcomeCount()
	{
		return outcomes.size();
	}

	public double sumProbabilities()
	{
		return outcomes.stream()
			.mapToDouble(WeightedHit::getProbability)
			.sum();
	}

	public int getMax()
	{
		return outcomes.stream()
			.mapToInt(WeightedHit::getSum)
			.max()
			.orElse(0);
	}

}
