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
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KerisDptComputableTest
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
	private MaxHitLimitComputable maxHitLimitComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private KerisDptComputable kerisDptComputable;

	@Test
	void isNotApplicableWhenCastingSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(kerisDptComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingKerisAgainstKalphite()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.KERIS),
			ofItemId(ItemID.SCYTHE_OF_VITUR)
		);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			DefenderAttributesUtil.DRAGON,
			DefenderAttributesUtil.KALPHITE
		);

		assertFalse(kerisDptComputable.isApplicable(context));
		assertFalse(kerisDptComputable.isApplicable(context));
	}

	@Test
	void isApplicableWhenUsingKerisAgainstKalphite()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.KERIS),
			ofItemId(ItemID.KERIS_PARTISAN),
			ofItemId(ItemID.KERISP)
		);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.KALPHITE);

		assertTrue(kerisDptComputable.isApplicable(context));
		assertTrue(kerisDptComputable.isApplicable(context));
		assertTrue(kerisDptComputable.isApplicable(context));
	}

	@Test
	void appliesTripleHitAppropriately()
	{
		when(context.get(baseHitDptComputable)).thenReturn(4.0);
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(50);
		when(context.get(hitChanceComputable)).thenReturn(0.5);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));

		double kerisEffectDps = BaseHitDptComputable.byComponents(0.5, 150, 5);
		assertEquals((50.0 / 51.0) * 4.0 + (1.0 / 51.0) * kerisEffectDps, kerisDptComputable.compute(context));
		verify(context).put(KerisDptComputable.KERIS_MAX_HIT, 150);
	}

	@Test
	void respectsMaxHitLimiters()
	{
		when(context.get(baseHitDptComputable)).thenReturn(4.0);
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(50);
		when(context.get(hitChanceComputable)).thenReturn(0.5);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenReturn(5);

		double kerisEffectDps = BaseHitDptComputable.byComponents(0.5, 5, 5);
		assertEquals((50.0 / 51.0) * 4.0 + (1.0 / 51.0) * kerisEffectDps, kerisDptComputable.compute(context));
		verify(context).put(KerisDptComputable.KERIS_MAX_HIT, 5);
	}

}