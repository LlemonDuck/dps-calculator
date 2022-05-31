package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
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
public class CrystalGearBonus implements GearBonusComputable
{

	private static final Set<Integer> CRYSTAL_BOWS = ImmutableSet.of(
		// there are a few dozen cbow ids from pre-rework but i think these are the only two in use now
		ItemID.CRYSTAL_BOW,
		ItemID.CRYSTAL_BOW_24123,
		ItemID.BOW_OF_FAERDHINEN,
		ItemID.BOW_OF_FAERDHINEN_C,
		ItemID.BOW_OF_FAERDHINEN_C_25869,
		ItemID.BOW_OF_FAERDHINEN_C_25884,
		ItemID.BOW_OF_FAERDHINEN_C_25886,
		ItemID.BOW_OF_FAERDHINEN_C_25888,
		ItemID.BOW_OF_FAERDHINEN_C_25890,
		ItemID.BOW_OF_FAERDHINEN_C_25892,
		ItemID.BOW_OF_FAERDHINEN_C_25896
	);

	private static final Set<Integer> CRYSTAL_HELM_IDS = ImmutableSet.of(
		ItemID.CRYSTAL_HELM
	);

	private static final Set<Integer> CRYSTAL_BODY_IDS = ImmutableSet.of(
		ItemID.CRYSTAL_BODY
	);

	private static final Set<Integer> CRYSTAL_LEGS_IDS = ImmutableSet.of(
		ItemID.CRYSTAL_LEGS
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return CRYSTAL_BOWS.contains(context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.WEAPON));
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		// no set bonus so we can treat each piece individually
		Map<EquipmentInventorySlot, Integer> equipment = context.get(equipmentItemIdsComputable);
		double bonus = 0.0;

		if (CRYSTAL_HELM_IDS.contains(equipment.get(EquipmentInventorySlot.HEAD)))
		{
			bonus += 0.05;
		}

		if (CRYSTAL_BODY_IDS.contains(equipment.get(EquipmentInventorySlot.BODY)))
		{
			bonus += 0.15;
		}

		if (CRYSTAL_LEGS_IDS.contains(equipment.get(EquipmentInventorySlot.LEGS)))
		{
			bonus += 0.10;
		}

		return GearBonuses.of(1.0 + bonus, 1.0 + (bonus / 2.0));
	}

}
