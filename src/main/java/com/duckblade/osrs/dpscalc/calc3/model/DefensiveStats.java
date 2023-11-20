package com.duckblade.osrs.dpscalc.calc3.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class DefensiveStats
{

	public static final DefensiveStats EMPTY = DefensiveStats.builder().build();

	private final int levelDefence;
	private final int levelMagic;

	private final int defenceStab;
	private final int defenceSlash;
	private final int defenceCrush;
	private final int defenceRanged;
	private final int defenceMagic;

	private final int accuracyMagic;

}
