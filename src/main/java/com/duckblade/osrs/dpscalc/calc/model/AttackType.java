package com.duckblade.osrs.dpscalc.calc.model;

public enum AttackType
{

	STAB,
	SLASH,
	CRUSH,
	MAGIC,
	RANGED,
	;

	public boolean isMelee()
	{
		switch (this)
		{
			case STAB:
			case SLASH:
			case CRUSH:
				return true;

			default:
				return false;
		}
	}

}
