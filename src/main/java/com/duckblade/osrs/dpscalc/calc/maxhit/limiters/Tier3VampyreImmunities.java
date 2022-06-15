package com.duckblade.osrs.dpscalc.calc.maxhit.limiters;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.MaxHitLimit;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class Tier3VampyreImmunities implements MaxHitLimiter
{

	private static final Set<Integer> T3_VAMPYREBANE_WEAPONS = ImmutableSet.of(
		// t3
		ItemID.IVANDIS_FLAIL,
		ItemID.BLISTERWOOD_SICKLE,
		ItemID.BLISTERWOOD_FLAIL
	);

	private static final MaxHitLimit TIER_3_IMMUNITY = MaxHitLimit.builder()
		.limit(0)
		.warning("Tier 3 vampyres can only be damaged by Blisterwood weapons.")
		.build();

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.DEFENDER_ATTRIBUTES).isVampyre3();
	}

	@Override
	public MaxHitLimit compute(ComputeContext context)
	{
		if (context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee() &&
			T3_VAMPYREBANE_WEAPONS.contains(context.get(weaponComputable).getItemId()))
		{
			return MaxHitLimit.UNLIMITED;
		}

		return TIER_3_IMMUNITY;
	}
}
