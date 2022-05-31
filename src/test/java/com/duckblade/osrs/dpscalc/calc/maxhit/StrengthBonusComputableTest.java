package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StrengthBonusComputableTest
{

	private static final ItemStats ITEM_STATS = ItemStats.builder()
		.strengthMelee(12)
		.strengthRanged(34)
		.strengthMagic(56.0)
		.build();

	@Mock
	private AttackerItemStatsComputable attackerItemStatsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private StrengthBonusComputable strengthBonusComputable;

	@Test
	void selectsMeleeStrengthForMelee()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(attackerItemStatsComputable)).thenReturn(ITEM_STATS);

		assertEquals(12, strengthBonusComputable.compute(context));
	}

	@Test
	void selectsRangedStrengthForRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(attackerItemStatsComputable)).thenReturn(ITEM_STATS);

		assertEquals(34, strengthBonusComputable.compute(context));
	}

	@Test
	void selectsMagicDamageBonusForMage()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(attackerItemStatsComputable)).thenReturn(ITEM_STATS);

		assertEquals(56.0, strengthBonusComputable.compute(context));
	}

}