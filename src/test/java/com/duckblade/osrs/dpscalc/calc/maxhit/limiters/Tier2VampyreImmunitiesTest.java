package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.VAMPYRE1;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.VAMPYRE2;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.VAMPYRE3;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Tier2VampyreImmunitiesTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private Tier2VampyreImmunities tier2VampyreImmunities;

	@Test
	void isApplicableForTier2Vampyres()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(VAMPYRE2);

		assertTrue(tier2VampyreImmunities.isApplicable(context));
	}

	@Test
	void isNotApplicableForNonTier2Vampyres()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(
			VAMPYRE1,
			VAMPYRE3,
			DefenderAttributes.EMPTY
		);

		assertFalse(tier2VampyreImmunities.isApplicable(context));
		assertFalse(tier2VampyreImmunities.isApplicable(context));
		assertFalse(tier2VampyreImmunities.isApplicable(context));
	}

	@Test
	void limitsMaxHitForMagicWithoutEfaritay()
	{
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(EquipmentInventorySlot.WEAPON, ItemID.BLISTERWOOD_FLAIL),
			Collections.emptyMap()
		);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals(0, tier2VampyreImmunities.compute(context));
		verify(context, times(1)).warn("Tier 2 vampyres cannot be hit by magic without Efaritay's aid.");
	}

	@Test
	void limitsMaxHitForMagicWithEfaritay()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals(10, tier2VampyreImmunities.compute(context));
		verify(context, times(1)).warn("Efaritay's aid limits max hit to 10.");
	}

	@Test
	void limitsMaxHitForRangedWithoutEfaritayAndSilverBolts()
	{
		when(context.get(equipmentItemIdsComputable)).thenAnswer(i -> Collections.emptyMap());
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(0, tier2VampyreImmunities.compute(context));
		verify(context, times(1)).warn("Tier 2 vampyres cannot be hit by ranged without Silver bolts.");
	}

	@Test
	void limitsMaxHitForRangedWithEfaritay()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(10, tier2VampyreImmunities.compute(context));
		verify(context, times(1)).warn("Efaritay's aid limits max hit to 10.");
	}

	@Test
	void doesNotlimitMaxHitForRangedWithSilverBolts()
	{
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(EquipmentInventorySlot.AMMO, ItemID.SILVER_BOLTS),
			ImmutableMap.of(
				EquipmentInventorySlot.AMMO, ItemID.SILVER_BOLTS,
				EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID
			)
		);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(Integer.MAX_VALUE, tier2VampyreImmunities.compute(context));
		assertEquals(Integer.MAX_VALUE, tier2VampyreImmunities.compute(context));
	}

	@Test
	void limitsMaxHitForMeleeWithoutEfarityAndSilverWeapons()
	{
		when(context.get(equipmentItemIdsComputable)).thenAnswer(i -> Collections.emptyMap());
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertEquals(0, tier2VampyreImmunities.compute(context));
		verify(context, times(1)).warn("Tier 2 vampyres can only be hit by silver weapons or with Efaritay's aid.");
	}

	@Test
	void limitsMaxHitForMeleeWithEfarity()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertEquals(10, tier2VampyreImmunities.compute(context));
		verify(context, times(1)).warn("Efaritay's aid limits max hit to 10.");
	}

	@Test
	void doesNotlimitMaxHitForMeleeWithSilverWeapons()
	{
		// noinspection unchecked
		when(context.get(equipmentItemIdsComputable)).thenReturn(
			ImmutableMap.of(EquipmentInventorySlot.WEAPON, ItemID.BLESSED_AXE),
			ImmutableMap.of(
				EquipmentInventorySlot.WEAPON, ItemID.BLISTERWOOD_FLAIL,
				EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID
			)
		);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertEquals(Integer.MAX_VALUE, tier2VampyreImmunities.compute(context));
		assertEquals(Integer.MAX_VALUE, tier2VampyreImmunities.compute(context));
	}
}