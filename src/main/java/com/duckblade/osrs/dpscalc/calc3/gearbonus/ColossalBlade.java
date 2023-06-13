package com.duckblade.osrs.dpscalc.calc3.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Addition;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ColossalBlade implements GearBonusOperation
{

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(weapon).getItemId() == ItemID.COLOSSAL_BLADE &&
			ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		int enemySize = ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).getSize();
		int bonus = 2 * Math.min(enemySize, 5);
		return new GearBonus(null, new Addition(bonus));
	}
}
