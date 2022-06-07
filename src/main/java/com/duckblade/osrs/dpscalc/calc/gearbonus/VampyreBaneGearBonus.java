package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class VampyreBaneGearBonus implements GearBonusComputable
{

	private static final Map<Integer, GearBonuses> weaponToBonus = ImmutableMap.of(
		ItemID.IVANDIS_FLAIL, GearBonuses.of(1, 1.20),
		ItemID.BLISTERWOOD_SICKLE, GearBonuses.of(1.05, 1.15),
		ItemID.BLISTERWOOD_FLAIL, GearBonuses.of(1.05, 1.25)
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		int weapon = context.get(weaponComputable).getItemId();
		if (!weaponToBonus.containsKey(weapon))
		{
			return false;
		}

		if (!context.get(ComputeInputs.DEFENDER_ATTRIBUTES).isVampyre())
		{
			context.warn("Vampyrebane weapons against a non-vampyre target provide no bonuses.");
			return false;
		}

		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee();
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		int weapon = context.get(weaponComputable).getItemId();
		return weaponToBonus.getOrDefault(weapon, GearBonuses.EMPTY);
	}
}
