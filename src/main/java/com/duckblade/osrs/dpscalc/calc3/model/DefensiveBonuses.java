package com.duckblade.osrs.dpscalc.calc3.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class DefensiveBonuses
{

	public static final DefensiveBonuses EMPTY = DefensiveBonuses.builder().build();

	private final int defenseStab;
	private final int defenseSlash;
	private final int defenseCrush;
	private final int defenseRanged;
	private final int defenseMagic;

}
