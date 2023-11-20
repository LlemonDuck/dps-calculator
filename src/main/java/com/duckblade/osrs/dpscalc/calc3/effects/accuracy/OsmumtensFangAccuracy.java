package com.duckblade.osrs.dpscalc.calc3.effects.accuracy;

import com.duckblade.osrs.dpscalc.calc3.core.AttackRoll;
import com.duckblade.osrs.dpscalc.calc3.core.DefenceRoll;
import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardAccuracy;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class OsmumtensFangAccuracy implements ContextValue<Double>
{

	static final Set<Integer> OSMUMTENS_FANG = ImmutableSet.of(
		ItemID.OSMUMTENS_FANG,
		ItemID.OSMUMTENS_FANG_OR
	);

	private final Weapon weapon;
	private final AttackRoll attackRoll;
	private final DefenceRoll defenceRoll;
	private final StandardAccuracy standardAccuracy;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return OSMUMTENS_FANG.contains(ctx.get(weapon).getItemId()) &&
			!ctx.get(ComputeInputs.SCENARIO).isSpecialAttack();
	}

	@Override
	public Double compute(ComputeContext ctx)
	{
		if (ctx.get(ComputeInputs.SCENARIO).getToaScale() != -1)
		{
			double atk = (double) ctx.get(attackRoll);
			double def = (double) ctx.get(defenceRoll);
			return (atk > def)
				? 1 - (def + 2) * (2 * def + 3) / (atk + 1) / (atk + 1) / 6
				: atk * (4 * atk + 5) / 6 / (atk + 1) / (def + 1);
		}

		return 1 - Math.pow(1 - ctx.get(standardAccuracy), 2);
	}
}
