package com.duckblade.osrs.dpscalc.calc.defender.skills;

import com.duckblade.osrs.dpscalc.calc.ToaArena;
import com.duckblade.osrs.dpscalc.calc.ToaArenaComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ToaScaling implements SkillScaling
{

	private final ToaArenaComputable toaArenaComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(toaArenaComputable) == ToaArena.FIGHTING_PATH_NPC
			|| context.get(toaArenaComputable) == ToaArena.FIGHTING_WARDENS;
	}

	@Override
	public int scale(ComputeContext context, Skill skill, int base)
	{
		if (skill != Skill.HITPOINTS)
		{
			return base;
		}

		return (int) (base * partySizeMultiplier(context) * pathLevelMultiplier(context) * invocationMultiplier(context));
	}

	private double partySizeMultiplier(ComputeContext context)
	{
		int partySize = Math.min(8, context.get(ComputeInputs.RAID_PARTY_SIZE));
		switch (partySize)
		{
			case 2:
				return 1.9;
			case 3:
				return 2.8;
			case 4:
				return 3.4; // calculating the general case 2.8 + 0.6 * (partySize - 3) gives rounding error at partySize = 7
			case 5:
				return 4.0;
			case 6:
				return 4.6;
			case 7:
				return 5.2;
			case 8:
				return 5.8;
			default:
				return 1.0;
		}
	}

	public double invocationMultiplier(ComputeContext context)
	{
		int invocationLevel = Math.min(600, context.get(ComputeInputs.TOA_INVOCATION_LEVEL));

		int invocationPercentage = (invocationLevel / 5) * 2;

		return 1.0 + (invocationPercentage / 100.0);
	}

	private double pathLevelMultiplier(ComputeContext context)
	{
		if (context.get(toaArenaComputable) != ToaArena.FIGHTING_PATH_NPC)
		{
			return 1.0;
		}

		int pathLevel = Math.min(6, context.get(ComputeInputs.TOA_PATH_LEVEL));

		int pathLevelPercentage;

		switch (pathLevel)
		{
			case 0:
				pathLevelPercentage = 0;
				break;
			case 1:
				pathLevelPercentage = 8;
				break;
			default:
				pathLevelPercentage = 8 + (5 * (pathLevel - 1));
				break;
		}

		return 1.0 + (pathLevelPercentage / 100.0);
	}

}
