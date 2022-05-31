package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
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
class RevenantWeaponGearBonusTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private RevenantWeaponGearBonus revenantWeaponGearBonus;

	@Test
	void isApplicableWhenUsingChainmace()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.VIGGORAS_CHAINMACE));

		assertTrue(revenantWeaponGearBonus.isApplicable(context));
	}

	@Test
	void isApplicableWhenUsingCrawsBow()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.CRAWS_BOW));

		assertTrue(revenantWeaponGearBonus.isApplicable(context));
	}

	@Test
	void isApplicableWhenUsingSceptre()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.THAMMARONS_SCEPTRE));

		assertTrue(revenantWeaponGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenMisusingWeapons()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.MAGIC),
			ofAttackType(AttackType.MAGIC),
			ofAttackType(AttackType.STAB)
		);
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.VIGGORAS_CHAINMACE),
			ofItemId(ItemID.CRAWS_BOW),
			ofItemId(ItemID.THAMMARONS_SCEPTRE)
		);

		assertFalse(revenantWeaponGearBonus.isApplicable(context));
		assertFalse(revenantWeaponGearBonus.isApplicable(context));
		assertFalse(revenantWeaponGearBonus.isApplicable(context));
	}

	@Test
	void warnsWhenOutOfWilderness()
	{
		when(context.get(ComputeInputs.IN_WILDERNESS)).thenReturn(false);

		assertEquals(GearBonuses.EMPTY, revenantWeaponGearBonus.compute(context));
		verify(context, times(1)).warn("Using revenant weapons outside the wilderness provides no bonuses.");
	}

	@Test
	void givesCorrectBonusesForChainmace()
	{
		when(context.get(ComputeInputs.IN_WILDERNESS)).thenReturn(true);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));

		assertEquals(GearBonuses.symmetric(1.5), revenantWeaponGearBonus.compute(context));
	}

	@Test
	void givesCorrectBonusesForCrawsBow()
	{
		when(context.get(ComputeInputs.IN_WILDERNESS)).thenReturn(true);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(GearBonuses.symmetric(1.5), revenantWeaponGearBonus.compute(context));
	}

	@Test
	void givesCorrectBonusesForSceptre()
	{
		when(context.get(ComputeInputs.IN_WILDERNESS)).thenReturn(true);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals(GearBonuses.of(2, 1.25), revenantWeaponGearBonus.compute(context));
	}

}