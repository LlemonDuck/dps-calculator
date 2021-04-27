package com.duckblade.osrs.dpscalc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MeleeStyle
{

	CRUSH("Crush"),
	SLASH("Slash"),
	STAB("Stab"),
	;
	
	@Getter
	private final String displayName;

}
