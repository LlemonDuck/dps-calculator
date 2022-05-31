package com.duckblade.osrs.dpscalc.calc.prayer;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.google.common.collect.ImmutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrayerDrainComputableTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private PrayerDrainComputable prayerDrainComputable;

	@Test
	void sumsPrayerDrains()
	{
		when(context.get(ComputeInputs.ATTACKER_PRAYERS)).thenReturn(ImmutableSet.of(
			Prayer.AUGURY,
			Prayer.PROTECT_ITEM,
			Prayer.PROTECT_FROM_MELEE
		));

		int expected = Prayer.AUGURY.getDrainRate() + Prayer.PROTECT_ITEM.getDrainRate() + Prayer.PROTECT_FROM_MELEE.getDrainRate();
		assertEquals(expected, prayerDrainComputable.compute(context));
	}

}