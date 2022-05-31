package com.duckblade.osrs.dpscalc.calc.util;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EffectiveLevelComputableTest
{

	@Mock
	private EffectiveLevelComputable effectiveLevelComputable;

	@Mock
	private ComputeContext context;

	@BeforeEach
	void setUp()
	{
		// this is the method we actually test, others can be mocked per-test
		when(effectiveLevelComputable.compute(context)).thenCallRealMethod();
	}

	@Test
	void computeReturnsCorrectAttackValues()
	{
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			Skills.builder()
				.level(Skill.ATTACK, 99)
				.level(Skill.STRENGTH, 12)
				.level(Skill.RANGED, 34)
				.level(Skill.MAGIC, 56)
				.boost(Skill.ATTACK, 12)
				.boost(Skill.RANGED, 16)
				.build()
		);
		when(effectiveLevelComputable.getBaseSkill()).thenReturn(Skill.ATTACK);

		when(context.get(ComputeInputs.ATTACKER_PRAYERS)).thenReturn(ImmutableSet.of(
			Prayer.PIETY,
			Prayer.CHIVALRY,
			Prayer.EAGLE_EYE,
			Prayer.MYSTIC_MIGHT
		));
		when(effectiveLevelComputable.getPrayerGroup()).thenReturn(Prayer.PrayerGroup.MELEE);
		when(effectiveLevelComputable.getPrayerMultiplier()).thenReturn(Prayer::getAttackMod);

		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.builder()
			.attackType(AttackType.RANGED)
			.combatStyle(CombatStyle.ACCURATE)
			.build());
		when(effectiveLevelComputable.getBaseBonus()).thenReturn(16);
		when(effectiveLevelComputable.getCombatFocusBonuses()).thenReturn(ImmutableMap.of(
			CombatStyle.ACCURATE, 11,
			CombatStyle.RAPID, 13
		));

		int expected = (int) ((99 + 12) * Prayer.PIETY.getAttackMod() + (16 + 11));
		assertEquals(expected, effectiveLevelComputable.compute(context));
	}

	@Test
	void computeReturnsCorrectStrengthValues()
	{
		// second test ensures that the class respects the provided values and is not defaulting to anything
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			Skills.builder()
				.level(Skill.ATTACK, 99)
				.level(Skill.STRENGTH, 12)
				.level(Skill.RANGED, 34)
				.level(Skill.MAGIC, 56)
				.boost(Skill.ATTACK, 12)
				.boost(Skill.RANGED, 16)
				.build()
		);
		when(effectiveLevelComputable.getBaseSkill()).thenReturn(Skill.RANGED);

		when(context.get(ComputeInputs.ATTACKER_PRAYERS)).thenReturn(ImmutableSet.of(
			Prayer.PIETY,
			Prayer.CHIVALRY,
			Prayer.EAGLE_EYE,
			Prayer.MYSTIC_MIGHT
		));
		when(effectiveLevelComputable.getPrayerGroup()).thenReturn(Prayer.PrayerGroup.RANGED);
		when(effectiveLevelComputable.getPrayerMultiplier()).thenReturn(Prayer::getStrengthMod);

		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.builder()
			.attackType(AttackType.RANGED)
			.combatStyle(CombatStyle.RAPID)
			.build());
		when(effectiveLevelComputable.getBaseBonus()).thenReturn(22);
		when(effectiveLevelComputable.getCombatFocusBonuses()).thenReturn(ImmutableMap.of(
			CombatStyle.ACCURATE, 11,
			CombatStyle.RAPID, 13
		));

		int expected = (int) ((34 + 16) * Prayer.EAGLE_EYE.getStrengthMod() + (22 + 13));
		assertEquals(expected, effectiveLevelComputable.compute(context));
	}

}