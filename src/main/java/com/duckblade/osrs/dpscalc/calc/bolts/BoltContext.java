package com.duckblade.osrs.dpscalc.calc.bolts;

import com.duckblade.osrs.dpscalc.calc.model.Monster;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BoltContext
{

	private final int maxHit;
	private final int rangedLvl;
	private final boolean zcb;
	private final boolean spec;
	private final boolean kandarinDiary;
	private final Monster monster;

}
