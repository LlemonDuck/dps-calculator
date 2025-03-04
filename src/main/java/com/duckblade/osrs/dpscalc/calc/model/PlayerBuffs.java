package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class PlayerBuffs
{

	@Builder.Default
	boolean onSlayerTask = true;

	@Builder.Default
	boolean inWilderness = true;

	@Builder.Default
	boolean forinthrySurge = true;

	@Builder.Default
	int soulreaperStacks = 6;

	@Builder.Default
	int baAttackerLevel = 5;

	@Builder.Default
	int chinchompaDistance = 4;

	@Builder.Default
	boolean kandarinDiary = true;

	@Builder.Default
	boolean chargeSpell = false;

	@Builder.Default
	boolean markOfDarknessSpell = false;

	@Builder.Default
	boolean usingSunfireRunes = false;

}
