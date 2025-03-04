package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import net.runelite.api.Skill;

@Data
@Builder(toBuilder = true)
@With
public class MonsterSkills
{

	@Builder.Default
	int hp = 0;

	@Builder.Default
	int atk = 0;

	@Builder.Default
	int str = 0;

	@Builder.Default
	int def = 0;

	@Builder.Default
	int ranged = 0;

	@Builder.Default
	int magic = 0;

	public int get(Skill skill)
	{
		switch (skill)
		{
			case HITPOINTS:
				return hp;

			case ATTACK:
				return atk;

			case STRENGTH:
				return str;

			case DEFENCE:
				return def;

			case RANGED:
				return ranged;

			case MAGIC:
				return magic;

			default:
				throw new IllegalArgumentException("unmapped skill " + skill.name());
		}
	}

	public void set(Skill skill, int value)
	{
		switch (skill)
		{
			case HITPOINTS:
				hp = value;
				break;

			case ATTACK:
				atk = value;
				break;

			case STRENGTH:
				str = value;
				break;

			case DEFENCE:
				def = value;
				break;

			case RANGED:
				ranged = value;
				break;

			case MAGIC:
				magic = value;
				break;

			default:
				throw new IllegalArgumentException("unmapped skill " + skill.name());
		}
	}

}
