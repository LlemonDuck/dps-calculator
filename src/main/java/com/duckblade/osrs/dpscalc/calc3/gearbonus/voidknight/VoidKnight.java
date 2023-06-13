package com.duckblade.osrs.dpscalc.calc3.gearbonus.voidknight;

import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Addition;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VoidKnight implements GearBonusOperation
{

	private final VoidEquipmentCheck voidEquipmentCheck;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(voidEquipmentCheck) != VoidLevel.NONE;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		VoidLevel voidLevel = ctx.get(voidEquipmentCheck);
		assert voidLevel != null;

		switch (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MAGIC:
				return new GearBonus(new Multiplication(11, 10), new Addition(25));

			case RANGED:
				if (voidLevel == VoidLevel.ELITE)
				{
					return new GearBonus(new Multiplication(11, 10), new Multiplication(9, 8));
				}
				return GearBonus.symmetric(new Multiplication(11, 10));

			default:
				return GearBonus.symmetric(new Multiplication(11, 10));
		}
	}
}
