package com.duckblade.osrs.dpscalc.calc.ammo;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BlowpipeDartsItemStatsComputable implements AmmoItemStatsComputable
{

	private static final Set<Integer> BLOWPIPE_IDS = ImmutableSet.of(
		ItemID.TOXIC_BLOWPIPE, ItemID.BLAZING_BLOWPIPE
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.RANGED &&
			BLOWPIPE_IDS.contains(context.get(weaponComputable).getItemId());
	}

	@Override
	public ItemStats compute(ComputeContext context)
	{
		ItemStats darts = context.get(ComputeInputs.BLOWPIPE_DARTS);
		return context.get(ComputeInputs.ATTACKER_ITEMS)
			.getOrDefault(EquipmentInventorySlot.AMMO, ItemStats.EMPTY)
			.toBuilder()
			.accuracyRanged(darts.getAccuracyRanged())
			.strengthRanged(darts.getStrengthRanged())
			.build();
	}

}
