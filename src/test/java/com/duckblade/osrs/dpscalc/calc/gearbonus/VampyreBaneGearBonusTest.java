package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.VAMPYRE1;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.VAMPYRE2;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.VAMPYRE3;
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
class VampyreBaneGearBonusTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private VampyreBaneGearBonus vampyreBaneGearBonus;

	@Test
	void isApplicableWhenUsingVampyrebaneAgainstVampyres()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.IVANDIS_FLAIL),
			ofItemId(ItemID.BLISTERWOOD_SICKLE),
			ofItemId(ItemID.BLISTERWOOD_FLAIL)
		);
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			VAMPYRE1,
			VAMPYRE2,
			VAMPYRE3
		);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));

		assertTrue(vampyreBaneGearBonus.isApplicable(context));
		assertTrue(vampyreBaneGearBonus.isApplicable(context));
		assertTrue(vampyreBaneGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingVampyrebane()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SCYTHE_OF_VITUR));

		assertFalse(vampyreBaneGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableAgainstNonVampyres()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.BLISTERWOOD_FLAIL));
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertFalse(vampyreBaneGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingMelee()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.BLISTERWOOD_FLAIL));
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(VAMPYRE1);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(vampyreBaneGearBonus.isApplicable(context));
	}

	@Test
	void providesAppropriateBonuses()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.IVANDIS_FLAIL),
			ofItemId(ItemID.BLISTERWOOD_SICKLE),
			ofItemId(ItemID.BLISTERWOOD_FLAIL)
		);

		assertEquals(GearBonuses.of(1, 1.20), vampyreBaneGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.05, 1.15), vampyreBaneGearBonus.compute(context));
		assertEquals(GearBonuses.of(1.05, 1.25), vampyreBaneGearBonus.compute(context));
	}

}