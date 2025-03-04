package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class MonsterInputs
{

	@Builder.Default
	boolean isFromCoxCm = false;

	@Builder.Default
	int toaInvocationLevel = 0;

	@Builder.Default
	int toaPathLevel = 0;

	@Builder.Default
	int partyMaxCombatLevel = 126;

	@Builder.Default
	int partyAvgMiningLevel = 99;

	@Builder.Default
	int partyMaxHpLevel = 99;

	@Builder.Default
	int partySize = 1;

	@Builder.Default
	int currentHp = -1;

	@Builder.Default
	String phase = null;

	@Builder.Default
	DefenceReductions defenceReductions = DefenceReductions.builder().build();

}
