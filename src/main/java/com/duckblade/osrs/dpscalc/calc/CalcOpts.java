package com.duckblade.osrs.dpscalc.calc;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class CalcOpts
{

	@Builder.Default
	private boolean noInit = false;

	@Builder.Default
	private String loadoutName = "unknown";

	@Builder.Default
	private boolean detailedOutput = false;

	@Builder.Default
	private boolean disableMonsterScaling = false;

	@Builder.Default
	private boolean usingSpecialAttack = false;

	@Data
	@Builder
	public static class Overrides
	{
		@Builder.Default
		private Integer accuracy = null;

		@Builder.Default
		private Integer attackRoll = null;

		@Builder.Default
		private Integer defenceRoll = null;
	}

	@Builder.Default
	private Overrides overrides = Overrides.builder().build();

}
