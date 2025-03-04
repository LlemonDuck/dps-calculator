package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class PlayerCombatStyleStats
{

	@Builder.Default
	int stab = 0;

	@Builder.Default
	int slash = 0;

	@Builder.Default
	int crush = 0;

	@Builder.Default
	int ranged = 0;

	@Builder.Default
	int magic = 0;

	PlayerCombatStyleStats merge(PlayerCombatStyleStats other)
	{
		return PlayerCombatStyleStats.builder()
			.stab(this.stab + other.stab)
			.slash(this.slash + other.slash)
			.crush(this.crush + other.crush)
			.ranged(this.ranged + other.ranged)
			.magic(this.magic + other.magic)
			.build();
	}

	public int of(CombatStyleType type)
	{
		switch (type)
		{
			case STAB:
				return stab;

			case SLASH:
				return slash;

			case CRUSH:
				return crush;

			case RANGED:
				return ranged;

			case MAGIC:
				return magic;

			default:
				throw new IllegalArgumentException("Illegal combat style type: " + type);
		}
	}
}
