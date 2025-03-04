package com.duckblade.osrs.dpscalc.calc.defender;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.skills.ToaScaling;
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

	private final DefenderSkillsComputable defenderSkillsComputable;
	private final ToaScaling invocationComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		Skills skills = context.get(defenderSkillsComputable);
		DefensiveBonuses defensiveBonuses = context.get(ComputeInputs.DEFENDER_BONUSES); // todo scaling bonuses

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

		return (int) ((defenseLevel + 9) * (defenseBonus + 64) * toaInvocationMultiplier(context));
	}

	private Double toaInvocationMultiplier(ComputeContext context)
	{
		if (invocationComputable.isApplicable(context))
		{
			return invocationComputable.invocationMultiplier(context);
		}
		else
		{
			return 1.0;
		}
	}
}
