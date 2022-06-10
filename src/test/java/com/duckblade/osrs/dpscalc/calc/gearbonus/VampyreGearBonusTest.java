package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil;
import net.runelite.api.ItemID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.VAMPYRE;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VampyreGearBonusTest
{
	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private VampyreGearBonus vampyreGearBonus;

	@Test
	void isApplicableWhenUsingVampyrebaneAgainstVampyres()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.VAMPYRE);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));
		when(context.get(weaponComputable)).thenReturn(
				ofItemId(ItemID.IVANDIS_FLAIL),
				ofItemId(ItemID.BLISTERWOOD_SICKLE),
				ofItemId(ItemID.BLISTERWOOD_FLAIL)
		);

		assertTrue(vampyreGearBonus.isApplicable(context));
		assertTrue(vampyreGearBonus.isApplicable(context));
		assertTrue(vampyreGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableForMeleeWithWrongWeapon()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SCYTHE_OF_VITUR));

		assertFalse(vampyreGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingMelee()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.BLISTERWOOD_FLAIL));
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributesUtil.VAMPYRE);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(vampyreGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableAgainstNonVampyres()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.BLISTERWOOD_FLAIL));
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertFalse(vampyreGearBonus.isApplicable(context));
	}

	@Test
	void providesAppropriateBonuses()
	{
		when(context.get(weaponComputable)).thenReturn(
				ofItemId(ItemID.IVANDIS_FLAIL),
				ofItemId(ItemID.BLISTERWOOD_SICKLE),
				ofItemId(ItemID.BLISTERWOOD_FLAIL)
		);

		assertEquals(GearBonuses.of(1, 1.20), vampyreGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.05, 1.15), vampyreGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.05, 1.25), vampyreGearBonus.compute(context));
	}

}