package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BlackMaskGearBonus implements GearBonusComputable
{

	private static final Set<Integer> BLACK_MASKS_MAGE_RANGED = ImmutableSet.of(
		ItemID.BLACK_MASK_I,
		ItemID.BLACK_MASK_I_25276,
		ItemID.BLACK_MASK_I_26781,
		ItemID.BLACK_MASK_1_I,
		ItemID.BLACK_MASK_1_I_25275,
		ItemID.BLACK_MASK_1_I_26780,
		ItemID.BLACK_MASK_2_I,
		ItemID.BLACK_MASK_2_I_25274,
		ItemID.BLACK_MASK_2_I_26779,
		ItemID.BLACK_MASK_3_I,
		ItemID.BLACK_MASK_3_I_25273,
		ItemID.BLACK_MASK_3_I_26778,
		ItemID.BLACK_MASK_4_I,
		ItemID.BLACK_MASK_4_I_25272,
		ItemID.BLACK_MASK_4_I_26777,
		ItemID.BLACK_MASK_5_I,
		ItemID.BLACK_MASK_5_I_25271,
		ItemID.BLACK_MASK_5_I_26776,
		ItemID.BLACK_MASK_6_I,
		ItemID.BLACK_MASK_6_I_25270,
		ItemID.BLACK_MASK_6_I_26775,
		ItemID.BLACK_MASK_7_I,
		ItemID.BLACK_MASK_7_I_25269,
		ItemID.BLACK_MASK_7_I_26774,
		ItemID.BLACK_MASK_8_I,
		ItemID.BLACK_MASK_8_I_25268,
		ItemID.BLACK_MASK_8_I_26773,
		ItemID.BLACK_MASK_9_I,
		ItemID.BLACK_MASK_9_I_25267,
		ItemID.BLACK_MASK_9_I_26772,
		ItemID.BLACK_MASK_10_I,
		ItemID.BLACK_MASK_10_I_25266,
		ItemID.BLACK_MASK_10_I_26771,
		ItemID.SLAYER_HELMET_I,
		ItemID.SLAYER_HELMET_I_25177,
		ItemID.SLAYER_HELMET_I_26674,
		ItemID.BLACK_SLAYER_HELMET_I,
		ItemID.BLACK_SLAYER_HELMET_I_25179,
		ItemID.BLACK_SLAYER_HELMET_I_26675,
		ItemID.GREEN_SLAYER_HELMET_I,
		ItemID.GREEN_SLAYER_HELMET_I_25181,
		ItemID.GREEN_SLAYER_HELMET_I_26676,
		ItemID.HYDRA_SLAYER_HELMET_I,
		ItemID.HYDRA_SLAYER_HELMET_I_25189,
		ItemID.HYDRA_SLAYER_HELMET_I_26680,
		ItemID.PURPLE_SLAYER_HELMET_I,
		ItemID.PURPLE_SLAYER_HELMET_I_25185,
		ItemID.PURPLE_SLAYER_HELMET_I_26678,
		ItemID.RED_SLAYER_HELMET_I,
		ItemID.RED_SLAYER_HELMET_I_25183,
		ItemID.RED_SLAYER_HELMET_I_26677,
		ItemID.TURQUOISE_SLAYER_HELMET_I,
		ItemID.TURQUOISE_SLAYER_HELMET_I_25187,
		ItemID.TURQUOISE_SLAYER_HELMET_I_26679,
		ItemID.TWISTED_SLAYER_HELMET_I,
		ItemID.TWISTED_SLAYER_HELMET_I_25191,
		ItemID.TWISTED_SLAYER_HELMET_I_26681,
		ItemID.TZKAL_SLAYER_HELMET_I,
		ItemID.TZKAL_SLAYER_HELMET_I_25914,
		ItemID.TZKAL_SLAYER_HELMET_I_26684,
		ItemID.TZTOK_SLAYER_HELMET_I,
		ItemID.TZTOK_SLAYER_HELMET_I_25902,
		ItemID.TZTOK_SLAYER_HELMET_I_26682,
		ItemID.VAMPYRIC_SLAYER_HELMET_I,
		ItemID.VAMPYRIC_SLAYER_HELMET_I_25908,
		ItemID.VAMPYRIC_SLAYER_HELMET_I_26683
	);

	private static final Set<Integer> BLACK_MASKS_MELEE = Sets.union(ImmutableSet.of(
		ItemID.BLACK_MASK,
		ItemID.BLACK_MASK_1,
		ItemID.BLACK_MASK_2,
		ItemID.BLACK_MASK_3,
		ItemID.BLACK_MASK_4,
		ItemID.BLACK_MASK_5,
		ItemID.BLACK_MASK_6,
		ItemID.BLACK_MASK_7,
		ItemID.BLACK_MASK_8,
		ItemID.BLACK_MASK_9,
		ItemID.BLACK_MASK_10,
		ItemID.SLAYER_HELMET,
		ItemID.BLACK_SLAYER_HELMET,
		ItemID.GREEN_SLAYER_HELMET,
		ItemID.HYDRA_SLAYER_HELMET,
		ItemID.PURPLE_SLAYER_HELMET,
		ItemID.RED_SLAYER_HELMET,
		ItemID.TURQUOISE_SLAYER_HELMET,
		ItemID.TWISTED_SLAYER_HELMET,
		ItemID.TZKAL_SLAYER_HELMET,
		ItemID.TZTOK_SLAYER_HELMET,
		ItemID.VAMPYRIC_SLAYER_HELMET
	), BLACK_MASKS_MAGE_RANGED);

	private static final GearBonuses BLACK_MASK_MELEE = GearBonuses.symmetric(7.0 / 6.0);
	private static final GearBonuses BLACK_MASK_RANGED_MAGE = GearBonuses.symmetric(1.15);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;
	private final SalveAmuletGearBonus salveAmuletGearBonus;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return BLACK_MASKS_MELEE.contains(context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.HEAD));
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		if (!context.get(ComputeInputs.ON_SLAYER_TASK))
		{
			context.warn("Black mask/Slayer helmet off-task provides minimal or negative accuracy bonuses.");
			return GearBonuses.EMPTY;
		}

		if (salveAmuletGearBonus.isApplicable(context))
		{
			context.warn("Black mask/Slayer helmet does not stack with salve amulet.");
			return GearBonuses.EMPTY;
		}

		AttackType attackType = context.get(ComputeInputs.ATTACK_STYLE).getAttackType();
		switch (attackType)
		{
			case MAGIC:
			case RANGED:
				int helmet = context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.HEAD);
				if (!BLACK_MASKS_MAGE_RANGED.contains(helmet))
				{
					context.warn("Unimbued Black mask/Slayer helmet provides negative bonuses for ranged/magic.");
					return GearBonuses.EMPTY;
				}
				return BLACK_MASK_RANGED_MAGE;

			default:
				return BLACK_MASK_MELEE;
		}
	}

}
