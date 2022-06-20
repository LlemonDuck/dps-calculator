package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.PreLimitBaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DharoksDptComputableTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private HitChanceComputable hitChanceComputable;

	@Mock
	private MaxHitLimitComputable maxHitLimitComputable;

	@Mock
	private PreLimitBaseMaxHitComputable preLimitBaseMaxHitComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private DharoksDptComputable dharoksDptComputable;

	@Test
	void isNotApplicableWhenCastingSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(dharoksDptComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutDharoks()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.DHAROKS_GREATAXE,
				EquipmentInventorySlot.HEAD, ItemID.DHAROKS_HELM,
				EquipmentInventorySlot.BODY, ItemID.DHAROKS_PLATEBODY_0,
				EquipmentInventorySlot.LEGS, ItemID.DHAROKS_PLATELEGS
			),
			Collections.emptyMap()
		);

		assertFalse(dharoksDptComputable.isApplicable(context));
		assertFalse(dharoksDptComputable.isApplicable(context));
	}

	@Test
	void isApplicableWithDharoks()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.DHAROKS_GREATAXE_75,
				EquipmentInventorySlot.HEAD, ItemID.DHAROKS_HELM_25,
				EquipmentInventorySlot.BODY, ItemID.DHAROKS_PLATEBODY_100,
				EquipmentInventorySlot.LEGS, ItemID.DHAROKS_PLATELEGS
			)
		);

		assertTrue(dharoksDptComputable.isApplicable(context));
	}

	@Test
	void increasesMaxHitByAppropriateAmountAt1HpWith99Max()
	{
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(100);
		when(context.get(hitChanceComputable)).thenReturn(1.0);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));

		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			Skills.builder()
				.level(Skill.HITPOINTS, 99)
				.boost(Skill.HITPOINTS, -98)
				.build()
		);
		double dharokMod = 1.9702;

		assertEquals(BaseHitDptComputable.byComponents(1.0, (int) (100 * dharokMod), 5), dharoksDptComputable.compute(context));
		verify(context).put(DharoksDptComputable.DHAROKS_MAX_HIT, (int) (100 * dharokMod));
	}

	@Test
	void increasesMaxHitByAppropriateAmountAt99HpWith99Max()
	{
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(100);
		when(context.get(hitChanceComputable)).thenReturn(1.0);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));

		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(ofSkill(Skill.HITPOINTS, 99));
		double dharokMod = 1.0;

		assertEquals(BaseHitDptComputable.byComponents(1.0, (int) (100 * dharokMod), 5), dharoksDptComputable.compute(context));
		verify(context).put(DharoksDptComputable.DHAROKS_MAX_HIT, (int) (100 * dharokMod));
	}

	@Test
	void increasesMaxHitByAppropriateAmountAt25HpWith50Max()
	{
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(100);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));
		when(context.get(hitChanceComputable)).thenReturn(1.0);
		when(context.get(attackSpeedComputable)).thenReturn(5);

		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			Skills.builder()
				.level(Skill.HITPOINTS, 50)
				.boost(Skill.HITPOINTS, -25)
				.build()
		);
		double dharokMod = 1.125;

		assertEquals(BaseHitDptComputable.byComponents(1.0, (int) (100 * dharokMod), 5), dharoksDptComputable.compute(context));
		verify(context).put(DharoksDptComputable.DHAROKS_MAX_HIT, (int) (100 * dharokMod));
	}

	@Test
	void respectsMaxHitLimiters()
	{
		when(context.get(preLimitBaseMaxHitComputable)).thenReturn(100);
		when(context.get(hitChanceComputable)).thenReturn(1.0);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenReturn(5);

		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			Skills.builder()
				.level(Skill.HITPOINTS, 99)
				.boost(Skill.HITPOINTS, -98)
				.build()
		);

		assertEquals(BaseHitDptComputable.byComponents(1.0, 5, 5), dharoksDptComputable.compute(context));
		verify(context).put(DharoksDptComputable.DHAROKS_MAX_HIT, 5);
	}

}