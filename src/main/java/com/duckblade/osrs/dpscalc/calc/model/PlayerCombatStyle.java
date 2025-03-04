package com.duckblade.osrs.dpscalc.calc.model;

import com.google.gson.annotations.JsonAdapter;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder(toBuilder = true)
@With
public class PlayerCombatStyle
{

	public static final PlayerCombatStyle MANUAL_CAST = PlayerCombatStyle.builder()
		.name("Manual Cast")
		.type(CombatStyleType.MAGIC)
		.stance(CombatStyleStance.MANUAL_CAST)
		.build();

	@Builder.Default
	int varp = -1;

	String name;
	CombatStyleType type;
	CombatStyleStance stance;

}
