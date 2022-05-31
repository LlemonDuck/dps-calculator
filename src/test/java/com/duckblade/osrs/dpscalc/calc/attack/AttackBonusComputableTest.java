package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttackBonusComputableTest
{

	private static final ItemStats ITEM_STATS = ItemStats.builder()
		.accuracyStab(12)
		.accuracySlash(23)
		.accuracyCrush(34)
		.accuracyRanged(45)
		.accuracyMagic(56)
		.build();

	@Mock
	private AttackerItemStatsComputable attackerItemStatsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private AttackBonusComputable attackBonusComputable;

	@BeforeEach
	void setUp()
	{
		when(context.get(attackerItemStatsComputable)).thenReturn(ITEM_STATS);
	}

	@Test
	void handlesStabAttacks()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));

		assertEquals(ITEM_STATS.getAccuracyStab(), attackBonusComputable.compute(context));
	}

	@Test
	void handlesSlashAttacks()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertEquals(ITEM_STATS.getAccuracySlash(), attackBonusComputable.compute(context));
	}

	@Test
	void handlesCrushAttacks()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));

		assertEquals(ITEM_STATS.getAccuracyCrush(), attackBonusComputable.compute(context));
	}

	@Test
	void handlesRangedAttacks()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(ITEM_STATS.getAccuracyRanged(), attackBonusComputable.compute(context));
	}

	@Test
	void handlesMagicAttacks()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals(ITEM_STATS.getAccuracyMagic(), attackBonusComputable.compute(context));
	}

}