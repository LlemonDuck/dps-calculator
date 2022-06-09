package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofCombatStyle;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import net.runelite.api.ItemID;
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
class ChinchompaDistanceGearBonusTest
{

	private static final ItemStats CHINCHOMPA = ItemStats.builder()
		.itemId(ItemID.BLACK_CHINCHOMPA)
		.weaponCategory(WeaponCategory.CHINCHOMPAS)
		.build();

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private ChinchompaDistanceGearBonus chinchompaDistanceGearBonus;

	@Test
	void isApplicableWhenUsingChinchompas()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(CHINCHOMPA);

		assertTrue(chinchompaDistanceGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(chinchompaDistanceGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingChinchompas()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.TWISTED_BOW));

		assertFalse(chinchompaDistanceGearBonus.isApplicable(context));
	}

	@Test
	void givesCorrectBonusesForShortFuse()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofCombatStyle(CombatStyle.ACCURATE));
		when(context.get(ComputeInputs.ATTACK_DISTANCE)).thenReturn(1, 5, 9);

		assertEquals(GearBonuses.of(1.0, 1.0), chinchompaDistanceGearBonus.compute(context));
		assertEquals(GearBonuses.of(0.75, 1.0), chinchompaDistanceGearBonus.compute(context));
		assertEquals(GearBonuses.of(0.5, 1.0), chinchompaDistanceGearBonus.compute(context));
		verify(context, times(3)).warn("Chinchompa calculation does not support splash damage. Results listed are for a single target.");
	}

	@Test
	void givesCorrectBonusesForMediumFuse()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofCombatStyle(CombatStyle.RAPID));
		when(context.get(ComputeInputs.ATTACK_DISTANCE)).thenReturn(3, 4, 7);

		assertEquals(GearBonuses.of(0.75, 1.0), chinchompaDistanceGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.0, 1.0), chinchompaDistanceGearBonus.compute(context));
		assertEquals(GearBonuses.of(0.75, 1.0), chinchompaDistanceGearBonus.compute(context));
		verify(context, times(3)).warn("Chinchompa calculation does not support splash damage. Results listed are for a single target.");
	}

	@Test
	void givesCorrectBonusesForLongFuse()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofCombatStyle(CombatStyle.LONGRANGE));
		when(context.get(ComputeInputs.ATTACK_DISTANCE)).thenReturn(-1, 6, 100);

		assertEquals(GearBonuses.of(0.5, 1.0), chinchompaDistanceGearBonus.compute(context));
		assertEquals(GearBonuses.of(0.75, 1.0), chinchompaDistanceGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.0, 1.0), chinchompaDistanceGearBonus.compute(context));
		verify(context, times(3)).warn("Chinchompa calculation does not support splash damage. Results listed are for a single target.");
	}

}