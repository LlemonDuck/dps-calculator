package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class DragonHunter implements GearBonusOperation
{

	private static final Set<Integer> DRAGON_HUNTER_MELEE = ImmutableSet.of(
		ItemID.DRAGON_HUNTER_LANCE
	);

	private static final Set<Integer> DRAGON_HUNTER_RANGED = ImmutableSet.of(
		ItemID.DRAGON_HUNTER_CROSSBOW,
		ItemID.DRAGON_HUNTER_CROSSBOW_B,
		ItemID.DRAGON_HUNTER_CROSSBOW_T
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		AttackType at = ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType();
		if (at == AttackType.MAGIC)
		{
			return false;
		}

		int weaponId = ctx.get(weapon).getItemId();
		Set<Integer> weaponSet = at == AttackType.RANGED ? DRAGON_HUNTER_RANGED : DRAGON_HUNTER_MELEE;
		if (!weaponSet.contains(weaponId))
		{
			return false;
		}

		if (!ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isDragon())
		{
			ctx.warn("Using dragon hunter weaponry against non-dragons provides no bonuses.");
			return false;
		}

		return true;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		if (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE)
		{
			// dhl
			return GearBonus.symmetric(Multiplication.ofPercent(20));
		}

		// dhcb
		return new GearBonus(Multiplication.ofPercent(30), Multiplication.ofPercent(25));
	}

}
