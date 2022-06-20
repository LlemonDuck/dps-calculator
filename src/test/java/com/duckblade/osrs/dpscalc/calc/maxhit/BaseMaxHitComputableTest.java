package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BaseMaxHitComputableTest
{

	@Mock
	private PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;

	@Mock
	private MaxHitLimitComputable maxHitLimitComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private BaseMaxHitComputable baseMaxHitComputable;

	@Test
	void appliesMaxHitLimiter()
	{
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(12);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenReturn(5);

		assertEquals(5, baseMaxHitComputable.compute(context));
	}

}