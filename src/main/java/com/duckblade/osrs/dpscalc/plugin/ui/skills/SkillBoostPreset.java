package com.duckblade.osrs.dpscalc.plugin.ui.skills;

import com.duckblade.osrs.dpscalc.calc.model.Factor;
import static com.duckblade.osrs.dpscalc.calc.model.Factor.of;
import com.duckblade.osrs.dpscalc.calc.model.PlayerSkills;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@RequiredArgsConstructor
public enum SkillBoostPreset
{
	ATTACK_POTION("Attack potion", Skill.ATTACK, of(1, 10), 3),
	STRENGTH_POTION("Strength potion", Skill.STRENGTH, of(1, 10), 3),
	DEFENSE_POTION("Defence potion", Skill.DEFENCE, of(1, 10), 3),
	COMBAT_POTION("Combat potion", ATTACK_POTION, STRENGTH_POTION, DEFENSE_POTION),

	SUPER_ATTACK_POTION("Super attack potion", Skill.ATTACK, of(3, 20), 5),
	SUPER_STRENGTH_POTION("Super strength potion", Skill.STRENGTH, of(3, 20), 5),
	SUPER_DEFENSE_POTION("Super defence potion", Skill.DEFENCE, of(3, 20), 5),
	SUPER_COMBAT_POTION("Super combat potion", SUPER_ATTACK_POTION, SUPER_STRENGTH_POTION, SUPER_DEFENSE_POTION),

	RANGING_POTION("Ranging potion", Skill.RANGED, of(1, 10), 4),
	SUPER_RANGING_POTION("Super ranging potion (NMZ)", Skill.RANGED, of(3, 20), 5),
	BASTION_POTION("Bastion potion", SUPER_DEFENSE_POTION, RANGING_POTION),

	ANCIENT_BREW("Ancient brew", Skill.MAGIC, of(1, 20), 2),
	MAGIC_POTION("Magic potion", Skill.MAGIC, of(0, 20), 4),
	SUPER_MAGIC_POTION("Super magic potion (NMZ)", Skill.MAGIC, of(3, 20), 5),
	BATTLEMAGE_POTION("Battlemage potion", MAGIC_POTION, SUPER_DEFENSE_POTION),

	IMBUED_HEART("Imbued Heart", Skill.MAGIC, of(1, 10), 1),

	COX_OVERLOAD_MINUS(
		"Overload (-)",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new Factor[]{of(1, 10), of(1, 10), of(1, 10), of(1, 10), of(1, 10)},
		new int[]{4, 4, 4, 4, 4}
	),
	COX_OVERLOAD(
		"Overload",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new Factor[]{of(13, 100), of(13, 100), of(13, 100), of(13, 100), of(13, 100)},
		new int[]{5, 5, 5, 5, 5}
	),
	COX_OVERLOAD_PLUS(
		"Overload (+)",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new Factor[]{of(16, 100), of(16, 100), of(16, 100), of(16, 100), of(16, 100)},
		new int[]{6, 6, 6, 6, 6}
	),
	NMZ_OVERLOAD(
		"Overload (NMZ)",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC},
		new Factor[]{of(3, 20), of(3, 20), of(3, 20), of(3, 20), of(3, 20)},
		new int[]{5, 5, 5, 5, 5}
	),

	SARADOMIN_BREW(
		"Saradomin brew",
		new Skill[]{Skill.DEFENCE, Skill.ATTACK, Skill.STRENGTH, Skill.RANGED, Skill.MAGIC},
		new Factor[]{of(6, 5), of(-1, 10), of(-1, 10), of(-1, 10), of(-1, 10)},
		new int[]{2, -2, -2, -2, -2}
	),
	ZAMORAK_BREW(
		"Zamorak brew",
		new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE},
		new Factor[]{of(6, 5), of(12, 100), of(-1, 10)},
		new int[]{2, 2, -2}
	),

	D_BAXE_SPEC(
		"Dragon battleaxe spec", state ->
	{
		PlayerSkills totals = state.getPlayer().getSkillTotals();
		PlayerSkills boosts = state.getPlayer().getBoosts();

		int attDrain = totals.getAtk() / 10;
		boosts.setAtk(boosts.getAtk() - attDrain);

		int defDrain = totals.getDef() / 10;
		boosts.setDef(boosts.getDef() - defDrain);

		int rngDrain = totals.getRanged() / 10;
		boosts.setRanged(boosts.getRanged() - rngDrain);

		int magDrain = totals.getMagic() / 10;
		boosts.setMagic(boosts.getMagic() - magDrain);

		int strBoost = 10 + (attDrain + defDrain + rngDrain + magDrain) / 4;
		if (boosts.getStr() < strBoost)
		{
			boosts.setStr(Math.min(strBoost, boosts.getStr() + strBoost));
		}
	}
	),
	;

	@Getter
	private final String displayName;
	private final Consumer<ComputeInput> mapFunction;

	// lvl * factor + base
	SkillBoostPreset(String displayName, Skill skill, Factor factor, int base)
	{
		this(displayName, new Skill[]{skill}, new Factor[]{factor}, new int[]{base});
	}

	// lvl * factor + base for multiple skills
	SkillBoostPreset(String displayName, Skill[] skills, Factor[] factors, int[] bases)
	{
		this(
			displayName, state ->
			{
				for (int i = 0; i < skills.length; i++)
				{
					int lvl = state.getPlayer().getSkills().get(skills[i]);
					int targetBoost = (int) (factors[i].apply(lvl)) + bases[i];
					state.getPlayer().getBoosts().set(skills[i], targetBoost);
				}
			}
		);
	}

	// compositional
	SkillBoostPreset(String displayName, SkillBoostPreset... compositions)
	{
		this(
			displayName, state ->
			{
				for (SkillBoostPreset p : compositions)
				{
					p.apply(state);
				}
			}
		);
	}

	public void apply(ComputeInput state)
	{
		mapFunction.accept(state);
	}

}