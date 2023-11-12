package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Addition;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CombatStyleBonus implements GearBonusOperation
{

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		// all styles get 8 base
		int attBonus = 8;
		int strBonus = 8;

		AttackStyle attackStyle = ctx.get(ComputeInputs.ATTACK_STYLE);
		switch (attackStyle.getCombatStyle())
		{
			case ACCURATE:
				attBonus += 3;
				if (attackStyle.getAttackType() == AttackType.RANGED)
				{
					strBonus += 3;
				}
				break;

			case AGGRESSIVE:
				strBonus += 3;
				break;

			case CONTROLLED:
				attBonus += 1;
				strBonus += 1;
				break;

			case LONGRANGE:
			case AUTOCAST:
				if (attackStyle.getAttackType() == AttackType.MAGIC)
				{
					attBonus += 1;
				}
				break;
		}

		return new GearBonus(new Addition(attBonus), new Addition(strBonus));
	}

}
