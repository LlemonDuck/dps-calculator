package com.duckblade.osrs.dpscalc.calc3.maxhit;

import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Addition;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MeleeCombatStyleBonus implements GearBonusOperation
{

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		// all styles get 8 base
		int attBonus = 8;
		int strBonus = 8;
		switch (ctx.get(ComputeInputs.ATTACK_STYLE).getCombatStyle())
		{
			case ACCURATE:
				attBonus += 3;
				break;

			case AGGRESSIVE:
				strBonus += 3;
				break;

			case CONTROLLED:
				attBonus += 1;
				strBonus += 1;
				break;
		}

		return new GearBonus(new Addition(attBonus), new Addition(strBonus));
	}

}
