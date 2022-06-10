package com.duckblade.osrs.dpscalc.calc.defender.skills;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.compute.OptionalComputable;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.runelite.api.Skill;

public interface SkillScaling extends OptionalComputable<Skills>
{

	int scale(ComputeContext context, Skill skill, int base);

	@Override
	default Skills compute(ComputeContext context)
	{
		Map<Skill, Integer> base = context.get(ComputeInputs.DEFENDER_SKILLS).getTotals();
		return Skills.builder()
			.levels(Arrays.stream(Skill.values())
				.collect(Collectors.toMap(
					s -> s,
					s -> scale(context, s, base.getOrDefault(s, 0))
				)))
			.build();
	}


}
