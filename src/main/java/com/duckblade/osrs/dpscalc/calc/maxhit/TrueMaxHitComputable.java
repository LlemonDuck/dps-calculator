package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Computes the combined max hit, 
 * accounting for special effects like multi-hits,
 * or special effects like the Keris.
 * 
 * This class is not called anywhere naturally within the calc module,
 * and will not be accurate unless run after the effect calculations,
 * which are only sourced during DptComputable.
 * It exists to be called after dps calculations, 
 * to simplify grabbing the true max hit that would otherwise be 
 * distributed across multiple possible output sources.
 */
@Singleton
public class TrueMaxHitComputable implements Computable<Integer>
{

	private final BaseMaxHitComputable baseMaxHitComputable;

	private final List<ComputeOutput<Integer>> effectMaxHitOutputs;

	@Inject
	public TrueMaxHitComputable(BaseMaxHitComputable baseMaxHitComputable, @Named("EffectMaxHitOutputs") List<ComputeOutput<Integer>> effectMaxHitOutputs)
	{
		this.baseMaxHitComputable = baseMaxHitComputable;
		this.effectMaxHitOutputs = effectMaxHitOutputs;
	}

	@Override
	public Integer compute(ComputeContext context)
	{
		return effectMaxHitOutputs.stream()
			.map(context::get)
			.filter(Objects::nonNull)
			.findFirst()
			.orElseGet(() -> context.get(baseMaxHitComputable));
	}
}
