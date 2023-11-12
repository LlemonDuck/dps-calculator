package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LeafBladedBattleAxe implements GearBonusOperation
{

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isLeafy() &&
			ctx.get(weapon).getItemId() == ItemID.LEAFBLADED_BATTLEAXE &&
			ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		return GearBonus.symmetric(new Multiplication(7, 40));
	}

}
