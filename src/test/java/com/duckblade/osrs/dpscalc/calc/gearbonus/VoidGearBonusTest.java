package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.VoidLevelComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.VoidLevel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoidGearBonusTest
{

	@Mock
	private VoidLevelComputable voidLevelComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private VoidGearBonus voidGearBonus;

	@Test
	void isApplicableWhenWearingVoid()
	{
		when(context.get(voidLevelComputable)).thenReturn(VoidLevel.REGULAR, VoidLevel.ELITE);

		assertTrue(voidGearBonus.isApplicable(context));
		assertTrue(voidGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotWearingVoid()
	{
		when(context.get(voidLevelComputable)).thenReturn(VoidLevel.NONE);

		assertFalse(voidGearBonus.isApplicable(context));
	}

	@Test
	void providesCorrectBonusesForMelee()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(voidLevelComputable)).thenReturn(VoidLevel.REGULAR, VoidLevel.ELITE);

		assertEquals(GearBonuses.symmetric(1.1), voidGearBonus.compute(context));
		assertEquals(GearBonuses.symmetric(1.1), voidGearBonus.compute(context));
	}

	@Test
	void providesCorrectBonusesForRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(voidLevelComputable)).thenReturn(VoidLevel.REGULAR, VoidLevel.ELITE);

		assertEquals(GearBonuses.symmetric(1.1), voidGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.1, 1.125), voidGearBonus.compute(context));
	}

	@Test
	void providesCorrectBonusesForMage()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(voidLevelComputable)).thenReturn(VoidLevel.REGULAR, VoidLevel.ELITE);

		assertEquals(GearBonuses.of(1.45, 1), voidGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.45, 1.025), voidGearBonus.compute(context));
	}

}