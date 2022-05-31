package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import static com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs.ATTACK_STYLE;
import static com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs.DEFENDER_ATTRIBUTES;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DragonHunterGearBonus implements GearBonusComputable
{

	private static final Set<Integer> DRAGON_HUNTER_MELEE = ImmutableSet.of(
		ItemID.DRAGON_HUNTER_LANCE
	);

	private static final Set<Integer> DRAGON_HUNTER_RANGED = ImmutableSet.of(
		ItemID.DRAGON_HUNTER_CROSSBOW,
		ItemID.DRAGON_HUNTER_CROSSBOW_B,
		ItemID.DRAGON_HUNTER_CROSSBOW_T
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		int weapon = context.get(weaponComputable).getItemId();
		switch (context.get(ATTACK_STYLE).getAttackType())
		{
			case MAGIC:
				return false;

			case RANGED:
				return DRAGON_HUNTER_RANGED.contains(weapon);

			default:
				return DRAGON_HUNTER_MELEE.contains(weapon);
		}
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		if (!context.get(DEFENDER_ATTRIBUTES).isDragon())
		{
			context.warn("Using dragon hunter weaponry against non-dragons provides no bonuses.");
			return GearBonuses.EMPTY;
		}

		ItemStats weapon = context.get(weaponComputable);
		if (DRAGON_HUNTER_MELEE.contains(weapon.getItemId()))
		{
			return GearBonuses.symmetric(1.2);
		}
		return GearBonuses.of(1.30, 1.25);
	}

}
