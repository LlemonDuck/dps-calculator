package com.duckblade.osrs.dpscalc.calc3.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ScenarioInputs
{

	public static final ScenarioInputs EMPTY = ScenarioInputs.builder().build();

	@Builder.Default
	private final boolean onSlayerTask = false;

	@Builder.Default
	private final boolean usingChargeSpell = false;

	@Builder.Default
	private final boolean usingMarkOfDarkness = false;

	@Builder.Default
	private final boolean inWilderness = false;

	@Builder.Default
	private final int tobPartySize = 0;

	@Builder.Default
	private final int coxPartySize = 0;

	@Builder.Default
	private final int coxScale = 0;

	@Builder.Default
	private final int toaPartySize = -1;

	@Builder.Default
	private final int toaScale = -1;

	@Builder.Default
	private final boolean isSpecialAttack = false;

}
