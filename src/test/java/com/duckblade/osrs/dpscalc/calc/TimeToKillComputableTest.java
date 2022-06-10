package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.defender.DefenderSkillsComputable;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeToKillComputableTest
{

	@Mock
	private DptComputable dptComputable;

	@Mock
	private DefenderSkillsComputable defenderSkillsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private TimeToKillComputable timeToKillComputable;

	@Test
	void givesInfinityOnZeroDps()
	{
		when(context.get(dptComputable)).thenReturn(0.0);

		assertEquals(-1, timeToKillComputable.getTicks(context));
	}

	@Test
	void givesTicksOnValidDps()
	{
		when(context.get(dptComputable)).thenReturn(1.0);
		when(context.get(defenderSkillsComputable)).thenReturn(ofSkill(Skill.HITPOINTS, 50));

		assertEquals(50, timeToKillComputable.getTicks(context));
	}

}