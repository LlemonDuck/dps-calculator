package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CombatStyle
{

	ACCURATE("Accurate"),
	AGGRESSIVE("Aggressive"),
	AUTOCAST("Autocast"),
	CONTROLLED("Controlled"),
	DEFENSIVE("Defensive"),
	LONGRANGE("Longrange"),
	RAPID("Rapid"),
	;

	private final String displayName;

}
