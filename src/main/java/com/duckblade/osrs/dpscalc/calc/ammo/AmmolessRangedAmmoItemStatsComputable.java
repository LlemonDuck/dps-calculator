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
public class AmmolessRangedAmmoItemStatsComputable implements AmmoItemStatsComputable
{

	private final static Set<Integer> AMMOLESS_RANGED_WEAPONS = ImmutableSet.of(
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

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.RANGED &&
			AMMOLESS_RANGED_WEAPONS.contains(context.get(weaponComputable).getItemId());
	}

	@Override
	public ItemStats compute(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACKER_EQUIPMENT)
			.getOrDefault(EquipmentInventorySlot.AMMO, ItemStats.EMPTY)
			.toBuilder()
			.strengthRanged(0)
			.accuracyRanged(0)
			.build();
	}

}
