package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.magic.MageMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BaseMaxHitComputableTest
{

	@Mock
	private MeleeRangedMaxHitComputable meleeRangedMaxHitComputable;

	@Mock
	private MageMaxHitComputable mageMaxHitComputable;

	@Mock
	private MaxHitLimitComputable maxHitLimitComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private BaseMaxHitComputable baseMaxHitComputable;

	@Test
	void defersToCorrectCombatStyle()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.RANGED),
			ofAttackType(AttackType.MAGIC)
		);
		when(context.get(meleeRangedMaxHitComputable)).thenReturn(12);
		when(context.get(mageMaxHitComputable)).thenReturn(34);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));

		assertEquals(12, baseMaxHitComputable.compute(context));
		assertEquals(12, baseMaxHitComputable.compute(context));
		assertEquals(34, baseMaxHitComputable.compute(context));
	}

	@Test
	void appliesMaxHitLimiter()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));
		when(context.get(meleeRangedMaxHitComputable)).thenReturn(12);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenReturn(5);

		assertEquals(5, baseMaxHitComputable.compute(context));
	}

}