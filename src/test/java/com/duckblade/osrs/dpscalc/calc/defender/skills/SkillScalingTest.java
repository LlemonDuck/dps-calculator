package com.duckblade.osrs.dpscalc.calc.defender.skills;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SkillScalingTest
{

	@Mock
	private ComputeContext context;

	@Mock
	private SkillScaling skillScaling;

	@BeforeEach
	void setUp()
	{
		when(skillScaling.compute(context)).thenCallRealMethod();
		when(skillScaling.scale(eq(context), any(), anyInt())).thenAnswer(i -> ((Integer) i.getArguments()[2]) * 2);
	}

	@Test
	void scalesEachSkillByImplementorScale()
	{
		when(context.get(ComputeInputs.DEFENDER_SKILLS)).thenReturn(
			Skills.builder()
				.level(Skill.HITPOINTS, 100)
				.level(Skill.ATTACK, 50)
				.boost(Skill.ATTACK, 25)
				.build()
		);

		Skills result = skillScaling.compute(context);
		assertEquals(200, result.getTotals().get(Skill.HITPOINTS));
		assertEquals(150, result.getTotals().get(Skill.ATTACK));
		assertEquals(0, result.getTotals().get(Skill.DEFENCE));
		verify(skillScaling, times(1)).scale(context, Skill.DEFENCE, 0);
	}
}