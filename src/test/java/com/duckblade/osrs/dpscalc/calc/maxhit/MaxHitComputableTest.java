package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimiter;
import com.duckblade.osrs.dpscalc.calc.maxhit.magic.MageMaxHitComputable;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.google.common.collect.ImmutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MaxHitComputableTest
{

	@Mock
	private MeleeRangedMaxHitComputable meleeRangedMaxHitComputable;

	@Mock
	private MageMaxHitComputable mageMaxHitComputable;

	@Mock
	private ComputeContext context;

	@Mock
	private MaxHitLimiter maxHitLimiter1, maxHitLimiter2;

	private MaxHitComputable maxHitComputable;

	@BeforeEach
	void setUp()
	{
		maxHitComputable = new MaxHitComputable(
			meleeRangedMaxHitComputable,
			mageMaxHitComputable,
			ImmutableSet.of(maxHitLimiter1, maxHitLimiter2)
		);
	}

	@Test
	void defersToCorrectCombatStyle()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.RANGED),
			ofAttackType(AttackType.MAGIC)
		);
		when(maxHitLimiter1.isApplicable(context)).thenReturn(false);
		when(maxHitLimiter2.isApplicable(context)).thenReturn(false);
		when(context.get(meleeRangedMaxHitComputable)).thenReturn(12);
		when(context.get(mageMaxHitComputable)).thenReturn(34);

		assertEquals(12, maxHitComputable.compute(context));
		assertEquals(12, maxHitComputable.compute(context));
		assertEquals(34, maxHitComputable.compute(context));
	}

	@Test
	void appliesLowestMaxHitLimiter()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(maxHitLimiter1.isApplicable(context)).thenReturn(true);
		when(context.get(maxHitLimiter1)).thenReturn(5);
		when(maxHitLimiter2.isApplicable(context)).thenReturn(true);
		when(context.get(maxHitLimiter2)).thenReturn(3);
		when(context.get(meleeRangedMaxHitComputable)).thenReturn(12);

		assertEquals(3, maxHitComputable.compute(context));
	}

}