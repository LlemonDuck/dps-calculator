package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofWeaponCategory;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MagicSalamanderMaxHitComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private MagicSalamanderMaxHitComputable magicSalamanderMaxHitComputable;

	@Test
	void isApplicableWhenUsingSalamandersForMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofWeaponCategory(WeaponCategory.SALAMANDER));

		assertTrue(magicSalamanderMaxHitComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertFalse(magicSalamanderMaxHitComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenManualCasting()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.MANUAL_CAST);

		assertFalse(magicSalamanderMaxHitComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingSalamanders()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(ofWeaponCategory(WeaponCategory.STAFF));

		assertFalse(magicSalamanderMaxHitComputable.isApplicable(context));
	}

	@Test
	void computesMaxHitForSalamanders()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.SWAMP_LIZARD),
			ofItemId(ItemID.ORANGE_SALAMANDER),
			ofItemId(ItemID.RED_SALAMANDER),
			ofItemId(ItemID.BLACK_SALAMANDER)
		);
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(ofSkill(Skill.MAGIC, 99));

		assertEquals(99 * (56 + 64) / 640, magicSalamanderMaxHitComputable.compute(context));
		assertEquals(99 * (59 + 64) / 640, magicSalamanderMaxHitComputable.compute(context));
		assertEquals(99 * (77 + 64) / 640, magicSalamanderMaxHitComputable.compute(context));
		assertEquals(99 * (92 + 64) / 640, magicSalamanderMaxHitComputable.compute(context));
	}

	@Test
	void throwsIllegalArgumentForNonSalamanders()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SCYTHE_OF_VITUR));

		assertThrows(IllegalArgumentException.class, () -> magicSalamanderMaxHitComputable.compute(context));
	}

}