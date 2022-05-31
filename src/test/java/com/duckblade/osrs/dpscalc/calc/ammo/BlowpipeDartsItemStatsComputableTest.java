package com.duckblade.osrs.dpscalc.calc.ammo;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import com.google.common.collect.ImmutableMap;
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
class BlowpipeDartsItemStatsComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private BlowpipeDartsItemStatsComputable blowpipeDartsItemStatsComputable;

	@Test
	void isApplicableWhenUsingBlowpipe()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.TOXIC_BLOWPIPE));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertTrue(blowpipeDartsItemStatsComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(blowpipeDartsItemStatsComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingBlowpipe()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SCYTHE_OF_VITUR));

		assertFalse(blowpipeDartsItemStatsComputable.isApplicable(context));
	}

	@Test
	void mergesAmmoSlotAndBlowpipeDartsAppropriately()
	{
		when(context.get(ComputeInputs.ATTACKER_EQUIPMENT)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.AMMO, ItemStats.builder()
				.prayer(3)
				.build()
		));
		when(context.get(ComputeInputs.BLOWPIPE_DARTS)).thenReturn(
			ItemStats.builder()
				.strengthRanged(1)
				.accuracyRanged(2)
				.build()
		);

		ItemStats expected = ItemStats.builder()
			.strengthRanged(1)
			.accuracyRanged(2)
			.prayer(3)
			.build();
		assertEquals(expected, blowpipeDartsItemStatsComputable.compute(context));
	}

}