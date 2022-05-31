package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.CALLISTO;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.ZULRAH;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
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
class CombatStyleImmunityMaxHitLimiterTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private CombatStyleImmunityMaxHitLimiter combatStyleImmunityMaxHitLimiter;

	@Test
	void isApplicableWhenImmune()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
//			ofAttackType(AttackType.RANGED), no ranged immunities
			ofAttackType(AttackType.MAGIC)
		);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			ZULRAH,
//			DefenderAttributes.builder().npcId().build(), no ranged immunities
			CALLISTO
		);

		assertTrue(combatStyleImmunityMaxHitLimiter.isApplicable(context));
//		assertTrue(combatStyleImmunityMaxHitLimiter.isApplicable(context)); no ranged immunities
		assertTrue(combatStyleImmunityMaxHitLimiter.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotImmune()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.RANGED),
			ofAttackType(AttackType.MAGIC)
		);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.EMPTY,
			CALLISTO,
			ZULRAH
		);

		assertFalse(combatStyleImmunityMaxHitLimiter.isApplicable(context));
		assertFalse(combatStyleImmunityMaxHitLimiter.isApplicable(context));
		assertFalse(combatStyleImmunityMaxHitLimiter.isApplicable(context));
	}

	@Test
	void limitsToZero()
	{
		assertEquals(0, combatStyleImmunityMaxHitLimiter.compute(context));
	}

}