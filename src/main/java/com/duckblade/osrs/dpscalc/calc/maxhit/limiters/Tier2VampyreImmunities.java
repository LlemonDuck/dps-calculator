package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class Tier2VampyreImmunities implements MaxHitLimiter
{

	private static final Set<Integer> SILVER_MELEE = ImmutableSet.of(
		// t3
		ItemID.IVANDIS_FLAIL,
		ItemID.BLISTERWOOD_SICKLE,
		ItemID.BLISTERWOOD_FLAIL,

		// t2
		ItemID.BLESSED_AXE,
		ItemID.SILVER_SICKLE,
		ItemID.SILVER_SICKLE_B,
		ItemID.EMERALD_SICKLE_B,
		ItemID.ENCHANTED_EMERALD_SICKLE_B,
		ItemID.RUBY_SICKLE_B,
		ItemID.ENCHANTED_RUBY_SICKLE_B,
		ItemID.SILVERLIGHT,
		ItemID.SILVERLIGHT_6745,
		ItemID.DARKLIGHT,
		ItemID.ARCLIGHT,
		ItemID.WOLFBANE,
		ItemID.ROD_OF_IVANDIS_1,
		ItemID.ROD_OF_IVANDIS_2,
		ItemID.ROD_OF_IVANDIS_3,
		ItemID.ROD_OF_IVANDIS_4,
		ItemID.ROD_OF_IVANDIS_5,
		ItemID.ROD_OF_IVANDIS_6,
		ItemID.ROD_OF_IVANDIS_7,
		ItemID.ROD_OF_IVANDIS_8,
		ItemID.ROD_OF_IVANDIS_9,
		ItemID.ROD_OF_IVANDIS_10
	);

	private static final Set<Integer> SILVER_AMMO = ImmutableSet.of(
		ItemID.SILVER_BOLTS,
		ItemID.SILVER_BOLTS_P,
		ItemID.SILVER_BOLTS_P_9299,
		ItemID.SILVER_BOLTS_P_9306
	);

	private static final Set<Integer> EFARITAYS_AID = ImmutableSet.of(
		ItemID.EFARITAYS_AID
	);

	private static final MaxHitLimit EFARITAY_LIMIT = MaxHitLimit.builder()
		.limit(10)
		.warning("Efaritay's aid limits max hit to 10.")
		.build();

	private static final MaxHitLimit TIER_2_IMMUNITY = MaxHitLimit.builder()
		.limit(0)
		.warning("Tier 2 vampyres can only be damaged by silver weaponry or with Efaritay's aid.")
		.build();

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.DEFENDER_ATTRIBUTES).isVampyre2();
	}

	@Override
	public MaxHitLimit compute(ComputeContext context)
	{
		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		boolean efaritay = EFARITAYS_AID.contains(equipment.get(EquipmentInventorySlot.RING));

		switch (context.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MAGIC:
				return efaritay ? EFARITAY_LIMIT : TIER_2_IMMUNITY;

			case RANGED:
				if (SILVER_AMMO.contains(equipment.get(EquipmentInventorySlot.AMMO)))
				{
					return MaxHitLimit.UNLIMITED;
				}

				return efaritay ? EFARITAY_LIMIT : TIER_2_IMMUNITY;

			default:
				if (SILVER_MELEE.contains(equipment.get(EquipmentInventorySlot.WEAPON)))
				{
					return MaxHitLimit.UNLIMITED;
				}

				return efaritay ? EFARITAY_LIMIT : TIER_2_IMMUNITY;
		}
	}
}
