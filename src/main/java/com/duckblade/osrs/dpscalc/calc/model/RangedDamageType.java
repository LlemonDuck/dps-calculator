package com.duckblade.osrs.dpscalc.calc.model;

public enum RangedDamageType
{

	LIGHT,
	STANDARD,
	HEAVY,
	MIXED,
	;

	public static RangedDamageType getRangedDamageType(EquipmentCategory category)
	{
		switch (category)
		{
			case THROWN:
				return LIGHT;

			case BOW:
				return STANDARD;

			case CROSSBOW:
			case CHINCHOMPA:
				return HEAVY;

			case SALAMANDER:
				return MIXED;

			default:
				throw new IllegalArgumentException("Not a ranged weapon category: " + category);
		}
	}
}
