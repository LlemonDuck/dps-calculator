package com.duckblade.osrs.dpscalc.calc;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Wither;

@Getter
@Setter(AccessLevel.PACKAGE)
@Builder(toBuilder = true)
@Wither
public class CalcResult
{
	
	private final int attackRoll;
	private final int defenseRoll;
	
	private final float hitRate;
	private final float hitChance;
	private final int maxHit;
	
	private final float dps;
	
	private final int prayerSeconds;
	private final int avgTtk;
	
}
