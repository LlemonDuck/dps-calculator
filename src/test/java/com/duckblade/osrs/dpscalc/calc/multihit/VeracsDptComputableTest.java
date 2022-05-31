package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.MaxHitComputable;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VeracsDptComputableTest
{

	@Mock
	private BaseHitDptComputable baseHitDptComputable;

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private MaxHitComputable maxHitComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private VeracsDptComputable veracsDptComputable;

	@Test
	void isNotApplicableWhenCastingSpells()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(veracsDptComputable.isApplicable(context));
	}

	@Test
	void isApplicableWhenUsingVeracs()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.WEAPON, ItemID.VERACS_FLAIL,
			EquipmentInventorySlot.HEAD, ItemID.VERACS_HELM_50,
			EquipmentInventorySlot.BODY, ItemID.VERACS_BRASSARD_100,
			EquipmentInventorySlot.LEGS, ItemID.VERACS_PLATESKIRT_25
		));

		assertTrue(veracsDptComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingVeracs()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.CRUSH));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.VERACS_FLAIL_0,
				EquipmentInventorySlot.HEAD, ItemID.VERACS_HELM_50,
				EquipmentInventorySlot.BODY, ItemID.VERACS_BRASSARD_100,
				EquipmentInventorySlot.LEGS, ItemID.VERACS_PLATESKIRT_25
			),
			Collections.emptyMap()
		);

		assertFalse(veracsDptComputable.isApplicable(context));
		assertFalse(veracsDptComputable.isApplicable(context));
	}

	@Test
	void appliesSpecialEffectAppropriately()
	{
		when(context.get(baseHitDptComputable)).thenReturn(4.0);
		when(context.get(maxHitComputable)).thenReturn(16);
		when(context.get(attackSpeedComputable)).thenReturn(5);

		// byComponents takes avg hit by max / 2, and veracs gives +1 to hit = calc with +2 to max
		double expectedEffectDps = BaseHitDptComputable.byComponents(1.0, 18, 5);
		assertEquals(0.75 * 4.0 + 0.25 * expectedEffectDps, veracsDptComputable.compute(context));
	}

}