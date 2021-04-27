package com.duckblade.osrs.dpscalc.model;

import lombok.Data;
import lombok.Getter;

@Data
public class WeaponMode
{
	
	@Getter
	private final String displayName;
	
	@Getter
	private final CombatMode mode;
	
	@Getter
	private final CombatFocus combatFocus;
	
	@Getter
	private final MeleeStyle meleeStyle;
	
}
