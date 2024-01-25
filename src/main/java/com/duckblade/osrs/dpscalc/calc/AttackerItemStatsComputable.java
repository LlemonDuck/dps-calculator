package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.ammo.AmmoSlotItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AttackerItemStatsComputable implements Computable<ItemStats>
{

	// this one is a bit more complicated since it depends whether you're using an ammo-based weapon
	private final AmmoSlotItemStatsComputable ammoSlotItemStatsComputable;

	private final ToaArenaComputable toaArenaComputable;

	@Override
	public ItemStats compute(ComputeContext context)
	{
		Map<EquipmentInventorySlot, ItemStats> itemStats = context.get(ComputeInputs.ATTACKER_ITEMS);
		ItemStats ammoless = itemStats.entrySet()
			.stream()
			.filter(is -> is.getKey() != EquipmentInventorySlot.AMMO)
			.map(Map.Entry::getValue)
			.filter(Objects::nonNull)
			.reduce(AttackerItemStatsComputable::reduce)
			.orElse(ItemStats.EMPTY);

		ItemStats ammoSlot = context.get(ammoSlotItemStatsComputable);
		ItemStats preShadow = reduce(ammoless, ammoSlot);

		// todo if anything else ever gets functionality similar, move this out to a better structure
		ItemStats weapon = itemStats.get(EquipmentInventorySlot.WEAPON);
		if (weapon != null && weapon.getItemId() == ItemID.TUMEKENS_SHADOW)
		{
			return applyTumekensShadow(context, preShadow);
		}
		else
		{
			return preShadow;
		}
	}

	private ItemStats applyTumekensShadow(ComputeContext context, ItemStats itemStats)
	{
		if (context.get(toaArenaComputable) == ToaArena.FIGHTING_OUTSIDE_TOA)
		{
			return itemStats.toBuilder()
				.accuracyMagic(3 * itemStats.getAccuracyMagic())
				.strengthMagic(3 * itemStats.getStrengthMagic())
				.build();
		}
		else
		{
			return itemStats.toBuilder()
				.accuracyMagic(4 * itemStats.getAccuracyMagic())
				.strengthMagic(4 * itemStats.getStrengthMagic())
				.build();
		}
	}

	public static ItemStats reduce(ItemStats a, ItemStats b)
	{
		ItemStats.ItemStatsBuilder builder = ItemStats.builder()
			.accuracyStab(a.getAccuracyStab() + b.getAccuracyStab())
			.accuracySlash(a.getAccuracySlash() + b.getAccuracySlash())
			.accuracyCrush(a.getAccuracyCrush() + b.getAccuracyCrush())
			.accuracyRanged(a.getAccuracyRanged() + b.getAccuracyRanged())
			.accuracyMagic(a.getAccuracyMagic() + b.getAccuracyMagic())
			.strengthMelee(a.getStrengthMelee() + b.getStrengthMelee())
			.strengthRanged(a.getStrengthRanged() + b.getStrengthRanged())
			.strengthMagic(a.getStrengthMagic() + b.getStrengthMagic())
			.prayer(a.getPrayer() + b.getPrayer());

		ItemStats weapon = getWeapon(a, b);
		if (weapon != null)
		{
			builder = builder.speed(weapon.getSpeed())
				.slot(EquipmentInventorySlot.WEAPON.getSlotIdx())
				.is2h(weapon.is2h())
				.weaponCategory(weapon.getWeaponCategory());
		}

		return builder.build();
	}

	private static ItemStats getWeapon(ItemStats a, ItemStats b)
	{
		int weaponSlot = EquipmentInventorySlot.WEAPON.getSlotIdx();
		if (a.getSlot() == weaponSlot)
		{
			return a;
		}
		else if (b.getSlot() == weaponSlot)
		{
			return b;
		}

		return null;
	}

}
