package com.duckblade.osrs.dpscalc.calc3.effects.hitdist;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardHitDistributions;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.AttackOutcome;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class KerisHitDist implements ContextValue<List<HitDistribution>>
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
	private final StandardHitDistributions standardHitDistributions;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isKalphite() &&
			ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE &&
			KERIS_WEAPONS.contains(ctx.get(weapon).getItemId());
	}

	@Override
	public List<HitDistribution> compute(ComputeContext ctx)
	{
		// two assumptions made here: standard distribution is a single dist, and keris only hits once
		HitDistribution standardDistribution = ctx.get(standardHitDistributions).get(0);
		HitDistribution typicalHitDistribution = standardDistribution.scale(50.0 / 51.0);

		HitDistribution tripleDamageDist = new HitDistribution();
		for (AttackOutcome o : standardDistribution)
		{
			tripleDamageDist.addOutcome(new AttackOutcome(
				o.getProbability() / 51.0,
				Collections.singletonList(o.getHits().get(0) * 3)
			));
		}

		return List.of(
			typicalHitDistribution,
			tripleDamageDist
		);
	}

}
