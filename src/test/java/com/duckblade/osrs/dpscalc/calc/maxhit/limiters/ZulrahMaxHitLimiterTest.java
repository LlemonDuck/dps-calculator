package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.MaxHitComputable;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.CALLISTO;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.ZULRAH;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
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
	void isApplicableWhenFightingZulrahAndMaxHitIsOver50()
	{
		when(context.get(MaxHitComputable.PRE_LIMIT_MAX_HIT)).thenReturn(51);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(ZULRAH);

		assertTrue(zulrahMaxHitLimiter.isApplicable(context));
		verify(context).warn("Zulrah has a max hit limiter of 50.");
	}

	@Test
	void isNotApplicableWhenNotFightingZulrah()
	{
		when(context.get(MaxHitComputable.PRE_LIMIT_MAX_HIT)).thenReturn(51);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			CALLISTO,
			DefenderAttributes.EMPTY
		);

		assertFalse(zulrahMaxHitLimiter.isApplicable(context));
		assertFalse(zulrahMaxHitLimiter.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenMaxHitIs50OrLess()
	{
		when(context.get(MaxHitComputable.PRE_LIMIT_MAX_HIT)).thenReturn(0, 50);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(ZULRAH);

		assertFalse(zulrahMaxHitLimiter.isApplicable(context));
		assertFalse(zulrahMaxHitLimiter.isApplicable(context));
	}

	@Test
	void limitsMaxHitTo50()
	{
		assertEquals(50, zulrahMaxHitLimiter.compute(context));
	}

}