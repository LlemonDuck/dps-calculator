package com.duckblade.osrs.dpscalc.calc.model;

import java.util.Objects;
import java.util.Set;

public enum MonsterAttribute
{

	DEMON,
	DRAGON,
	FIERY,
	GOLEM,
	KALPHITE,
	LEAFY,
	PENANCE,
	RAT,
	SHADE,
	SPECTRAL,
	UNDEAD,
	VAMPYRE_1,
	VAMPYRE_2,
	VAMPYRE_3,
	XERICIAN,
	;

	public static boolean isVampyre(Set<MonsterAttribute> mattrs)
	{
		return mattrs.stream()
			.filter(Objects::nonNull)
			.anyMatch(MonsterAttribute::isVampyre);
	}

	public boolean isVampyre()
	{
		switch (this)
		{
			case VAMPYRE_1:
			case VAMPYRE_2:
			case VAMPYRE_3:
				return true;

			default:
				return false;
		}
	}

}
