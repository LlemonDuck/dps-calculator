package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.PreLimitBaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ColossalBladeDptComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private HitChanceComputable hitChanceComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;

	@Mock
	private MaxHitLimitComputable maxHitLimitComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private ColossalBladeDptComputable colossalBladeDptComputable;

	@Test
	void isApplicableWhenUsingColossalBlade()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.COLOSSAL_BLADE));

		assertTrue(colossalBladeDptComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenUsingMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(colossalBladeDptComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingColossalBlade()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SCYTHE_OF_VITUR));

		assertFalse(colossalBladeDptComputable.isApplicable(context));
	}

	@Test
	void increasesMaxHitByDoubleSize()
	{
		when(context.get(hitChanceComputable)).thenReturn(0.75);
		when(context.get(attackSpeedComputable)).thenReturn(2);
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(15);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.builder().size(1).build(),
			DefenderAttributes.builder().size(3).build(),
			DefenderAttributes.builder().size(5).build()
		);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));

		assertEquals(BaseHitDptComputable.byComponents(0.75, 15 + 2, 2), colossalBladeDptComputable.compute(context));
		assertEquals(BaseHitDptComputable.byComponents(0.75, 15 + 6, 2), colossalBladeDptComputable.compute(context));
		assertEquals(BaseHitDptComputable.byComponents(0.75, 15 + 10, 2), colossalBladeDptComputable.compute(context));
	}

	@Test
	void boundsSizeBetween1And5()
	{
		when(context.get(hitChanceComputable)).thenReturn(0.75);
		when(context.get(attackSpeedComputable)).thenReturn(2);
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(15);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributes.builder().size(0).build(),
			DefenderAttributes.builder().size(6).build()
		);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));

		assertEquals(BaseHitDptComputable.byComponents(0.75, 15 + 2, 2), colossalBladeDptComputable.compute(context));
		assertEquals(BaseHitDptComputable.byComponents(0.75, 15 + 10, 2), colossalBladeDptComputable.compute(context));
	}

}