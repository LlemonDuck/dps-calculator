package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.VoidLevel;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.runelite.api.EquipmentInventorySlot;
import static net.runelite.api.ItemID.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoidLevelComputableTest
{

	private static Map<EquipmentInventorySlot, Integer> equipmentMap(int helm, int body, int legs, int gloves)
	{
		return ImmutableMap.of(
			EquipmentInventorySlot.HEAD, helm,
			EquipmentInventorySlot.BODY, body,
			EquipmentInventorySlot.LEGS, legs,
			EquipmentInventorySlot.GLOVES, gloves
		);
	}

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private VoidLevelComputable voidLevelComputable;

	@Test
	void recognizesFullVoid()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.RANGED),
			ofAttackType(AttackType.MAGIC)
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			equipmentMap(VOID_MELEE_HELM, VOID_KNIGHT_TOP, VOID_KNIGHT_ROBE, VOID_KNIGHT_GLOVES),
			equipmentMap(VOID_RANGER_HELM_OR, VOID_KNIGHT_TOP, ELITE_VOID_ROBE, VOID_KNIGHT_GLOVES_L),
			equipmentMap(VOID_MAGE_HELM_L, ELITE_VOID_TOP, VOID_KNIGHT_ROBE_OR, VOID_KNIGHT_GLOVES)
		);

		assertEquals(VoidLevel.REGULAR, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.REGULAR, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.REGULAR, voidLevelComputable.compute(context));
	}

	@Test
	void recognizesEliteVoid()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.RANGED),
			ofAttackType(AttackType.MAGIC)
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			equipmentMap(VOID_MELEE_HELM_OR, ELITE_VOID_TOP, ELITE_VOID_ROBE_L, VOID_KNIGHT_GLOVES),
			equipmentMap(VOID_RANGER_HELM_L, ELITE_VOID_TOP_L, ELITE_VOID_ROBE_OR, VOID_KNIGHT_GLOVES_L),
			equipmentMap(VOID_MAGE_HELM, ELITE_VOID_TOP_OR, ELITE_VOID_ROBE, VOID_KNIGHT_GLOVES)
		);

		assertEquals(VoidLevel.ELITE, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.ELITE, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.ELITE, voidLevelComputable.compute(context));
	}

	@Test
	void returnsNoneWithNoVoid()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			equipmentMap(TORVA_FULL_HELM, TORVA_PLATEBODY, TORVA_PLATELEGS, FEROCIOUS_GLOVES)
		);

		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
	}

	@Test
	void warnsWhenMissingPartsOfVoid()
	{
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			equipmentMap(ARCHER_HELM, VOID_KNIGHT_TOP, VOID_KNIGHT_ROBE, VOID_KNIGHT_GLOVES),
			equipmentMap(VOID_MELEE_HELM, TORVA_PLATEBODY, TORVA_PLATELEGS, FEROCIOUS_GLOVES),
			equipmentMap(VOID_RANGER_HELM, ARMADYL_PLATEBODY, ARMADYL_PLATELEGS, VOID_KNIGHT_GLOVES),
			equipmentMap(VOID_MAGE_HELM, VOID_KNIGHT_TOP, VOID_KNIGHT_ROBE, TORMENTED_BRACELET)
		);

		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
		verify(context, times(4)).warn("Wearing incomplete void equipment provides no offensive bonuses.");
	}

	@Test
	void warnsWhenUsingWrongAttackStyle()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.RANGED),
			ofAttackType(AttackType.MAGIC),
			ofAttackType(AttackType.STAB)
			);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			equipmentMap(VOID_MELEE_HELM, VOID_KNIGHT_TOP, VOID_KNIGHT_ROBE, VOID_KNIGHT_GLOVES),
			equipmentMap(VOID_RANGER_HELM, VOID_KNIGHT_TOP, VOID_KNIGHT_ROBE, VOID_KNIGHT_GLOVES),
			equipmentMap(VOID_MAGE_HELM, VOID_KNIGHT_TOP, VOID_KNIGHT_ROBE, VOID_KNIGHT_GLOVES)
		);

		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
		assertEquals(VoidLevel.NONE, voidLevelComputable.compute(context));
		verify(context, times(3)).warn("Void helm does not matach attack style.");
	}

}