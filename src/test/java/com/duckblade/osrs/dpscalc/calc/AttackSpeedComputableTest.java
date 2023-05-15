package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttackSpeedComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private AttackSpeedComputable attackSpeedComputable;

	@Test
	void isCorrectForHarmStaff()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(
			ItemStats.builder()
				.itemId(ItemID.HARMONISED_NIGHTMARE_STAFF)
				.weaponCategory(WeaponCategory.STAFF)
				.build()
		);
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.FIRE_SURGE,
			Spell.ICE_BARRAGE
		);

		assertEquals(4, attackSpeedComputable.compute(context));
		assertEquals(5, attackSpeedComputable.compute(context));
	}

	@Test
	void isCorrectForPoweredStaves()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.MAGIC),
			ofAttackType(AttackType.MAGIC),
			AttackStyle.MANUAL_CAST
		);
		when(context.get(weaponComputable)).thenReturn(
			ItemStats.builder()
				.weaponCategory(WeaponCategory.POWERED_STAFF)
				.speed(5)
				.build(),
			ItemStats.builder()
				.weaponCategory(WeaponCategory.POWERED_STAFF)
				.speed(3)
				.build(),
			ItemStats.builder()
				.weaponCategory(WeaponCategory.POWERED_STAFF)
				.speed(3)
				.build()
		);

		assertEquals(5, attackSpeedComputable.compute(context));
		assertEquals(3, attackSpeedComputable.compute(context));
		assertEquals(5, attackSpeedComputable.compute(context));
	}

	@Test
	void isCorrectForSalamanders()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.MAGIC),
			ofAttackType(AttackType.MAGIC),
			AttackStyle.MANUAL_CAST
		);
		when(context.get(weaponComputable)).thenReturn(
			ItemStats.builder()
				.weaponCategory(WeaponCategory.SALAMANDER)
				.speed(5)
				.build(),
			ItemStats.builder()
				.weaponCategory(WeaponCategory.SALAMANDER)
				.speed(3)
				.build(),
			ItemStats.builder()
				.weaponCategory(WeaponCategory.SALAMANDER)
				.speed(3)
				.build()
		);

		assertEquals(5, attackSpeedComputable.compute(context));
		assertEquals(3, attackSpeedComputable.compute(context));
		assertEquals(5, attackSpeedComputable.compute(context));
	}

	@Test
	void isCorrectForOtherMagic()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));
		when(context.get(weaponComputable)).thenReturn(
			ItemStats.builder()
				.weaponCategory(WeaponCategory.STAFF)
				.build(),
			ItemStats.builder()
				.weaponCategory(WeaponCategory.SCYTHE)
				.build()
		);

		assertEquals(5, attackSpeedComputable.compute(context));
		assertEquals(5, attackSpeedComputable.compute(context));
	}

	@Test
	void isCorrectForRapidRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.builder()
			.attackType(AttackType.RANGED)
			.combatStyle(CombatStyle.RAPID)
			.build());
		when(context.get(weaponComputable)).thenReturn(
			ItemStats.builder()
				.weaponCategory(WeaponCategory.BOW)
				.speed(5)
				.build(),
			ItemStats.builder()
				.weaponCategory(WeaponCategory.THROWN)
				.speed(3)
				.build()
		);

		assertEquals(4, attackSpeedComputable.compute(context));
		assertEquals(2, attackSpeedComputable.compute(context));
	}

	@Test
	void isCorrectForOtherRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(AttackStyle.builder()
			.attackType(AttackType.RANGED)
			.combatStyle(CombatStyle.ACCURATE)
			.build());
		when(context.get(weaponComputable)).thenReturn(
			ItemStats.builder()
				.weaponCategory(WeaponCategory.BOW)
				.speed(5)
				.build()
		);

		assertEquals(5, attackSpeedComputable.compute(context));
	}

	@Test
	void isCorrectForMelee()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(weaponComputable)).thenReturn(
			ItemStats.builder()
				.weaponCategory(WeaponCategory.STAFF)
				.speed(4)
				.build()
		);

		assertEquals(4, attackSpeedComputable.compute(context));
	}

}