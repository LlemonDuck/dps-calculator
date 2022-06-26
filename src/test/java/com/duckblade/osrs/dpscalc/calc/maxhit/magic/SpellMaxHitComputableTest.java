package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofWeaponCategory;
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
class SpellMaxHitComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private SpellcastingMaxHitBonusComputable spellcastingMaxHitBonusComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private SpellMaxHitComputable spellMaxHitComputable;

	@Test
	void isApplicableWhenUsingSpellbookSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofWeaponCategory(WeaponCategory.STAFF));

		assertTrue(spellMaxHitComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertFalse(spellMaxHitComputable.isApplicable(context));
	}

	@Test
	void isAlwaysApplicableWhenManualCasting()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.MANUAL_CAST);

		assertTrue(spellMaxHitComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenUsingStavesOrSalamanders()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(
			ofWeaponCategory(WeaponCategory.POWERED_STAFF),
			ofWeaponCategory(WeaponCategory.SALAMANDER)
		);

		assertFalse(spellMaxHitComputable.isApplicable(context));
		assertFalse(spellMaxHitComputable.isApplicable(context));
	}

	@Test
	void returnsMaxHitWithBonus()
	{
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.BLOOD_BARRAGE,
			Spell.FIRE_SURGE,
			Spell.DARK_DEMONBANE
		);
		when(context.get(spellcastingMaxHitBonusComputable)).thenReturn(
			1,
			2,
			3
		);

		assertEquals(Spell.BLOOD_BARRAGE.getBaseMaxHit() + 1, spellMaxHitComputable.compute(context));
		assertEquals(Spell.FIRE_SURGE.getBaseMaxHit() + 2, spellMaxHitComputable.compute(context));
		assertEquals(Spell.DARK_DEMONBANE.getBaseMaxHit() + 3, spellMaxHitComputable.compute(context));
	}

}