package com.duckblade.osrs.dpscalc.calc.util;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicksToDurationComputableTest
{

	@Mock
	private ComputeContext context;

	@Mock
	private TicksToDurationComputable ticksToDurationComputable;

	@BeforeEach
	void setUp()
	{
		when(ticksToDurationComputable.compute(context)).thenCallRealMethod();
	}

	@Test
	void convertsNegativeTicksToNull()
	{
		when(ticksToDurationComputable.getTicks(context)).thenReturn(
			-1,
			-100
		);

		assertNull(ticksToDurationComputable.compute(context));
		assertNull(ticksToDurationComputable.compute(context));
	}

	@Test
	void convertsTicksToSeconds()
	{
		when(ticksToDurationComputable.getTicks(context)).thenReturn(
			5,
			10,
			15
		);

		assertEquals(Duration.ofSeconds(3), ticksToDurationComputable.compute(context));
		assertEquals(Duration.ofSeconds(6), ticksToDurationComputable.compute(context));
		assertEquals(Duration.ofSeconds(9), ticksToDurationComputable.compute(context));
	}

}