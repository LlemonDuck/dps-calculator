package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.BaseHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimitComputable;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import java.util.Collections;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OsmumtensFangDptComputableTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private BaseMaxHitComputable baseMaxHitComputable;

	@Mock
	private HitChanceComputable hitChanceComputable;

	@Mock
	private AttackSpeedComputable attackSpeedComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private OsmumtensFangDptComputable osmumtensFangDptComputable;

	@Test
	void isApplicableWhenUsingFang()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.OSMUMTENS_FANG),
			Collections.singletonMap(EquipmentInventorySlot.WEAPON, ItemID.OSMUMTENS_FANG_OR)
		);

		assertTrue(osmumtensFangDptComputable.isApplicable(context));
		assertTrue(osmumtensFangDptComputable.isApplicable(context));
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

		assertFalse(osmumtensFangDptComputable.isApplicable(context));
		assertFalse(osmumtensFangDptComputable.isApplicable(context));
		assertFalse(osmumtensFangDptComputable.isApplicable(context));
	}

	@Test
	void appliesAccuracyDoubleRollEffect()
	{
		when(context.get(baseMaxHitComputable)).thenReturn(15);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(context.get(MaxHitLimitComputable.LIMIT_APPLIED)).thenReturn(false);
		when(context.get(hitChanceComputable)).thenReturn(
			1.0,
			0.5,
			0.25
		);

		assertEquals(BaseHitDptComputable.byComponents(1.0, 15, 5), osmumtensFangDptComputable.compute(context));
		assertEquals(BaseHitDptComputable.byComponents(0.75, 15, 5), osmumtensFangDptComputable.compute(context));
		assertEquals(BaseHitDptComputable.byComponents(0.4375, 15, 5), osmumtensFangDptComputable.compute(context));
		verify(context, never()).warn(anyString());
	}

	@Test
	void warnsWhenMaxHitLimitInEffect()
	{
		when(context.get(baseMaxHitComputable)).thenReturn(15);
		when(context.get(attackSpeedComputable)).thenReturn(5);
		when(context.get(hitChanceComputable)).thenReturn(1.0);
		when(context.get(MaxHitLimitComputable.LIMIT_APPLIED)).thenReturn(true);

		assertEquals(BaseHitDptComputable.byComponents(1.0, 15, 5), osmumtensFangDptComputable.compute(context));
		verify(context, times(1)).warn("Max hit may be inaccurate due to conflicting effects of a max hit limiter and fang max hit clamping.");
	}

}