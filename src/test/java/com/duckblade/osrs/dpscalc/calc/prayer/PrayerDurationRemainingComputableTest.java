package com.duckblade.osrs.dpscalc.calc.prayer;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrayerDurationRemainingComputableTest
{

	@Mock
	private AttackerItemStatsComputable attackerItemStatsComputable;

	@Mock
	private PrayerDrainComputable prayerDrainComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private PrayerDurationRemainingComputable prayerDurationRemainingComputable;

	@Test
	void givesInfinitePrayerOnZeroDrain()
	{
		when(context.get(prayerDrainComputable)).thenReturn(0);

		assertEquals(-1, prayerDurationRemainingComputable.getTicks(context));
	}

	@Test
	void computesPrayerTicksRemaining()
	{
		when(context.get(prayerDrainComputable)).thenReturn(12);
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(ofSkill(Skill.PRAYER, 12));
		when(context.get(attackerItemStatsComputable)).thenReturn(ItemStats.builder().prayer(30).build());

		assertEquals(120, prayerDurationRemainingComputable.getTicks(context));
	}

}