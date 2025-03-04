package com.duckblade.osrs.dpscalc.calc.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class MonsterDefensive
{

	@Builder.Default
	@SerializedName("flat_armour")
	int flatArmour = 0;

	@Builder.Default
	int stab = 0;

	@Builder.Default
	int slash = 0;

	@Builder.Default
	int crush = 0;

	@Builder.Default
	int magic = 0;

	@Builder.Default
	int light = 0;

	@Builder.Default
	int standard = 0;

	@Builder.Default
	int heavy = 0;

	public int of(RangedDamageType rangedType)
	{
		switch (rangedType)
		{
			case LIGHT:
				return light;

			case STANDARD:
				return standard;

			case HEAVY:
				return heavy;

			case MIXED:
				return (light + standard + heavy) / 3;

			default:
				throw new IllegalArgumentException("Invalid ranged damage type: " + rangedType);
		}
	}

	public int of(CombatStyleType combatStyle)
	{
		switch (combatStyle)
		{
			case STAB:
				return stab;

			case SLASH:
				return slash;

			case CRUSH:
				return crush;

			case MAGIC:
				return magic;

			default:
				throw new IllegalArgumentException("Invalid combat style: " + combatStyle);
		}
	}
}
