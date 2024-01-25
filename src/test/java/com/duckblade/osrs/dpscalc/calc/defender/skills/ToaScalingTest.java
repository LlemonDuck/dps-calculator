package com.duckblade.osrs.dpscalc.calc.defender.skills;

import com.duckblade.osrs.dpscalc.calc.ToaArena;
import com.duckblade.osrs.dpscalc.calc.ToaArenaComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToaScalingTest
{
	@Mock
	private ComputeContext context;

	@Mock
	private ToaArenaComputable toaArenaComputable;

	@InjectMocks
	private ToaScaling toaSkillScaling;

	@Test
	void isApplicableForToaNpcs()
	{
		when(context.get(toaArenaComputable)).thenReturn(
				ToaArena.FIGHTING_PATH_NPC,
				ToaArena.FIGHTING_WARDENS
		);

		assertTrue(toaSkillScaling.isApplicable(context));
		assertTrue(toaSkillScaling.isApplicable(context));
	}

	@Test
	void isNotApplicableForOtherNpcs()
	{
		when(context.get(toaArenaComputable)).thenReturn(
				ToaArena.FIGHTING_OUTSIDE_TOA
		);

		assertFalse(toaSkillScaling.isApplicable(context));
	}

	@Test
	void scalesHealthByPartySize()
	{
		when(context.get(toaArenaComputable)).thenReturn(ToaArena.FIGHTING_WARDENS);
		when(context.get(ComputeInputs.RAID_PARTY_SIZE)).thenReturn(1, 2, 3, 4, 5, 6, 7, 8);
		when(context.get(ComputeInputs.TOA_INVOCATION_LEVEL)).thenReturn(0);

		assertEquals(1000, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1900, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(2800, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(3400, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(4000, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(4600, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(5200, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(5800, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
	}

	@Test
	void scalesHealthByInvocationLevel()
	{
		when(context.get(toaArenaComputable)).thenReturn(ToaArena.FIGHTING_WARDENS);
		when(context.get(ComputeInputs.TOA_INVOCATION_LEVEL)).thenReturn(0, 150, 250, 300);
		when(context.get(ComputeInputs.RAID_PARTY_SIZE)).thenReturn(1);

		assertEquals(1000, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1600, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(2000, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(2200, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
	}

	@Test
	void scalesHealthByPathLevel()
	{
		when(context.get(toaArenaComputable)).thenReturn(ToaArena.FIGHTING_PATH_NPC);
		when(context.get(ComputeInputs.TOA_PATH_LEVEL)).thenReturn(0, 1, 2, 4, 6, 10);
		when(context.get(ComputeInputs.RAID_PARTY_SIZE)).thenReturn(1);
		when(context.get(ComputeInputs.TOA_INVOCATION_LEVEL)).thenReturn(0);

		assertEquals(1000, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1080, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1130, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1230, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1330, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
		assertEquals(1330, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
	}

	@Test
	void doesNotScaleWardensByPathLevel()
	{
		when(context.get(toaArenaComputable)).thenReturn(ToaArena.FIGHTING_WARDENS);
		when(context.get(ComputeInputs.RAID_PARTY_SIZE)).thenReturn(1);
		when(context.get(ComputeInputs.TOA_INVOCATION_LEVEL)).thenReturn(0);

		assertEquals(1000, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));

		verify(context, never()).get(ComputeInputs.TOA_PATH_LEVEL);
	}

	@Test
	void differentScalingsApplyMultiplicatively()
	{
		when(context.get(toaArenaComputable)).thenReturn(ToaArena.FIGHTING_PATH_NPC);
		when(context.get(ComputeInputs.TOA_INVOCATION_LEVEL)).thenReturn(250);
		when(context.get(ComputeInputs.RAID_PARTY_SIZE)).thenReturn(5);
		when(context.get(ComputeInputs.TOA_PATH_LEVEL)).thenReturn(3);

		assertEquals(9440, toaSkillScaling.scale(context, Skill.HITPOINTS, 1000));
	}

	@Test
	void doesNotScaleOtherSkills()
	{
		assertEquals(100, toaSkillScaling.scale(context, Skill.ATTACK, 100));
		assertEquals(100, toaSkillScaling.scale(context, Skill.STRENGTH, 100));
		assertEquals(100, toaSkillScaling.scale(context, Skill.RANGED, 100));
		assertEquals(100, toaSkillScaling.scale(context, Skill.MAGIC, 100));
		assertEquals(100, toaSkillScaling.scale(context, Skill.DEFENCE, 100));
	}

}
