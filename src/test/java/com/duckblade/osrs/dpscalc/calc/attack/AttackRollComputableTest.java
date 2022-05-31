package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.gearbonus.AggregateGearBonusesComputable;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttackRollComputableTest
{

	@Mock
	private EffectiveAttackLevelComputable effectiveAttackLevelComputable;

	@Mock
	private AttackBonusComputable attackBonusComputable;

	@Mock
	private AggregateGearBonusesComputable aggregateGearBonusesComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private AttackRollComputable attackRollComputable;

	@Test
	void computesAttackRollCorrectly()
	{
		when(context.get(effectiveAttackLevelComputable)).thenReturn(12);
		when(context.get(attackBonusComputable)).thenReturn(34);
		when(context.get(aggregateGearBonusesComputable)).thenReturn(GearBonuses.of(1.2, 1.4));

		assertEquals((int) (12 * (34 + 64) * 1.2), attackRollComputable.compute(context));
	}

}