package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class DefenceReductions
{

	@Builder.Default
	boolean vulnerability = false;

	@Builder.Default
	boolean accursed = false;

	@Builder.Default
	int elderMaul = 0;

	@Builder.Default
	int dwh = 0;

	@Builder.Default
	int arclight = 0;

	@Builder.Default
	int emberlight = 0;

	@Builder.Default
	int bgs = 0;

	@Builder.Default
	int tonalztic = 0;

}
