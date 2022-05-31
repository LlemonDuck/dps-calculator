package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.DEMON;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MageDemonbaneGearBonusTest
{

	@Mock
	private ComputeContext context;

	@InjectMocks
	private MageDemonbaneGearBonus mageDemonbaneGearBonus;

	@Test
	void isApplicableWhenUsingDemonbaneSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.INFERIOR_DEMONBANE,
			Spell.SUPERIOR_DEMONBANE,
			Spell.DARK_DEMONBANE
		);

		assertTrue(mageDemonbaneGearBonus.isApplicable(context));
		assertTrue(mageDemonbaneGearBonus.isApplicable(context));
		assertTrue(mageDemonbaneGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertFalse(mageDemonbaneGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingDemonbaneSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.FIRE_SURGE);

		assertFalse(mageDemonbaneGearBonus.isApplicable(context));
	}

	@Test
	void warnsWhenNotFightingDemons()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertEquals(GearBonuses.symmetric(0), mageDemonbaneGearBonus.compute(context));
		verify(context, times(1)).warn("Demonbane spells cannot be used against non-demons.");
	}

	@Test
	void providesCorrectBonusWithoutMarkOfDarkness()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DEMON);
		when(context.get(ComputeInputs.USING_MARK_OF_DARKNESS)).thenReturn(false);

		assertEquals(GearBonuses.of(1.2, 1.25), mageDemonbaneGearBonus.compute(context));
	}

	@Test
	void providesCorrectBonusWithMarkOfDarkness()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DEMON);
		when(context.get(ComputeInputs.USING_MARK_OF_DARKNESS)).thenReturn(true);

		assertEquals(GearBonuses.of(1.4, 1.25), mageDemonbaneGearBonus.compute(context));
	}

}