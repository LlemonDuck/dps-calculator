package com.duckblade.osrs.dpscalc.plugin.live;

import com.duckblade.osrs.dpscalc.calc.DpsResultCache;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import lombok.Value;

@Value
public class TargetedDpsChanged
{

	private final TargetedDps targetedDps;
	private final ComputeInput input;
	private final DpsResultCache dpsResultCache;

}
