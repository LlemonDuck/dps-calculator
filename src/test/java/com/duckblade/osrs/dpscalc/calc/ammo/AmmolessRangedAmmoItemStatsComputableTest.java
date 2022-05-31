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
class AmmolessRangedAmmoItemStatsComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private AmmolessRangedAmmoItemStatsComputable ammolessRangedAmmoItemStatsComputable;

	@Test
	void isApplicableWhenUsingCrystalBows()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.CRYSTAL_BOW),
			ofItemId(ItemID.BOW_OF_FAERDHINEN),
			ofItemId(ItemID.BOW_OF_FAERDHINEN_C_25869)
		);

		assertTrue(ammolessRangedAmmoItemStatsComputable.isApplicable(context));
		assertTrue(ammolessRangedAmmoItemStatsComputable.isApplicable(context));
		assertTrue(ammolessRangedAmmoItemStatsComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingRanged()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertFalse(ammolessRangedAmmoItemStatsComputable.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenUsingRegularBow()
	{
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.MAGIC_SHORTBOW));

		assertFalse(ammolessRangedAmmoItemStatsComputable.isApplicable(context));
	}

	@Test
	void zerosRangedStatsFromAmmoSlot()
	{
		when(context.get(ComputeInputs.ATTACKER_ITEMS)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.AMMO, ItemStats.builder()
				.strengthRanged(15)
				.accuracyRanged(10)
				.prayer(5)
				.build()
		));

		ItemStats expected = ItemStats.builder()
			.prayer(5)
			.build();
		assertEquals(expected, ammolessRangedAmmoItemStatsComputable.compute(context));
	}

}