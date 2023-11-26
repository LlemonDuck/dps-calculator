package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.core.standard.magicmaxhit.SpellbookMaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs.DEFENDER_ATTRIBUTES;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.Spell;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MageDemonbane implements GearBonusOperation
{

	private static final Set<Spell> DEMONBANE_SPELLS = ImmutableSet.of(
		Spell.INFERIOR_DEMONBANE,
		Spell.SUPERIOR_DEMONBANE,
		Spell.DARK_DEMONBANE
	);

	private final SpellbookMaxHit spellbookMaxHit;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.isApplicable(this) &&
			DEMONBANE_SPELLS.contains(ctx.get(ComputeInputs.SPELL));
	}

	@Override
	public GearBonus compute(ComputeContext context)
	{
		if (!context.get(DEFENDER_ATTRIBUTES).isDemon())
		{
			context.warn("Demonbane spells cannot be used against non-demons.");
			return new GearBonus(new Multiplication(0), new Multiplication(0));
		}

		boolean mark = context.get(ComputeInputs.USING_MARK_OF_DARKNESS);
		return new GearBonus(
			new Multiplication(mark ? 14 : 12, 10),
			new Multiplication(mark ? 5 : 4, 4)
		);
	}

}
