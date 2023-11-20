package com.duckblade.osrs.dpscalc.calc3.effects.hitdist;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardHitDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.AttackDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.HitDistribution;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.WeightedHit;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class KerisDistribution implements ContextValue<AttackDistribution>
{

	private static final Set<Integer> KERIS_WEAPONS = ImmutableSet.of(
		ItemID.KERIS,
		ItemID.KERISP,
		ItemID.KERISP_10583,
		ItemID.KERISP_10584,
		ItemID.KERIS_PARTISAN,
		ItemID.KERIS_PARTISAN_OF_BREACHING
	);

	private final Weapon weapon;
	private final StandardHitDistribution standardHitDistribution;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isKalphite() &&
			ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE &&
			KERIS_WEAPONS.contains(ctx.get(weapon).getItemId());
	}

	@Override
	public AttackDistribution compute(ComputeContext ctx)
	{
		HitDistribution standardDistribution = ctx.get(standardHitDistribution);

		HitDistribution ret = new HitDistribution();
		standardDistribution.scaleProbability(50.0 / 51.0)
			.forEach(ret::addOutcome);
		standardDistribution.forEach(h -> ret.addOutcome(new WeightedHit(
			h.getProbability() / 51.0,
			h.getHits().stream().map(i -> i * 3).collect(Collectors.toList())
		)));

		return new AttackDistribution(List.of(ret));
	}

}
