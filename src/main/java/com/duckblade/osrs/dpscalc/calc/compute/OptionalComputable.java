package com.duckblade.osrs.dpscalc.calc.compute;

/**
 * Allows for a pre-computation check on whether the node should be computed by its parent,
 * without fully computing the value and storing the result in the context.
 * Useful to prune single-parent nodes from the result set, slimming down unnecessary final values.
 */
public interface OptionalComputable<T> extends Computable<T>
{

	boolean isApplicable(ComputeContext context);

}
