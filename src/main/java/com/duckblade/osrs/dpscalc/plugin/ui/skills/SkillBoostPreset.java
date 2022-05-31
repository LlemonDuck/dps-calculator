package com.duckblade.osrs.dpscalc.plugin.ui.skills;

import java.util.Map;
import java.util.function.BiConsumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@RequiredArgsConstructor
public enum SkillBoostPreset
{
	ATTACK_POTION("Attack potion", Skill.ATTACK, 0.10f, 3),
	STRENGTH_POTION("Strength potion", Skill.STRENGTH, 0.10f, 3),
	DEFENSE_POTION("Defence potion", Skill.DEFENCE, 0.10f, 3),
	COMBAT_POTION("Combat potion", ATTACK_POTION, STRENGTH_POTION, DEFENSE_POTION),

	SUPER_ATTACK_POTION("Super attack potion", Skill.ATTACK, 0.15f, 5),
	SUPER_STRENGTH_POTION("Super strength potion", Skill.STRENGTH, 0.15f, 5),
	SUPER_DEFENSE_POTION("Super defence potion", Skill.DEFENCE, 0.15f, 5),
	SUPER_COMBAT_POTION("Super combat potion", SUPER_ATTACK_POTION, SUPER_STRENGTH_POTION, SUPER_DEFENSE_POTION),

	RANGING_POTION("Ranging potion", Skill.RANGED, 0.10f, 4),
	SUPER_RANGING_POTION("Super ranging potion (NMZ)", Skill.RANGED, 0.15f, 5),
	BASTION_POTION("Bastion potion", SUPER_DEFENSE_POTION, RANGING_POTION),

	ANCIENT_BREW("Ancient brew", Skill.MAGIC, 0.05f, 2),
	MAGIC_POTION("Magic potion", Skill.MAGIC, 0f, 4),
	SUPER_MAGIC_POTION("Super magic potion (NMZ)", Skill.MAGIC, 0.15f, 5),
	BATTLEMAGE_POTION("Battlemage potion", MAGIC_POTION, SUPER_DEFENSE_POTION),

	IMBUED_HEART("Imbued Heart", Skill.MAGIC, 0.10f, 1),

	COX_OVERLOAD_MINUS("Overload (-)",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new float[]{0.10f, 0.10f, 0.10f, 0.10f, 0.10f},
		new int[]{4, 4, 4, 4, 4}
	),
	COX_OVERLOAD("Overload",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new float[]{0.13f, 0.13f, 0.13f, 0.13f, 0.13f},
		new int[]{5, 5, 5, 5, 5}
	),
	COX_OVERLOAD_PLUS("Overload (+)",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new float[]{0.16f, 0.16f, 0.16f, 0.16f, 0.16f},
		new int[]{6, 6, 6, 6, 6}
	),
	NMZ_OVERLOAD("Overload (NMZ)",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new float[]{0.15f, 0.15f, 0.15f, 0.15f, 0.15f},
		new int[]{5, 5, 5, 5, 5}
	),

	SARADOMIN_BREW("Saradomin brew",
		new Skill[]{Skill.DEFENCE, Skill.ATTACK, Skill.STRENGTH, Skill.RANGED, Skill.MAGIC},
		new float[]{0.20f, -0.10f, -0.10f, -0.10f, -0.10f},
		new int[]{2, -2, -2, -2, -2}
	),
	ZAMORAK_BREW("Zamorak brew",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE},
		new float[]{0.20f, 0.12f, -0.10f},
		new int[]{2, 2, -2}
	),

	D_BAXE_SPEC("Dragon battleaxe spec", (lvls, boosts) ->
	{
		int attDrain = (int) (lvls.get(Skill.ATTACK).getValue() * 0.10f);
		boosts.get(Skill.ATTACK).setValue(-attDrain);

		int defDrain = (int) (lvls.get(Skill.DEFENCE).getValue() * 0.10f);
		boosts.get(Skill.DEFENCE).setValue(-defDrain);

		int rngDrain = (int) (lvls.get(Skill.RANGED).getValue() * 0.10f);
		boosts.get(Skill.RANGED).setValue(-rngDrain);

		int magDrain = (int) (lvls.get(Skill.MAGIC).getValue() * 0.10f);
		boosts.get(Skill.MAGIC).setValue(-magDrain);

		int strBoost = 10 + (attDrain + defDrain + rngDrain + magDrain) / 4;
		boosts.get(Skill.STRENGTH).setValue(strBoost);
	}),
	;

	@Getter
	private final String displayName;
	private final BiConsumer<Map<Skill, StatBox>, Map<Skill, StatBox>> mapFunction;

	// lvl * factor + base
	SkillBoostPreset(String displayName, Skill skill, float percent, int base)
	{
		this(displayName, (levels, boosts) ->
		{
			int lvl = levels.get(skill).getValue();
			int targetBoost = (int) (lvl * percent) + base;
			boosts.get(skill).setValue(targetBoost);
		});
	}

	// lvl * factor + base for multiple skills
	SkillBoostPreset(String displayName, Skill[] skills, float[] percents, int[] bases)
	{
		this(displayName, (levels, boosts) ->
		{
			for (int i = 0; i < skills.length; i++)
			{
				int lvl = levels.get(skills[i]).getValue();
				int targetBoost = (int) (lvl * percents[i]) + bases[i];
				boosts.get(skills[i]).setValue(targetBoost);
			}
		});
	}

	// compositional
	SkillBoostPreset(String displayName, SkillBoostPreset... compositions)
	{
		this(displayName, (skills, boosts) ->
		{
			for (SkillBoostPreset p : compositions)
			{
				p.apply(skills, boosts);
			}
		});
	}

	public void apply(Map<Skill, StatBox> skills, Map<Skill, StatBox> boosts)
	{
		mapFunction.accept(skills, boosts);
	}

}