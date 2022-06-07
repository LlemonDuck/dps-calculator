package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

import static com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs.ATTACK_STYLE;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class VampyreGearBonus implements GearBonusComputable
{

	private static final Map<Integer, GearBonuses> weaponToBonus = ImmutableMap.of(
			ItemID.BLISTERWOOD_FLAIL, GearBonuses.of(1.05, 1.25),
			ItemID.IVANDIS_FLAIL, GearBonuses.of(1, 1.20)
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		DefenderAttributes attributes = context.get(ComputeInputs.DEFENDER_ATTRIBUTES);
		if (!attributes.isVampyre())
		{
			return false;
		}

		if (!context.get(ATTACK_STYLE).getAttackType().isMelee())
		{
			return false;
		}

		int weapon = context.get(weaponComputable).getItemId();
		return weaponToBonus.containsKey(weapon);
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		DefenderAttributes attributes = context.get(ComputeInputs.DEFENDER_ATTRIBUTES);
		if (!attributes.isVampyre())
		{
			context.warn("Vampyrebane weapons against a non-vampyre target provides no bonuses.");
			return GearBonuses.EMPTY;
		}
		int weapon = context.get(weaponComputable).getItemId();
		return weaponToBonus.getOrDefault(weapon, GearBonuses.EMPTY);
	}
}
