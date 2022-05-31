package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.gearbonus.AggregateGearBonusesComputable;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MageMaxHitComputableTest
{

	private static final ItemStats POWERED_STAFF = ItemStats.builder()
		.weaponCategory(WeaponCategory.POWERED_STAFF)
		.strengthMagic(10)
		.build();

	private static final ItemStats SALAMANDER = ItemStats.builder()
		.weaponCategory(WeaponCategory.SALAMANDER)
		.strengthMagic(20)
		.build();

	private static final ItemStats STANDARD_STAFF = ItemStats.builder()
		.weaponCategory(WeaponCategory.STAFF)
		.strengthMagic(30)
		.build();

	private static final GearBonuses GEAR_BONUSES = GearBonuses.of(1.4, 1.5);

	@Mock
	private AttackerItemStatsComputable attackerItemStatsComputable;

	@Mock
	private AggregateGearBonusesComputable aggregateGearBonusesComputable;

	@Mock
	private MagicSalamanderMaxHitComputable magicSalamanderMaxHitComputable;

	@Mock
	private PoweredStaffMaxHitComputable poweredStaffMaxHitComputable;

	@Mock
	private SpellcastingMaxHitBonusComputable spellcastingMaxHitBonusComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private MageMaxHitComputable mageMaxHitComputable;

	@Test
	void computesPoweredStaffMaxHitWhenUsingPoweredStaves()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(attackerItemStatsComputable)).thenReturn(POWERED_STAFF);
		when(context.get(aggregateGearBonusesComputable)).thenReturn(GEAR_BONUSES);
		when(context.get(poweredStaffMaxHitComputable)).thenReturn(15);

		assertEquals((int) (15 * (1.1 * 1.5)), mageMaxHitComputable.compute(context));
	}

	@Test
	void computesSalamanderMaxHitWhenUsingSalamanders()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(attackerItemStatsComputable)).thenReturn(SALAMANDER);
		when(context.get(aggregateGearBonusesComputable)).thenReturn(GEAR_BONUSES);
		when(context.get(magicSalamanderMaxHitComputable)).thenReturn(25);

		assertEquals((int) (25 * (1.2 * 1.5)), mageMaxHitComputable.compute(context));
	}

	@Test
	void computesBaseSpellMaxHitWhenUsingSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			AttackStyle.MANUAL_CAST,
			AttackStyle.MANUAL_CAST,
			ofAttackType(AttackType.MAGIC)
		);
		when(context.get(attackerItemStatsComputable)).thenReturn(
			POWERED_STAFF,
			SALAMANDER,
			STANDARD_STAFF
		);
		when(context.get(aggregateGearBonusesComputable)).thenReturn(GEAR_BONUSES);
		when(context.get(spellcastingMaxHitBonusComputable)).thenReturn(5);
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.BLOOD_BARRAGE);

		assertEquals((int) ((29 + 5) * (1.1 * 1.5)), mageMaxHitComputable.compute(context));
		assertEquals((int) ((29 + 5) * (1.2 * 1.5)), mageMaxHitComputable.compute(context));
		assertEquals((int) ((29 + 5) * (1.3 * 1.5)), mageMaxHitComputable.compute(context));
	}

}