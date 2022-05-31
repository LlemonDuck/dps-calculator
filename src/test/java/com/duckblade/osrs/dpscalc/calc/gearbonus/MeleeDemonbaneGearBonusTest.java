package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.DEMON;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
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
class MeleeDemonbaneGearBonusTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private MeleeDemonbaneGearBonus meleeDemonbaneGearBonus;

	@Test
	void isApplicableWhenUsingDemonbaneWeapons()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.SILVERLIGHT),
			ofItemId(ItemID.SILVERLIGHT_6745),
			ofItemId(ItemID.DARKLIGHT),
			ofItemId(ItemID.ARCLIGHT)
		);

		assertTrue(meleeDemonbaneGearBonus.isApplicable(context));
		assertTrue(meleeDemonbaneGearBonus.isApplicable(context));
		assertTrue(meleeDemonbaneGearBonus.isApplicable(context));
		assertTrue(meleeDemonbaneGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutDemonbaneWeapons()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SCYTHE_OF_VITUR));

		assertFalse(meleeDemonbaneGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableForMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SILVERLIGHT));

		assertFalse(meleeDemonbaneGearBonus.isApplicable(context));
	}

	@Test
	void warnsAgainstNonDemons()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertEquals(GearBonuses.EMPTY, meleeDemonbaneGearBonus.compute(context));
		verify(context, times(1)).warn("Using demonbane weaponry against non-demons provides no bonuses.");
	}

	@Test
	void providesCorrectBonusForLevel1Demonbane()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DEMON);
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.SILVERLIGHT),
			ofItemId(ItemID.SILVERLIGHT_6745),
			ofItemId(ItemID.DARKLIGHT)
		);

		assertEquals(GearBonuses.of(1, 1.6), meleeDemonbaneGearBonus.compute(context));
		assertEquals(GearBonuses.of(1, 1.6), meleeDemonbaneGearBonus.compute(context));
		assertEquals(GearBonuses.of(1, 1.6), meleeDemonbaneGearBonus.compute(context));
	}

	@Test
	void providesCorrectBonusForLevel2Demonbane()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DEMON);
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.ARCLIGHT));

		assertEquals(GearBonuses.symmetric(1.7), meleeDemonbaneGearBonus.compute(context));
	}

}