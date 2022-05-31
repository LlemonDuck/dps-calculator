package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.gearbonus.AggregateGearBonusesComputable;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MeleeRangedMaxHitComputableTest
{

	@Mock
	private MeleeEffectiveStrengthLevelComputable meleeEffectiveStrengthLevelComputable;

	@Mock
	private RangedEffectiveStrengthLevelComputable rangedEffectiveStrengthLevelComputable;

	@Mock
	private StrengthBonusComputable strengthBonusComputable;

	@Mock
	private AggregateGearBonusesComputable aggregateGearBonusesComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private MeleeRangedMaxHitComputable meleeRangedMaxHitComputable;

	@Test
	void combinesValuesCorrectlyForMelee()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(meleeEffectiveStrengthLevelComputable)).thenReturn(512);
		when(context.get(strengthBonusComputable)).thenReturn(123);
		when(context.get(aggregateGearBonusesComputable)).thenReturn(GearBonuses.of(1.1, 1.5));

		int expected = (int) ((512 * (123 + 64) + 320) / 640 * 1.5);
		assertEquals(expected, meleeRangedMaxHitComputable.compute(context));
	}

	@Test
	void combinesValuesCorrectlyForRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(rangedEffectiveStrengthLevelComputable)).thenReturn(256);
		when(context.get(strengthBonusComputable)).thenReturn(321);
		when(context.get(aggregateGearBonusesComputable)).thenReturn(GearBonuses.of(1.2, 1.4));

		int expected = (int) ((256 * (321 + 64) + 320) / 640 * 1.4);
		assertEquals(expected, meleeRangedMaxHitComputable.compute(context));
	}

}