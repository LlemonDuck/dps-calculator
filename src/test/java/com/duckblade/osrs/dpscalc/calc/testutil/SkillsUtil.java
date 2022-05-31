package com.duckblade.osrs.dpscalc.calc.testutil;

import com.duckblade.osrs.dpscalc.calc.model.Skills;
import net.runelite.api.Skill;

public class SkillsUtil
{

	public static Skills ofSkill(Skill skill, int level)
	{
		return Skills.builder()
			.level(skill, level)
			.build();
	}

}
