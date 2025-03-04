package com.duckblade.osrs.dpscalc.calc.dist;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransformOpts
{

	@Builder.Default
	private boolean transformInaccurate = true;

}
