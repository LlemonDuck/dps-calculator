package com.duckblade.osrs.dpscalc.calc.defender.skills;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TheatreEntryModeSkillScalingTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private TheatreEntryModeSkillScaling theatreEntryModeSkillScaling;

	@Test
	void isApplicableForTobEmNpcs()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.builder()
				.npcId(NpcID.THE_MAIDEN_OF_SUGADINTI_10814)
				.build(),
			DefenderAttributes.builder()
				.npcId(NpcID.VERZIK_VITUR_10830)
				.build()
		);

		assertTrue(theatreEntryModeSkillScaling.isApplicable(context));
		assertTrue(theatreEntryModeSkillScaling.isApplicable(context));
	}

	@Test
	void isNotApplicableForOtherNpcs()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.builder()
				.npcId(NpcID.GUARD)
				.build()
		);

		assertFalse(theatreEntryModeSkillScaling.isApplicable(context));
	}

	@Test
	void scalesHealthByPartySize()
	{
		when(context.get(ComputeInputs.RAID_PARTY_SIZE)).thenReturn(1, 2, 3, 4, 5);

		assertEquals(200, theatreEntryModeSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(400, theatreEntryModeSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(600, theatreEntryModeSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(800, theatreEntryModeSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1000, theatreEntryModeSkillScaling.scale(context, Skill.HITPOINTS, 1000));
	}

	@Test
	void doesNotScaleOtherSkills()
	{
		assertEquals(100, theatreEntryModeSkillScaling.scale(context, Skill.ATTACK, 100));
		assertEquals(100, theatreEntryModeSkillScaling.scale(context, Skill.DEFENCE, 100));
		assertEquals(100, theatreEntryModeSkillScaling.scale(context, Skill.RANGED, 100));
	}

}