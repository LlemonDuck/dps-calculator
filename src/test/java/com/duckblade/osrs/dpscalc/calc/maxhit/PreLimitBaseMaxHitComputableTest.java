package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.magic.MageMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PreLimitBaseMaxHitComputableTest
{

	@Mock
	private MageMaxHitComputable mageMaxHitComputable;

	@Mock
	private MeleeRangedMaxHitComputable meleeRangedMaxHitComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;

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

		assertEquals(12, preLimitBaseMaxHitComputable.compute(context));
		assertEquals(12, preLimitBaseMaxHitComputable.compute(context));
		assertEquals(34, preLimitBaseMaxHitComputable.compute(context));
	}

}
