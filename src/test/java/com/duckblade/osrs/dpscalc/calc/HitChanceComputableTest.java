package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.attack.AttackRollComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HitChanceComputableTest
{

	@Mock
	private AttackRollComputable attackRollComputable;

	@Mock
	private DefenseRollComputable defenseRollComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private HitChanceComputable hitChanceComputable;

	@Test
	void isCorrectWhenAttackHigherThanDefense()
	{
		when(context.get(attackRollComputable)).thenReturn(456);
		when(context.get(defenseRollComputable)).thenReturn(123);

		double expected = 1.0 - ((123.0 + 2.0) / (2.0 * (456.0 + 1.0)));
		assertEquals(expected, hitChanceComputable.compute(context));
	}

	@Test
	void isCorrectWhenDefenseHigherThanAttack()
	{
		when(context.get(attackRollComputable)).thenReturn(123);
		when(context.get(defenseRollComputable)).thenReturn(456);

		double expected = 123.0 / (2.0 * (456.0 + 1));
		assertEquals(expected, hitChanceComputable.compute(context));
	}

}