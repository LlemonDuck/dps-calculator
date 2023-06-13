package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Addition;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Golembane implements GearBonusOperation
{

	private static final Set<Integer> GOLEMBANE_WEAPONS = ImmutableSet.of(
		ItemID.BARRONITE_MACE
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isGolem() &&
			ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE &&
			GOLEMBANE_WEAPONS.contains(ctx.get(weapon).getItemId());
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		return new GearBonus(null, new Multiplication(23, 20));
	}

}
