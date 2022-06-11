package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Tier3VampyreImmunitiesTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private Tier3VampyreImmunities tier3VampyreImmunities;

	@Test
	void isApplicableAgainstT3Vampyres()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(VAMPYRE3);

		assertTrue(tier3VampyreImmunities.isApplicable(context));
	}

	@Test
	void isNotApplicableAgainstNonT3Vampyres()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			VAMPYRE1,
			VAMPYRE2,
			DefenderAttributes.EMPTY
		);

		assertFalse(tier3VampyreImmunities.isApplicable(context));
		assertFalse(tier3VampyreImmunities.isApplicable(context));
		assertFalse(tier3VampyreImmunities.isApplicable(context));
	}

	@Test
	void limitsMaxHitForNonMelee()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.MAGIC),
			ofAttackType(AttackType.RANGED)
		);

		assertEquals(0, tier3VampyreImmunities.compute(context));
		assertEquals(0, tier3VampyreImmunities.compute(context));
		verify(context, times(2)).warn("Tier 3 vampyres can only be damaged by Blisterwood weapons.");
	}

	@Test
	void limitsMaxHitForMeleeWithoutVampyrebane()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.SLASH),
			ofAttackType(AttackType.CRUSH)
		);
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.GHRAZI_RAPIER),
			ofItemId(ItemID.SCYTHE_OF_VITUR),
			ofItemId(ItemID.ABYSSAL_BLUDGEON)
		);

		assertEquals(0, tier3VampyreImmunities.compute(context));
		assertEquals(0, tier3VampyreImmunities.compute(context));
		assertEquals(0, tier3VampyreImmunities.compute(context));
		verify(context, times(3)).warn("Tier 3 vampyres can only be damaged by Blisterwood weapons.");
	}

	@Test
	void doesNotLimitMaxHitForVampyrebane()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.BLISTERWOOD_FLAIL),
			ofItemId(ItemID.BLISTERWOOD_SICKLE),
			ofItemId(ItemID.IVANDIS_FLAIL)
		);

		assertEquals(Integer.MAX_VALUE, tier3VampyreImmunities.compute(context));
		assertEquals(Integer.MAX_VALUE, tier3VampyreImmunities.compute(context));
		assertEquals(Integer.MAX_VALUE, tier3VampyreImmunities.compute(context));
	}
}