package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.core.DefenderStats;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.DefensiveStats;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardDefenceRoll implements ContextValue<Integer>
{

	private final DefenderStats defenderStats;

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int defenceBonus = getEquipmentBonus(ctx);

		DefensiveStats stats = ctx.get(ComputeInputs.DEFENDER_STATS);
		int defenceLevel = stats.getLevelDefence();
		if (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC)
		{
			defenceLevel = stats.getLevelMagic();
		}

		return (defenceLevel + 9) * (defenceBonus + 64);
	}

	private int getEquipmentBonus(ComputeContext ctx)
	{
		AttackStyle attackStyle = ctx.get(ComputeInputs.ATTACK_STYLE);
		DefensiveStats defensiveStats = ctx.get(defenderStats);
		switch (attackStyle.getAttackType())
		{
			case MELEE:
				switch (attackStyle.getMeleeAttackType())
				{
					case STAB:
						return defensiveStats.getDefenceStab();

					case SLASH:
						return defensiveStats.getDefenceSlash();

					case CRUSH:
						return defensiveStats.getDefenceCrush();
				}

			case RANGED:
				return defensiveStats.getDefenceRanged();

			case MAGIC:
				return defensiveStats.getDefenceMagic();
		}

		return 0;
	}

}
