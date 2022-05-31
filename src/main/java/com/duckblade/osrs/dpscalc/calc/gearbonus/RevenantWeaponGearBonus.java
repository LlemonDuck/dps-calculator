package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RevenantWeaponGearBonus implements GearBonusComputable
{

	private static final Set<Integer> REVENANT_MELEE = ImmutableSet.of(
		ItemID.VIGGORAS_CHAINMACE
	);

	private static final Set<Integer> REVENANT_RANGED = ImmutableSet.of(
		ItemID.CRAWS_BOW
	);

	private static final Set<Integer> REVENANT_MAGE = ImmutableSet.of(
		ItemID.THAMMARONS_SCEPTRE
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		int weapon = context.get(weaponComputable).getItemId();

		// can't check only for weapon, since staff can bash and mace/bow can manual cast
		switch (context.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MAGIC:
				return REVENANT_MAGE.contains(weapon);

			case RANGED:
				return REVENANT_RANGED.contains(weapon);

			default:
				return REVENANT_MELEE.contains(weapon);
		}
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		if (!context.get(ComputeInputs.IN_WILDERNESS))
		{
			context.warn("Using revenant weapons outside the wilderness provides no bonuses.");
			return GearBonuses.EMPTY;
		}

		if (context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC)
		{
			return GearBonuses.of(2.0, 1.25);
		}
		return GearBonuses.symmetric(1.5);
	}

}
