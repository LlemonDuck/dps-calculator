package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs.DEFENDER_ATTRIBUTES;
import com.duckblade.osrs.dpscalc.calc.maxhit.magic.SpellMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MageDemonbaneGearBonus implements GearBonusComputable
{

	private static final Set<Spell> DEMONBANE_SPELLS = ImmutableSet.of(
		Spell.INFERIOR_DEMONBANE,
		Spell.SUPERIOR_DEMONBANE,
		Spell.DARK_DEMONBANE
	);

	private final SpellMaxHitComputable spellMaxHitComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return spellMaxHitComputable.isApplicable(context) &&
			DEMONBANE_SPELLS.contains(context.get(ComputeInputs.SPELL));
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		if (!context.get(DEFENDER_ATTRIBUTES).isDemon())
		{
			context.warn("Demonbane spells cannot be used against non-demons.");
			return GearBonuses.symmetric(0.0);
		}

		boolean mark = context.get(ComputeInputs.USING_MARK_OF_DARKNESS);
		return GearBonuses.of(mark ? 1.4 : 1.2, 1.25);
	}

}
