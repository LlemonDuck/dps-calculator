package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.CombatMode;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject)) // for sake of UITest, better than @Inject on the fields
public class CalcManager
{
	
	private final MageDpsCalc mageDpsCalc;
	private final MeleeDpsCalc meleeDpsCalc;
	private final RangedDpsCalc rangedDpsCalc;
	
	private AbstractCalc getCalc(CombatMode mode)
	{
		switch (mode)
		{
			case MAGE:
				return mageDpsCalc;
			case MELEE:
				return meleeDpsCalc;
			default:
				return rangedDpsCalc;
		}
	}

	public CalcResult calculateDPS(CalcInput input)
	{
		return getCalc(input.getCombatMode())
				.calculateDPS(input);
	}
	

}
