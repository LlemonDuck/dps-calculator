package com.duckblade.osrs.dpscalc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CombatFocus
{

	ACCURATE("Accurate"),
	AGGRESSIVE("Aggressive"),
	AUTOCAST("Autocast"),
	CONTROLLED("Controlled"),
	DEFENSIVE("Defensive"),
	LONGRANGE("Longrange"),
	RAPID("Rapid"),
	;

	@Getter
	private final String displayName;

}
