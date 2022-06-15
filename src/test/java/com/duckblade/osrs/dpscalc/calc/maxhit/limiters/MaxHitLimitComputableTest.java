package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
import com.google.common.collect.ImmutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MaxHitLimitComputableTest
{

	@Mock
	private MaxHitLimiter limiter1, limiter2;

	@Mock
	private ComputeContext context;

	private MaxHitLimitComputable maxHitLimitComputable;

	@BeforeEach
	void setUp()
	{
		maxHitLimitComputable = new MaxHitLimitComputable(
			ImmutableSet.of(limiter1, limiter2)
		);
	}

	@Test
	void computeReturnsLowestApplicableLimiter()
	{
		when(limiter1.isApplicable(context)).thenReturn(true, false, true);
		when(limiter2.isApplicable(context)).thenReturn(false, true, true);
		when(context.get(limiter1)).thenReturn(MaxHitLimit.of(5, "A"));
		when(context.get(limiter2)).thenReturn(MaxHitLimit.of(3, "B"));

		assertEquals(MaxHitLimit.of(5, "A"), maxHitLimitComputable.compute(context));
		assertEquals(MaxHitLimit.of(3, "B"), maxHitLimitComputable.compute(context));
		assertEquals(MaxHitLimit.of(3, "B"), maxHitLimitComputable.compute(context));
		verify(context, times(3)).put(MaxHitLimitComputable.LIMIT_APPLIED, false);
	}

	@Test
	void computeDefaultsToUnlimited()
	{
		when(limiter1.isApplicable(context)).thenReturn(false);
		when(limiter2.isApplicable(context)).thenReturn(false);

		assertEquals(MaxHitLimit.UNLIMITED, maxHitLimitComputable.compute(context));
		verify(context, times(1)).put(MaxHitLimitComputable.LIMIT_APPLIED, false);
	}

	@Test
	void coerceDoesNothingWhenLimitIsGreaterThanMaxHit()
	{
		when(context.get(maxHitLimitComputable)).thenReturn(MaxHitLimit.of(10, "A"));

		assertEquals(5, maxHitLimitComputable.coerce(5, context));
		assertEquals(10, maxHitLimitComputable.coerce(10, context));
		verifyNoMoreInteractions(context);
	}

	@Test
	void coerceLimitsMaxHitAndWarnsIfNotYetWarned()
	{
		when(context.get(maxHitLimitComputable)).thenReturn(MaxHitLimit.of(10, "A"));
		when(context.get(MaxHitLimitComputable.LIMIT_APPLIED)).thenReturn(false);

		assertEquals(10, maxHitLimitComputable.coerce(15, context));
		verify(context, times(1)).warn("A");
		verify(context, times(1)).put(MaxHitLimitComputable.LIMIT_APPLIED, true);
	}

	@Test
	void coerceLimitsMaxHitAndDoesNotWarnIfAlreadyWarned()
	{
		when(context.get(maxHitLimitComputable)).thenReturn(MaxHitLimit.of(10, "A"));
		when(context.get(MaxHitLimitComputable.LIMIT_APPLIED)).thenReturn(true);

		assertEquals(10, maxHitLimitComputable.coerce(15, context));
		verifyNoMoreInteractions(context);
	}

}