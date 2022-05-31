package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EffectiveAttackLevelComputableTest
{

	@Mock
	private MeleeEffectiveAttackLevelComputable meleeComputable;

	@Mock
	private RangedEffectiveAttackLevelComputable rangedComputable;

	@Mock
	private MageEffectiveAttackLevelComputable mageComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private EffectiveAttackLevelComputable effectiveAttackLevelComputable;

	@Test
	void defersToMelee()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(meleeComputable)).thenReturn(45);

		assertEquals(45, effectiveAttackLevelComputable.compute(context));
	}

	@Test
	void defersToRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(rangedComputable)).thenReturn(23);

		assertEquals(23, effectiveAttackLevelComputable.compute(context));
	}

	@Test
	void defersToMage()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(mageComputable)).thenReturn(67);

		assertEquals(67, effectiveAttackLevelComputable.compute(context));
	}

}