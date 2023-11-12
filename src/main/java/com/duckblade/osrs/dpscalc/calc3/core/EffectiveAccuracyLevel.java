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
public class EffectiveAccuracyLevel implements ContextValue<Integer>
{

	private final PrayerBonus prayerBonus;
	private final CombatStyleBonus combatStyleBonus;
	private final VoidKnight voidKnight;

	@Override
	public Integer compute(ComputeContext ctx)
	{
		Map<Skill, Integer> skills = ctx.get(ComputeInputs.ATTACKER_SKILLS).getTotals();
		int effectiveAccuracy = 0;
		switch (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MELEE:
				effectiveAccuracy = skills.get(Skill.ATTACK);
				break;
			case RANGED:
				effectiveAccuracy = skills.get(Skill.RANGED);
				break;
			case MAGIC:
				effectiveAccuracy = skills.get(Skill.MAGIC);
				break;
		}

		// these three are all applied to effective strength, not the max hit
		effectiveAccuracy = prayerBonus.applyAccuracy(ctx, effectiveAccuracy);
		effectiveAccuracy = combatStyleBonus.applyAccuracy(ctx, effectiveAccuracy);
		effectiveAccuracy = voidKnight.applyAccuracy(ctx, effectiveAccuracy);
		return effectiveAccuracy;
	}

}
