package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.PreLimitBaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScytheDptComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private BaseHitDptComputable baseHitDptComputable;

	@Mock
	private HitChanceComputable hitChanceComputable;

	@Mock
	private PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;

	@Mock
	private BaseMaxHitComputable baseMaxHitComputable;

	@Mock
	private MaxHitLimitComputable maxHitLimitComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private ScytheDptComputable scytheDptComputable;

	@Test
	void isNotApplicableWhenCastingSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(scytheDptComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingScythe()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.SCYTHE),
			ofItemId(-1)
		);

		assertFalse(scytheDptComputable.isApplicable(context));
		assertFalse(scytheDptComputable.isApplicable(context));
		assertFalse(scytheDptComputable.isApplicable(context));
	}

	@Test
	void isApplicableWhenUsingScythe()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.SCYTHE_OF_VITUR),
			ofItemId(ItemID.HOLY_SCYTHE_OF_VITUR),
			ofItemId(ItemID.SANGUINE_SCYTHE_OF_VITUR),
			ofItemId(ItemID.SCYTHE_OF_VITUR_UNCHARGED)
		);

		assertTrue(scytheDptComputable.isApplicable(context));
		assertTrue(scytheDptComputable.isApplicable(context));
		assertTrue(scytheDptComputable.isApplicable(context));
		assertTrue(scytheDptComputable.isApplicable(context));
	}

	@Test
	void warnsAgainstSize1Targets()
	{
		when(context.get(baseHitDptComputable)).thenReturn(1.5);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.SIZE_1);

		assertEquals(1.5, scytheDptComputable.compute(context));
		verify(context).warn("Using the Scythe of vitur against size 1 targets is inefficient unless hitting multiple enemies.");
	}

	@Test
	void appliesSecondHitAgainstSize2Targets()
	{
		when(context.get(baseHitDptComputable)).thenReturn(1.5);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.SIZE_2);
		when(context.get(hitChanceComputable)).thenReturn(0.5);
		when(context.get(baseMaxHitComputable)).thenReturn(4);
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(4);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));
		when(context.get(attackSpeedComputable)).thenReturn(5);

		double expectedSecondHitDps = BaseHitDptComputable.byComponents(0.5, 2, 5);
		assertEquals(1.5 + expectedSecondHitDps, scytheDptComputable.compute(context));
		verify(context).put(ScytheDptComputable.SCY_MAX_HIT_2, 2);
		verify(context).put(ScytheDptComputable.SCY_MAX_HIT_SUM, 6);
	}

	@Test
	void appliesSecondAndThirdHitAgainstSize3PlusTargets()
	{
		when(context.get(baseHitDptComputable)).thenReturn(1.5);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.SIZE_3, DefenderAttributesUtil.SIZE_4);
		when(context.get(hitChanceComputable)).thenReturn(0.5);
		when(context.get(baseMaxHitComputable)).thenReturn(4);
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(4);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));
		when(context.get(attackSpeedComputable)).thenReturn(5);

		double expectedSecondHitDps = BaseHitDptComputable.byComponents(0.5, 2, 5);
		double expectedThirdHitDps = BaseHitDptComputable.byComponents(0.5, 1, 5);
		assertEquals(1.5 + expectedSecondHitDps + expectedThirdHitDps, scytheDptComputable.compute(context));
		assertEquals(1.5 + expectedSecondHitDps + expectedThirdHitDps, scytheDptComputable.compute(context));
		verify(context, times(2)).put(ScytheDptComputable.SCY_MAX_HIT_2, 2);
		verify(context, times(2)).put(ScytheDptComputable.SCY_MAX_HIT_3, 1);
		verify(context, times(2)).put(ScytheDptComputable.SCY_MAX_HIT_SUM, 7);
	}

	@Test
	void respectsMaxHitLimiters()
	{
		when(context.get(baseHitDptComputable)).thenReturn(1.5);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.SIZE_3, DefenderAttributesUtil.SIZE_4);
		when(context.get(hitChanceComputable)).thenReturn(0.5);
		when(context.get(baseMaxHitComputable)).thenReturn(1);
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(4);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenReturn(1);
		when(context.get(attackSpeedComputable)).thenReturn(5);

		double expectedSecondHitDps = BaseHitDptComputable.byComponents(0.5, 1, 5);
		double expectedThirdHitDps = BaseHitDptComputable.byComponents(0.5, 1, 5);
		assertEquals(1.5 + expectedSecondHitDps + expectedThirdHitDps, scytheDptComputable.compute(context));
		assertEquals(1.5 + expectedSecondHitDps + expectedThirdHitDps, scytheDptComputable.compute(context));
		verify(context, times(2)).put(ScytheDptComputable.SCY_MAX_HIT_2, 1);
		verify(context, times(2)).put(ScytheDptComputable.SCY_MAX_HIT_3, 1);
		verify(context, times(2)).put(ScytheDptComputable.SCY_MAX_HIT_SUM, 3);
	}
}