package com.duckblade.osrs.dpscalc.calc3.meta.math;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HitDistribution
{

	private final Map<List<Integer>, Double> hitProbabilities = new HashMap<>();

	public Set<Map.Entry<List<Integer>, Double>> getEntries()
	{
		return hitProbabilities.entrySet();
	}

	public void put(int hit, double probability)
	{
		this.put(Collections.singletonList(hit), probability);
	}

	public void put(List<Integer> hits, double probability)
	{
		hits.sort(Integer::compareTo);
		hitProbabilities.put(
			hits,
			hitProbabilities.getOrDefault(hits, 0.0) + probability
		);
	}

	public double getAverageHit()
	{
		return hitProbabilities.entrySet()
			.stream()
			.mapToDouble(e -> sumIntList(e.getKey()) * e.getValue())
			.sum();
	}

	public int getMaxHit()
	{
		return hitProbabilities.keySet()
			.stream()
			.mapToInt(HitDistribution::sumIntList)
			.max()
			.orElse(0);
	}

	@Override
	public String toString()
	{
		return "HitDist{max=" + getMaxHit() + ",avg=" + getAverageHit() + ",hits=" + hitProbabilities + "}";
	}

	private static int sumIntList(List<Integer> list)
	{
		return list.stream()
			.mapToInt(Integer::intValue)
			.sum();
	}

}
