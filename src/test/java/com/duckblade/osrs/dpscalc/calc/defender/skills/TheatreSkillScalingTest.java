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
class TheatreSkillScalingTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private TheatreSkillScaling theatreSkillScaling;

	@Test
	void isApplicableForTobNpcs()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.builder()
				.npcId(NpcID.THE_MAIDEN_OF_SUGADINTI)
				.build(),
			DefenderAttributes.builder()
				.npcId(NpcID.VERZIK_VITUR_10847)
				.build()
		);

		assertTrue(theatreSkillScaling.isApplicable(context));
		assertTrue(theatreSkillScaling.isApplicable(context));
	}

	@Test
	void isApplicableForTobHmNpcs()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.builder()
				.npcId(NpcID.THE_MAIDEN_OF_SUGADINTI_10822)
				.build(),
			DefenderAttributes.builder()
				.npcId(NpcID.VERZIK_VITUR_10847)
				.build()
		);

		assertTrue(theatreSkillScaling.isApplicable(context));
		assertTrue(theatreSkillScaling.isApplicable(context));
	}

	@Test
	void isNotApplicableForOtherNpcs()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.builder()
				.npcId(NpcID.GUARD)
				.build()
		);

		assertFalse(theatreSkillScaling.isApplicable(context));
	}

	@Test
	void scalesHealthByPartySize()
	{
		when(context.get(ComputeInputs.RAID_PARTY_SIZE)).thenReturn(2, 3, 4, 5, 6);

		assertEquals(750, theatreSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(750, theatreSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(875, theatreSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1000, theatreSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1000, theatreSkillScaling.scale(context, Skill.HITPOINTS, 1000));
	}

	@Test
	void doesNotScaleOtherSkills()
	{
		assertEquals(100, theatreSkillScaling.scale(context, Skill.ATTACK, 100));
		assertEquals(100, theatreSkillScaling.scale(context, Skill.DEFENCE, 100));
		assertEquals(100, theatreSkillScaling.scale(context, Skill.RANGED, 100));
	}

}