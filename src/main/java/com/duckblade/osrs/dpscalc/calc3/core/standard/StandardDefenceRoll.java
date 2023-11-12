package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.DefensiveBonuses;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StandardDefenceRoll implements ContextValue<Integer>
{

	@Override
	public Integer compute(ComputeContext ctx)
	{
		Map<Skill, Integer> totals = ctx.get(ComputeInputs.DEFENDER_SKILLS).getTotals();
		int defenceBonus = getEquipmentBonus(ctx);
		int defenceLevel = totals.get(Skill.DEFENCE);
		if (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC)
		{
			defenceLevel = totals.get(Skill.MAGIC);
		}


		return (defenceLevel + 9) * (defenceBonus + 64);
	}

	private int getEquipmentBonus(ComputeContext ctx)
	{
		AttackStyle attackStyle = ctx.get(ComputeInputs.ATTACK_STYLE);
		DefensiveBonuses equipment = ctx.get(ComputeInputs.DEFENDER_BONUSES);
		switch (attackStyle.getAttackType())
		{
			case MELEE:
				switch (attackStyle.getMeleeAttackType())
				{
					case STAB:
						return equipment.getDefenseStab();

					case SLASH:
						return equipment.getDefenseSlash();

					case CRUSH:
						return equipment.getDefenseCrush();
				}

			case RANGED:
				return equipment.getDefenseRanged();

			case MAGIC:
				return equipment.getDefenseMagic();
		}

		return 0;
	}

}
