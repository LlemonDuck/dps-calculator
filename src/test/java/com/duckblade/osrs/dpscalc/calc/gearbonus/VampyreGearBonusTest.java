package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
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
class VampyreGearBonusTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private VampyreGearBonus vampyreGearBonus;

	@Test
	void isApplicableAgainstVampyres()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.VAMPYRE);

		assertTrue(vampyreGearBonus.isApplicable(context));
		verify(context).warn("Bonuses for vampyre immunities / vampyrebane is not yet implemented.");
	}

	@Test
	void isNotApplicableAgainstNonVampyres()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertFalse(vampyreGearBonus.isApplicable(context));
	}

	@Test
	void returnsEmptyBonusesForNow()
	{
		assertEquals(GearBonuses.EMPTY, vampyreGearBonus.compute(context));
	}

}