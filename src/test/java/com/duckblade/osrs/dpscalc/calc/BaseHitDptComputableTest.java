package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BaseHitDptComputableTest
{

	@Mock
	private HitChanceComputable hitChanceComputable;

	@Mock
	private BaseMaxHitComputable baseMaxHitComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private BaseHitDptComputable baseHitDptComputable;

	@Test
	void computePassesChildrenToByComponents()
	{
		when(context.get(hitChanceComputable)).thenReturn(3.0);
		when(context.get(baseMaxHitComputable)).thenReturn(12);
		when(context.get(attackSpeedComputable)).thenReturn(4);

		assertEquals(BaseHitDptComputable.byComponents(3, 12, 4), baseHitDptComputable.compute(context));
	}

	@Test
	void byComponentsReturnsCorrectResults()
	{
		double hitChance = 0.5;
		int maxHit = 50;
		int attackSpeed = 5;

		assertEquals((hitChance * maxHit) / (2.0 * attackSpeed), BaseHitDptComputable.byComponents(hitChance, maxHit, attackSpeed));
	}

}