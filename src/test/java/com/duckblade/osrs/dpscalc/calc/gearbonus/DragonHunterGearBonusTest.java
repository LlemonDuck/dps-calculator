package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.DRAGON;
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
class DragonHunterGearBonusTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private DragonHunterGearBonus dragonHunterGearBonus;

	@Test
	void isApplicableWhenUsingDragonHunterLance()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.DRAGON_HUNTER_LANCE));

		assertTrue(dragonHunterGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableForMeleeWithoutLance()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SCYTHE_OF_VITUR));

		assertFalse(dragonHunterGearBonus.isApplicable(context));
	}

	@Test
	void isApplicableWhenUsingDragonHunterCrossbow()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.DRAGON_HUNTER_CROSSBOW),
			ofItemId(ItemID.DRAGON_HUNTER_CROSSBOW_B),
			ofItemId(ItemID.DRAGON_HUNTER_CROSSBOW_T)
		);

		assertTrue(dragonHunterGearBonus.isApplicable(context));
		assertTrue(dragonHunterGearBonus.isApplicable(context));
		assertTrue(dragonHunterGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenUsingRangedWithoutCrossbow()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.MAGIC_SHORTBOW)
		);

		assertFalse(dragonHunterGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableForMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.DRAGON_HUNTER_LANCE));

		assertFalse(dragonHunterGearBonus.isApplicable(context));
	}

	@Test
	void warnsForNonDragons()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertEquals(GearBonuses.EMPTY, dragonHunterGearBonus.compute(context));
		verify(context, times(1)).warn("Using dragon hunter weaponry against non-dragons provides no bonuses.");
	}

	@Test
	void providesCorrectBonusForLance()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DRAGON);
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.DRAGON_HUNTER_LANCE));

		assertEquals(GearBonuses.symmetric(1.2), dragonHunterGearBonus.compute(context));
	}

	@Test
	void providesCorrectBonusForCrossbow()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DRAGON);
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.DRAGON_HUNTER_CROSSBOW));

		assertEquals(GearBonuses.of(1.3, 1.25), dragonHunterGearBonus.compute(context));
	}

}