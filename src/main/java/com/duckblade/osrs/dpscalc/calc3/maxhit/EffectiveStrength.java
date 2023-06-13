package com.duckblade.osrs.dpscalc.calc3.maxhit;

import com.duckblade.osrs.dpscalc.calc3.gearbonus.voidknight.VoidKnight;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.prayer.PrayerBonus;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class EffectiveStrength implements ContextValue<Integer>
{

	private final PrayerBonus prayerBonus;
	private final MeleeCombatStyleBonus combatStyleBonus;
	private final VoidKnight voidKnight;

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int effectiveStrength = ctx.get(ComputeInputs.ATTACKER_SKILLS)
			.getTotals()
			.get(Skill.STRENGTH);

		// these three are all applied to effective strength, not the max hit
		effectiveStrength = prayerBonus.applyStrength(ctx, effectiveStrength);
		effectiveStrength = combatStyleBonus.applyStrength(ctx, effectiveStrength);
		effectiveStrength = voidKnight.applyStrength(ctx, effectiveStrength);
		return effectiveStrength;
	}

}
