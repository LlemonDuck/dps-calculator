package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.attack.AttackRollComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.DefenseRollComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FangHitChanceComputableTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private AttackRollComputable attackRollComputable;

	@Mock
	private DefenseRollComputable defenseRollComputable;

	@Mock
	private ToaArenaComputable toaArenaComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private FangHitChanceComputable fangHitChanceComputable;

	@Test
	void isApplicableWhenUsingFang()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.OSMUMTENS_FANG),
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.OSMUMTENS_FANG_OR)
		);

		assertTrue(fangHitChanceComputable.isApplicable(context));
		assertTrue(fangHitChanceComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingFang()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
			ofAttackType(AttackType.MAGIC),
			ofAttackType(AttackType.STAB),
			ofAttackType(AttackType.CRUSH)
		);
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.SCYTHE_OF_VITUR),
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, -1)
		);

		assertFalse(fangHitChanceComputable.isApplicable(context));
		assertFalse(fangHitChanceComputable.isApplicable(context));
		assertFalse(fangHitChanceComputable.isApplicable(context));
	}

	@Test
	void appliesEffectDifferentlyInsideToa()
	{
		when(context.get(toaArenaComputable)).thenReturn(
			ToaArena.FIGHTING_OUTSIDE_TOA,
			ToaArena.FIGHTING_PATH_NPC,
			ToaArena.FIGHTING_WARDENS
		);
		when(context.get(attackRollComputable)).thenReturn(1000);
		when(context.get(defenseRollComputable)).thenReturn(1000);

		assertEquals(0.67, fangHitChanceComputable.compute(context), 0.005);
		assertEquals(0.75, fangHitChanceComputable.compute(context), 0.005);
		assertEquals(0.75, fangHitChanceComputable.compute(context), 0.005);
	}

	@Test
	void appliesDoubleAttackRollOutsideToA()
	{
		when(context.get(toaArenaComputable)).thenReturn(ToaArena.FIGHTING_OUTSIDE_TOA);
		when(context.get(attackRollComputable)).thenReturn(1000);
		when(context.get(defenseRollComputable)).thenReturn(0, 1000, 2000);

		assertEquals(1.0, fangHitChanceComputable.compute(context), 0.005);
		assertEquals(0.67, fangHitChanceComputable.compute(context), 0.005);
		assertEquals(0.33, fangHitChanceComputable.compute(context), 0.005);
	}

	@Test
	void appliesDoubleAttackAndDefRollInsideToA()
	{
		when(context.get(toaArenaComputable)).thenReturn(ToaArena.FIGHTING_PATH_NPC);
		when(context.get(attackRollComputable)).thenReturn(1000);
		when(context.get(defenseRollComputable)).thenReturn(0, 1000, 2000);

		assertEquals(1.0, fangHitChanceComputable.compute(context), 0.005);
		assertEquals(0.75, fangHitChanceComputable.compute(context), 0.005);
		assertEquals(0.44, fangHitChanceComputable.compute(context), 0.005);
	}
}
