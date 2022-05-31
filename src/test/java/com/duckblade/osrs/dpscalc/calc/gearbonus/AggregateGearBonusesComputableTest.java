package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AggregateGearBonusesComputableTest
{

	@Mock
	private GearBonusComputable gearBonus1, gearBonus2;

	@Mock
	private ComputeContext context;

	private AggregateGearBonusesComputable aggregateGearBonusesComputable;

	@BeforeEach
	void setUp()
	{
		aggregateGearBonusesComputable = new AggregateGearBonusesComputable(
			ImmutableSet.of(
				gearBonus1,
				gearBonus2
			)
		);
	}

	@Test
	void defaultsToEmptyWhenNonApplicable()
	{
		when(gearBonus1.isApplicable(context)).thenReturn(false);
		when(gearBonus2.isApplicable(context)).thenReturn(false);

		assertEquals(GearBonuses.EMPTY, aggregateGearBonusesComputable.compute(context));
		verify(gearBonus1, never()).compute(any());
		verify(gearBonus2, never()).compute(any());
	}

	@Test
	void returnsSingleResultWhenOneApplicable()
	{
		when(gearBonus1.isApplicable(context)).thenReturn(false);
		when(gearBonus2.isApplicable(context)).thenReturn(true);
		when(context.get(gearBonus2)).thenReturn(GearBonuses.symmetric(2));

		assertEquals(GearBonuses.symmetric(2), aggregateGearBonusesComputable.compute(context));
		verify(gearBonus1, never()).compute(any());
	}

	@Test
	void combinesResultsWhenMultipleApplicable()
	{
		when(gearBonus1.isApplicable(context)).thenReturn(true);
		when(context.get(gearBonus1)).thenReturn(GearBonuses.symmetric(1.5));

		when(gearBonus2.isApplicable(context)).thenReturn(true);
		when(context.get(gearBonus2)).thenReturn(GearBonuses.of(1.25, 3));

		assertEquals(GearBonuses.of(1.875, 4.5), aggregateGearBonusesComputable.compute(context));
	}

}