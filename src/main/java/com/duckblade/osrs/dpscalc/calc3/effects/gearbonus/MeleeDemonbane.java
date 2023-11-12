package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

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
public class MeleeDemonbane implements GearBonusOperation
{

	private static final Set<Integer> DEMONBANE_L2 = ImmutableSet.of(
		ItemID.ARCLIGHT
	);

	private static final Set<Integer> DEMONBANE_L1 = ImmutableSet.of(
		ItemID.DARKLIGHT,
		ItemID.SILVERLIGHT,
		ItemID.SILVERLIGHT_6745 // stained black, mid-shadow of the storm
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		int weaponId = ctx.get(weapon).getItemId();
		return ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE &&
			(DEMONBANE_L1.contains(weaponId) || DEMONBANE_L2.contains(weaponId));
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		if (!ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isDemon())
		{
			ctx.warn("Using demonbane weaponry against non-demons provides no bonuses.");
			return null;
		}

		int weaponId = ctx.get(weapon).getItemId();
		if (DEMONBANE_L2.contains(weaponId))
		{
			return GearBonus.symmetric(Multiplication.ofPercent(70));
		}
		return new GearBonus(null, Multiplication.ofPercent(60));
	}

}
