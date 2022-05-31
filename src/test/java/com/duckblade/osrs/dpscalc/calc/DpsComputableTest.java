package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DpsComputableTest
{

	@Mock
	private DptComputable dptComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private DpsComputable dpsComputable;

	@Test
	void convertsDptToDps()
	{
		when(context.get(dptComputable)).thenReturn(
			3.0,
			5.4,
			10.8
		);

		assertEquals(3.0 / 0.6, dpsComputable.compute(context));
		assertEquals(5.4 / 0.6, dpsComputable.compute(context));
		assertEquals(10.8 / 0.6, dpsComputable.compute(context));
	}

}