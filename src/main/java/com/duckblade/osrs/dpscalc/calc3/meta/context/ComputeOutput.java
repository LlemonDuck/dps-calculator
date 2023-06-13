package com.duckblade.osrs.dpscalc.calc3.meta.context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class ComputeOutput<T> implements ContextValue<T>
{

	public static final ComputeOutput<Integer> BASE_ATTACK_ROLL = ComputeOutput.of("BaseAttackRoll");
	public static final ComputeOutput<Integer> BASE_DEFENSE_ROLL = ComputeOutput.of("BaseDefenseRoll");
	public static final ComputeOutput<Double> BASE_ACCURACY = ComputeOutput.of("BaseAccuracy");
	public static final ComputeOutput<Integer> BASE_MAX_HIT = ComputeOutput.of("BaseMaxHit");

	private final String key;

	@Override
	public String key()
	{
		return this.key;
	}

	@Override
	public T compute(ComputeContext context)
	{
		// outputs are placed manually into the context by other computations
		// missing outputs may be requested, we just always return a non-result
		return null;
	}

}
