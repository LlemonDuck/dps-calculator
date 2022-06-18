package com.duckblade.osrs.dpscalc.plugin.live;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import lombok.Value;

@Value
public class TargetedDpsChanged
{

	private final TargetedDps targetedDps;
	private final ComputeInput input;
	private final ComputeContext context;

}
