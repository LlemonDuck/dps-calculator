package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class RevenantWeapons implements GearBonusOperation
{

	private static final Set<Integer> REVENANT_MELEE = ImmutableSet.of(
		ItemID.VIGGORAS_CHAINMACE,
		ItemID.URSINE_CHAINMACE
	);

	private static final Set<Integer> REVENANT_RANGED = ImmutableSet.of(
		ItemID.CRAWS_BOW,
		ItemID.WEBWEAVER_BOW
	);

	private static final Set<Integer> REVENANT_MAGE = ImmutableSet.of(
		ItemID.THAMMARONS_SCEPTRE,
		ItemID.THAMMARONS_SCEPTRE_A,
		ItemID.ACCURSED_SCEPTRE,
		ItemID.ACCURSED_SCEPTRE_A
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		int weaponId = ctx.get(weapon).getItemId();

		// can't check only for weaponId, since staff can bash and mace/bow can manual cast
		switch (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MAGIC:
				if (!REVENANT_MAGE.contains(weaponId))
				{
					return false;
				}
				break;

			case RANGED:
				if (!REVENANT_RANGED.contains(weaponId))
				{
					return false;
				}
				break;

			default:
				if (!REVENANT_MELEE.contains(weaponId))
				{
					return false;
				}
				break;
		}

		if (!ctx.get(ComputeInputs.IN_WILDERNESS))
		{
			ctx.warn("Using revenant weapons outside the wilderness provides no bonuses.");
			return false;
		}

		return true;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		return GearBonus.symmetric(Multiplication.ofPercent(50));
	}
}
