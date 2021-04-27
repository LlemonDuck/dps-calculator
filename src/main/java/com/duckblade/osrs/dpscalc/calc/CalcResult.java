package com.duckblade.osrs.dpscalc.calc;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalcResult
{
	
	private final int attackRoll;
	private final int defenseRoll;
	
	private final float hitRate;
	private final float hitChance;
	private final int maxHit;
	
	private final float dps;
	
	private final int prayerSeconds;
	
}
