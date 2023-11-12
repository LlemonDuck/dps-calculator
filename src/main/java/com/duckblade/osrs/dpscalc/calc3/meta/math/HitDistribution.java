package com.duckblade.osrs.dpscalc.calc3.meta.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HitDistribution implements Iterable<AttackOutcome>
{

	List<AttackOutcome> outcomes = new ArrayList<>();

	public void addOutcome(AttackOutcome o)
	{
		this.outcomes.add(o);
	}

	public HitDistribution zip(HitDistribution other)
	{
		HitDistribution d = new HitDistribution();
		for (AttackOutcome o : this)
		{
			for (AttackOutcome p : other)
			{
				d.addOutcome(o.zip(p));
			}
		}

		return d;
	}

	public HitDistribution transform(HitTransformer t)
	{
		HitDistribution d = new HitDistribution();
		for (AttackOutcome o : this)
		{
			for (AttackOutcome p : o.transform(t))
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
		for (AttackOutcome o : this)
		{
			accumulator.compute(o.getHits(), (_k, v) -> (v == null ? 0.0 : v) + o.getProbability());
		}

		HitDistribution flattened = new HitDistribution();
		accumulator.entrySet().stream()
			.map(e -> new AttackOutcome(e.getValue(), e.getKey()))
			.forEach(flattened::addOutcome);
		System.out.println("End size " + flattened.outcomeCount());
		return flattened;
	}

	public static HitDistribution linear(double accuracy, int minInclusive, int maxInclusive)
	{
		HitDistribution d = new HitDistribution();
		double constProb = 1.0 / (maxInclusive - minInclusive + 1);
		for (int hit = minInclusive; hit <= maxInclusive; hit++)
		{
			d.addOutcome(new AttackOutcome(
				constProb,
				Collections.singletonList(hit)
			));
		}
		System.out.println(d);
		d = d.scale(accuracy);
		d.addOutcome(new AttackOutcome(1.0 - accuracy, Collections.singletonList(0)));

		return d;
	}

	public static HitDistribution linear(double accuracy, int maxInclusive)
	{
		return linear(accuracy, 0, maxInclusive);
	}

	public static HitDistribution single(double accuracy, int hit)
	{
		HitDistribution d = new HitDistribution();
		d.addOutcome(new AttackOutcome(accuracy, Collections.singletonList(hit)));
		if (accuracy != 1.0)
		{
			d.addOutcome(new AttackOutcome(1 - accuracy, Collections.singletonList(0)));
		}
		return d;
	}

	@Override
	public Iterator<AttackOutcome> iterator()
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
			.mapToDouble(AttackOutcome::getExpectedValue)
			.sum();
	}

	public HitDistribution scale(double probabilityFactor)
	{
		HitDistribution d = new HitDistribution();
		for (AttackOutcome o : this)
		{
			d.addOutcome(o.scale(probabilityFactor));
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
			.mapToDouble(AttackOutcome::getProbability)
			.sum();
	}

}
