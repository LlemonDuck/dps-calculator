package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.*;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OsmumtensFangDptComputableTest
{
	@Mock
	private FangHitChanceComputable fangHitChanceComputable;

	@Mock
	private BaseMaxHitComputable baseMaxHitComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private OsmumtensFangDptComputable osmumtensFangDptComputable;

	@Test
	void warnsWhenMaxHitLimitInEffect()
	{
		when(context.get(baseMaxHitComputable)).thenReturn(15);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(context.get(fangHitChanceComputable)).thenReturn(1.0);
		when(context.get(MaxHitLimitComputable.LIMIT_APPLIED)).thenReturn(true);

		assertEquals(BaseHitDptComputable.byComponents(1.0, 15, 5), osmumtensFangDptComputable.compute(context));
		verify(context, times(1)).warn("Max hit may be inaccurate due to conflicting effects of a max hit limiter and fang max hit clamping.");
	}

}
