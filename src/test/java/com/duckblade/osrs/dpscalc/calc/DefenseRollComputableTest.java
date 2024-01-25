package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.DefenderSkillsComputable;
import com.duckblade.osrs.dpscalc.calc.defender.DefenseRollComputable;
import com.duckblade.osrs.dpscalc.calc.defender.skills.ToaScaling;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefenseRollComputableTest
{

	private static final Skills SKILLS = Skills.builder()
		.level(Skill.DEFENCE, 12)
		.level(Skill.MAGIC, 34)
		.build();

	private static final DefensiveBonuses BONUSES = DefensiveBonuses.builder()
		.defenseStab(12)
		.defenseSlash(34)
		.defenseCrush(56)
		.defenseRanged(78)
		.defenseMagic(90)
		.build();

	@Mock
	private DefenderSkillsComputable defenderSkillsComputable;

	@Mock
	private ToaScaling toaScaling;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private DefenseRollComputable defenseRollComputable;

	@BeforeEach
	void setUp()
	{
		when(context.get(defenderSkillsComputable)).thenReturn(SKILLS);
		when(context.get(ComputeInputs.DEFENDER_BONUSES)).thenReturn(BONUSES);
		when(toaScaling.isApplicable(context)).thenReturn(false);
	}

	@Test
	void isCorrectForMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals((34 + 9) * (90 + 64), defenseRollComputable.compute(context));
	}

	@Test
	void isCorrectForRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals((12 + 9) * (78 + 64), defenseRollComputable.compute(context));
	}

	@Test
	void isCorrectForStab()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));

		assertEquals((12 + 9) * (12 + 64), defenseRollComputable.compute(context));
	}

	@Test
	void isCorrectForSlash()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertEquals((12 + 9) * (34 + 64), defenseRollComputable.compute(context));
	}

	@Test
	void isCorrectForCrush()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));

		assertEquals((12 + 9) * (56 + 64), defenseRollComputable.compute(context));
	}

	@Test
	void appliesToaScalingToDefenseRoll()
	{
		when(toaScaling.isApplicable(context)).thenReturn(true);
		when(toaScaling.invocationMultiplier(context)).thenReturn(2.0);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals((34 + 9) * (90 + 64) * 2, defenseRollComputable.compute(context));
	}
}
