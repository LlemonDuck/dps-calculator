package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeOutput;
import com.google.common.collect.ImmutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrueMaxHitComputableTest
{

	@Mock
	private BaseMaxHitComputable baseMaxHitComputable;

	@Mock
	private ComputeContext context;

	@Mock
	private ComputeOutput<Integer> effectMaxHit1, effectMaxHit2;

	private TrueMaxHitComputable trueMaxHitComputable;

	@BeforeEach
	private void setUp()
	{
		trueMaxHitComputable = new TrueMaxHitComputable(
			baseMaxHitComputable,
			ImmutableList.of(effectMaxHit1, effectMaxHit2)
		);
	}

	@Test
	void selectsFirstNonNullOutput()
	{
		when(context.get(effectMaxHit1)).thenReturn(null, 3);
		when(context.get(effectMaxHit2)).thenReturn(5, (Integer) null);

		assertEquals(trueMaxHitComputable.compute(context), 5);
		assertEquals(trueMaxHitComputable.compute(context), 3);
	}

	@Test
	void defaultsToBaseMaxHit()
	{
		when(context.get(effectMaxHit1)).thenReturn(null);
		when(context.get(effectMaxHit2)).thenReturn(null);
		when(context.get(baseMaxHitComputable)).thenReturn(12);

		assertEquals(12, trueMaxHitComputable.compute(context));
	}

}