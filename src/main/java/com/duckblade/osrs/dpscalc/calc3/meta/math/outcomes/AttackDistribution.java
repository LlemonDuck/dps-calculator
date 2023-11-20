package com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes;

import com.duckblade.osrs.dpscalc.calc3.meta.math.Operation;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttackDistribution implements Iterable<HitDistribution>
{

	private final List<HitDistribution> hitDists;

	public AttackDistribution(List<HitDistribution> hitDists)
	{
		this.hitDists = hitDists;
	}

	public void addDist(HitDistribution d)
	{
		hitDists.add(d);
	}

	public double expectedDamage()
	{
		return hitDists.stream()
			.mapToDouble(HitDistribution::expectedHit)
			.sum();
	}

	public AttackDistribution transform(HitTransformer t)
	{
		return map(hd -> hd.transform(t));
	}

	public AttackDistribution flattenAll()
	{
		return map(HitDistribution::flattenDuplicates);
	}

	public AttackDistribution scaleProbability(double factor)
	{
		return map(hd -> hd.scaleProbability(factor));
	}

	public AttackDistribution scaleDamage(Operation op)
	{
		return map(hd -> hd.scaleDamage(op));
	}

	public int getMax()
	{
		return hitDists.stream()
			.mapToInt(HitDistribution::getMax)
			.max()
			.orElse(0);
	}

	@Override
	public Iterator<HitDistribution> iterator()
	{
		return hitDists.iterator();
	}

	private AttackDistribution map(Function<HitDistribution, HitDistribution> mapper)
	{
		return new AttackDistribution(
			this.hitDists.stream()
				.map(mapper)
				.collect(Collectors.toList())
		);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("AttackDistribution(hitDists=");
		sb.append(hitDists.stream()
			.map(HitDistribution::toString)
			.collect(Collectors.joining(",")));
		sb.append(")");
		return sb.toString();
	}

}
