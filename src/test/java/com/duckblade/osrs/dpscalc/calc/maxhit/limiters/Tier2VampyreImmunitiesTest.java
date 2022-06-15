package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
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

		assertEquals(
			MaxHitLimit.of(0, "Tier 2 vampyres can only be damaged by silver weaponry or with Efaritay's aid."),
			tier2VampyreImmunities.compute(context)
		);
	}

	@Test
	void limitsMaxHitForMagicWithEfaritay()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals(
			MaxHitLimit.of(10, "Efaritay's aid limits max hit to 10."),
			tier2VampyreImmunities.compute(context)
		);
	}

	@Test
	void limitsMaxHitForRangedWithoutEfaritayAndSilverBolts()
	{
		when(context.get(equipmentItemIdsComputable)).thenAnswer(i -> Collections.emptyMap());
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(
			MaxHitLimit.of(0, "Tier 2 vampyres can only be damaged by silver weaponry or with Efaritay's aid."),
			tier2VampyreImmunities.compute(context)
		);
	}

	@Test
	void limitsMaxHitForRangedWithEfaritay()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(
			MaxHitLimit.of(10, "Efaritay's aid limits max hit to 10."),
			tier2VampyreImmunities.compute(context)
		);
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

		assertEquals(MaxHitLimit.UNLIMITED, tier2VampyreImmunities.compute(context));
		assertEquals(MaxHitLimit.UNLIMITED, tier2VampyreImmunities.compute(context));
	}

	@Test
	void limitsMaxHitForMeleeWithoutEfarityAndSilverWeapons()
	{
		when(context.get(equipmentItemIdsComputable)).thenAnswer(i -> Collections.emptyMap());
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertEquals(
			MaxHitLimit.of(0, "Tier 2 vampyres can only be damaged by silver weaponry or with Efaritay's aid."),
			tier2VampyreImmunities.compute(context)
		);
	}

	@Test
	void limitsMaxHitForMeleeWithEfarity()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(EquipmentInventorySlot.RING, ItemID.EFARITAYS_AID));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.SLASH));

		assertEquals(
			MaxHitLimit.of(10, "Efaritay's aid limits max hit to 10."),
			tier2VampyreImmunities.compute(context)
		);
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

		assertEquals(MaxHitLimit.UNLIMITED, tier2VampyreImmunities.compute(context));
		assertEquals(MaxHitLimit.UNLIMITED, tier2VampyreImmunities.compute(context));
	}
}