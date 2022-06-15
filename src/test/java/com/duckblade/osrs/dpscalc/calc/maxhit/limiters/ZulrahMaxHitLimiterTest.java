package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.CALLISTO;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.ZULRAH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ZulrahMaxHitLimiterTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private ZulrahMaxHitLimiter zulrahMaxHitLimiter;

	@Test
	void isApplicableWhenFightingZulrah()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(ZULRAH);

		assertTrue(zulrahMaxHitLimiter.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotFightingZulrah()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			CALLISTO,
			DefenderAttributes.EMPTY
		);

		assertFalse(zulrahMaxHitLimiter.isApplicable(context));
		assertFalse(zulrahMaxHitLimiter.isApplicable(context));
	}

	@Test
	void limitsMaxHitTo50()
	{
		assertEquals(
			MaxHitLimit.of(50, "Zulrah has a max hit limiter of 50."),
			zulrahMaxHitLimiter.compute(context)
		);
	}

}