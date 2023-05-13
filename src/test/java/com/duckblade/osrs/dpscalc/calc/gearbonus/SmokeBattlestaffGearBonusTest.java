package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.*;

import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import net.runelite.api.ItemID;
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
class SmokeBattlestaffGearBonusTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private SmokeBattlestaffGearBonus smokeBattlestaffGearBonus;

	@Test
	void isApplicableWhenUsingSmokeStaffForStandardSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SMOKE_BATTLESTAFF));
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.WATER_STRIKE);

		assertTrue(smokeBattlestaffGearBonus.isApplicable(context));
	}

	@Test
	void isApplicableWhenNotUsingSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SMOKE_BATTLESTAFF));
		when(context.get(ComputeInputs.SPELL)).thenReturn(null);
		assertFalse(smokeBattlestaffGearBonus.isApplicable(context));
	}

	@Test
	void isApplicableWhenNotUsingSmokeStaff()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
				AttackStyle.builder()
					.attackType(AttackType.MAGIC)
					.isManualCast(true)
					.build(),
				AttackStyle.builder()
					.attackType(AttackType.MAGIC)
					.combatStyle(CombatStyle.AUTOCAST)
					.build()
		);
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.MIST_BATTLESTAFF));
		when(context.get(ComputeInputs.SPELL)).thenReturn(
				Spell.FIRE_SURGE
		);
		assertFalse(smokeBattlestaffGearBonus.isApplicable(context));
		assertFalse(smokeBattlestaffGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingStandardSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SMOKE_BATTLESTAFF));
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.ICE_BARRAGE);

		assertFalse(smokeBattlestaffGearBonus.isApplicable(context));
	}

	@Test
	void grantsApplicableBonus()
	{
		assertEquals(GearBonuses.symmetric(1.1), smokeBattlestaffGearBonus.compute(context));
	}

}