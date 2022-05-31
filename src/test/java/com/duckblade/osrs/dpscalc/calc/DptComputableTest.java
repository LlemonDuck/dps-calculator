package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.multihit.MultiHitDptComputable;
import com.google.common.collect.ImmutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DptComputableTest
{

	@Mock
	private BaseHitDptComputable baseHitDptComputable;

	@Mock
	private MultiHitDptComputable multiHitDptComputable1, multiHitDptComputable2;

	@Mock
	private ComputeContext context;

	private DptComputable dptComputable;

	@BeforeEach
	void setUp()
	{
		dptComputable = new DptComputable(
			baseHitDptComputable,
			ImmutableSet.of(multiHitDptComputable1, multiHitDptComputable2)
		);
	}

	@Test
	void usesApplicableMultiHitResult()
	{
		when(multiHitDptComputable1.isApplicable(context)).thenReturn(false);
		when(multiHitDptComputable2.isApplicable(context)).thenReturn(true);
		when(context.get(multiHitDptComputable2)).thenReturn(3.0);

		assertEquals(3.0, dptComputable.compute(context));
	}

	@Test
	void usesBaseResultWhenNonApplicable()
	{
		when(context.get(baseHitDptComputable)).thenReturn(5.0);
		when(multiHitDptComputable1.isApplicable(context)).thenReturn(false);
		when(multiHitDptComputable2.isApplicable(context)).thenReturn(false);

		assertEquals(5.0, dptComputable.compute(context));
	}

	@Test
	void passesExceptionsUpward()
	{
		when(context.get(baseHitDptComputable)).thenThrow(new RuntimeException());
		assertThrows(RuntimeException.class, () -> dptComputable.compute(context));
	}

}