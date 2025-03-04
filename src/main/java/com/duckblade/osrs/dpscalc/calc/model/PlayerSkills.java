package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import net.runelite.api.Skill;

@Data
@Builder(toBuilder = true)
@With
public class PlayerSkills
{

	public static final PlayerSkills MAXED = PlayerSkills.builder()
		.hp(99)
		.atk(99)
		.str(99)
		.def(99)
		.ranged(99)
		.magic(99)
		.prayer(99)
		.mining(99)
		.herblore(99)
		.build();

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

	@Builder.Default
	int prayer = 0;

	@Builder.Default
	int mining = 0;

	@Builder.Default
	int herblore = 0;

	public PlayerSkills add(PlayerSkills other)
	{
		return PlayerSkills.builder()
			.hp(this.hp + other.hp)
			.atk(this.atk + other.atk)
			.str(this.str + other.str)
			.def(this.def + other.def)
			.ranged(this.ranged + other.ranged)
			.magic(this.magic + other.magic)
			.prayer(this.prayer + other.prayer)
			.mining(this.mining + other.mining)
			.herblore(this.herblore + other.herblore)
			.build();
	}

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

			case PRAYER:
				return prayer;

			case MINING:
				return mining;

			case HERBLORE:
				return herblore;

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

			case PRAYER:
				prayer = value;
				break;

			case MINING:
				mining = value;
				break;

			case HERBLORE:
				herblore = value;
				break;

			default:
				throw new IllegalArgumentException("unmapped skill " + skill.name());
		}
	}

}
