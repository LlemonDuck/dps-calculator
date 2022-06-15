package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KarilsDptComputableTest
{

	@Mock
	private BaseHitDptComputable baseHitDptComputable;

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private HitChanceComputable hitChanceComputable;

	@Mock
	private MaxHitLimitComputable maxHitLimitComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private KarilsDptComputable karilsDptComputable;

	@Test
	void isNotApplicableWhenCastingSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(karilsDptComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWithoutKarilsAndDamned()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.KARILS_CROSSBOW,
				EquipmentInventorySlot.HEAD, ItemID.KARILS_COIF,
				EquipmentInventorySlot.BODY, ItemID.KARILS_LEATHERTOP,
				EquipmentInventorySlot.LEGS, ItemID.KARILS_LEATHERSKIRT
			),
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.KARILS_CROSSBOW,
				EquipmentInventorySlot.HEAD, ItemID.KARILS_COIF_0,
				EquipmentInventorySlot.BODY, ItemID.KARILS_LEATHERTOP,
				EquipmentInventorySlot.LEGS, ItemID.KARILS_LEATHERSKIRT,
				EquipmentInventorySlot.AMULET, ItemID.AMULET_OF_THE_DAMNED_FULL
			),
			Collections.emptyMap()
		);

		assertFalse(karilsDptComputable.isApplicable(context));
		verify(context, times(1)).warn("Karil's equipment only provides a beneficial set effect with the Amulet of the damned.");
		assertFalse(karilsDptComputable.isApplicable(context));
		assertFalse(karilsDptComputable.isApplicable(context));
	}

	@Test
	void isApplicableWithKarilsAndDamned()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.KARILS_CROSSBOW_75,
				EquipmentInventorySlot.HEAD, ItemID.KARILS_COIF_50,
				EquipmentInventorySlot.BODY, ItemID.KARILS_LEATHERTOP_25,
				EquipmentInventorySlot.LEGS, ItemID.KARILS_LEATHERSKIRT,
				EquipmentInventorySlot.AMULET, ItemID.AMULET_OF_THE_DAMNED_FULL
			)
		);

		assertTrue(karilsDptComputable.isApplicable(context));
	}

	@Test
	void addsSecondHitToDps()
	{
		when(context.get(baseHitDptComputable)).thenReturn(4.0);
		when(context.get(BaseMaxHitComputable.PRE_LIMIT_MAX_HIT)).thenReturn(50);
		when(context.get(hitChanceComputable)).thenReturn(0.5);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenAnswer(i -> i.getArgument(0));

		double karilsEffectDps = BaseHitDptComputable.byComponents(0.5, 75, 5);
		assertEquals(0.75 * 4.0 + 0.25 * karilsEffectDps, karilsDptComputable.compute(context));
		verify(context).put(KarilsDptComputable.KARILS_MAX_HIT, 75);
	}

	@Test
	void respectsMaxHitLimiters()
	{
		when(context.get(baseHitDptComputable)).thenReturn(4.0);
		when(context.get(BaseMaxHitComputable.PRE_LIMIT_MAX_HIT)).thenReturn(50);
		when(context.get(hitChanceComputable)).thenReturn(0.5);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(maxHitLimitComputable.coerce(anyInt(), eq(context))).thenReturn(5);

		double karilsEffectDps = BaseHitDptComputable.byComponents(0.5, 5, 5);
		assertEquals(0.75 * 4.0 + 0.25 * karilsEffectDps, karilsDptComputable.compute(context));
		verify(context).put(KarilsDptComputable.KARILS_MAX_HIT, 5);
	}

}