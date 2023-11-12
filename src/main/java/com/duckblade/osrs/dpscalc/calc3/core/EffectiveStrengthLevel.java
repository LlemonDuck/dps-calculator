package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.effects.voidknight.VoidKnight;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class EffectiveStrengthLevel implements ContextValue<Integer>
{

	private final PrayerBonus prayerBonus;
	private final CombatStyleBonus combatStyleBonus;
	private final VoidKnight voidKnight;

	@Override
	public Integer compute(ComputeContext ctx)
	{
		Map<Skill, Integer> skills = ctx.get(ComputeInputs.ATTACKER_SKILLS).getTotals();
		int effectiveStrength = 0;
		switch (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MELEE:
				effectiveStrength = skills.get(Skill.STRENGTH);
				break;
			case RANGED:
				effectiveStrength = skills.get(Skill.RANGED);
				break;
			case MAGIC:
				effectiveStrength = skills.get(Skill.MAGIC);
				break;
		}
		;

		// these three are all applied to effective strength, not the max hit
		effectiveStrength = prayerBonus.applyStrength(ctx, effectiveStrength);
		effectiveStrength = combatStyleBonus.applyStrength(ctx, effectiveStrength);
		effectiveStrength = voidKnight.applyStrength(ctx, effectiveStrength);
		return effectiveStrength;
	}

}
