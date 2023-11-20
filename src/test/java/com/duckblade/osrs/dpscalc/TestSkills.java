package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.calc.model.Skills;
import java.util.Arrays;
import java.util.stream.Collectors;
import net.runelite.api.Skill;

public class TestSkills
{

	public static final Skills MAXED = Skills.builder()
		.levels(Arrays.stream(Skill.values()).collect(Collectors.toMap(skill -> skill, skill -> 99)))
		.build();

	public static final Skills MAXED_WITH_BOOSTS = MAXED.toBuilderDeep()
		.boost(Skill.ATTACK, 19) // super combat
		.boost(Skill.STRENGTH, 19) // super combat
		.boost(Skill.DEFENCE, 21) // sara brew
		.boost(Skill.RANGED, 13) // ranging potion
		.boost(Skill.MAGIC, 10) // imbued heart
		.build();

}
