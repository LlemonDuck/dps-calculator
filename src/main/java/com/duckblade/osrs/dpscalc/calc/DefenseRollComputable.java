package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DefenseRollComputable implements Computable<Integer>
{

	@Override
	public Integer compute(ComputeContext context)
	{
		Skills skills = context.get(ComputeInputs.DEFENDER_SKILLS);
		DefensiveBonuses defensiveBonuses = context.get(ComputeInputs.DEFENDER_BONUSES);

		int defenseLevel;
		int defenseBonus;

		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		switch (attackStyle.getAttackType())
		{
			case MAGIC:
				defenseLevel = skills.getTotals().get(Skill.MAGIC);
				defenseBonus = defensiveBonuses.getDefenseMagic();
				break;

			case RANGED:
				defenseLevel = skills.getTotals().get(Skill.DEFENCE);
				defenseBonus = defensiveBonuses.getDefenseRanged();
				break;

			case STAB:
				defenseLevel = skills.getTotals().get(Skill.DEFENCE);
				defenseBonus = defensiveBonuses.getDefenseStab();
				break;

			case SLASH:
				defenseLevel = skills.getTotals().get(Skill.DEFENCE);
				defenseBonus = defensiveBonuses.getDefenseSlash();
				break;

			default:
				defenseLevel = skills.getTotals().get(Skill.DEFENCE);
				defenseBonus = defensiveBonuses.getDefenseCrush();
				break;
		}

		return (defenseLevel + 9) * (defenseBonus + 64);
	}
}
