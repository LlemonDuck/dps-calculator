package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.KALPHITE;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
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
class KerisGearBonusTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private KerisGearBonus kerisGearBonus;

	@Test
	void isApplicableWhenUsingKeris()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.KERIS),
			ofItemId(ItemID.KERISP),
			ofItemId(ItemID.KERIS_PARTISAN)
		);

		assertTrue(kerisGearBonus.isApplicable(context));
		assertTrue(kerisGearBonus.isApplicable(context));
		assertTrue(kerisGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingKeris()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(ItemStats.EMPTY);

		assertFalse(kerisGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableForMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.KERIS_PARTISAN));

		assertFalse(kerisGearBonus.isApplicable(context));
	}

	@Test
	void givesNoBonusForNonKalphite()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(DefenderAttributes.EMPTY);

		assertEquals(GearBonuses.EMPTY, kerisGearBonus.compute(context));
	}

	@Test
	void givesBonusForKalphite()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(KALPHITE);

		assertEquals(GearBonuses.of(1, 1.33), kerisGearBonus.compute(context));
	}

}