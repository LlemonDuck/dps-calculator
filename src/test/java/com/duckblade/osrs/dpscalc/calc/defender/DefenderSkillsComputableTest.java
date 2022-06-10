package com.duckblade.osrs.dpscalc.calc.defender;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.skills.SkillScaling;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import com.google.common.collect.ImmutableSet;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefenderSkillsComputableTest
{

	@Mock
	private SkillScaling skillScaling1, skillScaling2;

	@Mock
	private ComputeContext context;

	private DefenderSkillsComputable defenderSkillsComputable;

	@BeforeEach
	void setUp()
	{
		defenderSkillsComputable = new DefenderSkillsComputable(
			ImmutableSet.of(
				skillScaling1,
				skillScaling2
			)
		);
	}

	@Test
	void defaultsToInputWhenNonApplicable()
	{
		when(skillScaling1.isApplicable(context)).thenReturn(false);
		when(skillScaling2.isApplicable(context)).thenReturn(false);
		when(context.get(ComputeInputs.DEFENDER_SKILLS)).thenReturn(ofSkill(Skill.HITPOINTS, 50));

		assertEquals(ofSkill(Skill.HITPOINTS, 50), defenderSkillsComputable.compute(context));
		verify(skillScaling1, never()).compute(any());
		verify(skillScaling2, never()).compute(any());
	}

	@Test
	void returnsFirstResultWhenApplicable()
	{
		when(skillScaling1.isApplicable(context)).thenReturn(false);
		when(skillScaling2.isApplicable(context)).thenReturn(true);
		when(context.get(skillScaling2)).thenReturn(ofSkill(Skill.ATTACK, 100));

		assertEquals(ofSkill(Skill.ATTACK, 100), defenderSkillsComputable.compute(context));
		verify(skillScaling1, never()).compute(any());
	}

}