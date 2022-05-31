package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs.DEFENDER_ATTRIBUTES;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MeleeDemonbaneGearBonus implements GearBonusComputable
{

	private static final Set<Integer> DEMONBANE_L2 = ImmutableSet.of(
		ItemID.ARCLIGHT
	);

	private static final Set<Integer> DEMONBANE_L1 = ImmutableSet.of(
		ItemID.DARKLIGHT,
		ItemID.SILVERLIGHT,
		ItemID.SILVERLIGHT_6745 // stained black, mid-shadow of the storm
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		boolean melee = context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee();
		int weapon = context.get(weaponComputable).getItemId();
		return melee && (DEMONBANE_L1.contains(weapon) || DEMONBANE_L2.contains(weapon));
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		if (!context.get(DEFENDER_ATTRIBUTES).isDemon())
		{
			context.warn("Using demonbane weaponry against non-demons provides no bonuses.");
			return GearBonuses.EMPTY;
		}

		int weapon = context.get(weaponComputable).getItemId();
		if (DEMONBANE_L2.contains(weapon))
		{
			return GearBonuses.symmetric(1.7);
		}
		return GearBonuses.of(1.0, 1.6);
	}

}
