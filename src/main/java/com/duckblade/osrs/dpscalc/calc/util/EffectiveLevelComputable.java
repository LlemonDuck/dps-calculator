package com.duckblade.osrs.dpscalc.calc.util;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import java.util.Map;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import net.runelite.api.Skill;

// "Effective levels" come up a lot across both attack and strength rolls
public abstract class EffectiveLevelComputable implements Computable<Integer>
{

	protected abstract Skill getBaseSkill();

	protected abstract Prayer.PrayerGroup getPrayerGroup();

	protected abstract ToDoubleFunction<Prayer> getPrayerMultiplier();

	protected abstract int getBaseBonus();

	protected abstract Map<CombatStyle, Integer> getCombatFocusBonuses();

	@Override
	public Integer compute(ComputeContext context)
	{
		Skills skills = context.get(ComputeInputs.ATTACKER_SKILLS);
		int strLevel = skills.getTotals().get(getBaseSkill());

		Set<Prayer> prayers = context.get(ComputeInputs.ATTACKER_PRAYERS);
		double prayerBonus = prayers.stream()
			.filter(p -> p.getPrayerGroup() == this.getPrayerGroup())
			.mapToDouble(getPrayerMultiplier())
			.max()
			.orElse(1.0);

		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		int weaponModeBonus = getBaseBonus() + getCombatFocusBonuses().getOrDefault(attackStyle.getCombatStyle(), 0);

		return (int) (strLevel * prayerBonus + weaponModeBonus);
	}
}
