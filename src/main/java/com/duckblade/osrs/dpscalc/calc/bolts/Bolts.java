package com.duckblade.osrs.dpscalc.calc.bolts;

import com.duckblade.osrs.dpscalc.calc.dist.HitDistribution;
import com.duckblade.osrs.dpscalc.calc.dist.HitTransformer;
import com.duckblade.osrs.dpscalc.calc.dist.Hitsplat;
import com.duckblade.osrs.dpscalc.calc.dist.WeightedHit;
import com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute;
import com.google.common.collect.ImmutableList;
import java.util.Set;

public class Bolts
{

	private static double kandarinFactor(BoltContext ctx)
	{
		return ctx.isKandarinDiary() ? 1.1 : 1.0;
	}

	private static HitTransformer bonusDamageTransform(BoltContext ctx, double chance, int bonusDmg, boolean accurateOnly)
	{
		return h ->
		{
			if (h.isAccurate() && ctx.isZcb() && ctx.isSpec())
			{
				return HitDistribution.single(1.0, new Hitsplat(h.getDamage() + bonusDmg));
			}
			if (!h.isAccurate() && accurateOnly)
			{
				return new HitDistribution(new WeightedHit(1.0, h));
			}

			return new HitDistribution(
				new WeightedHit(chance, new Hitsplat(h.getDamage() + bonusDmg, h.isAccurate())),
				new WeightedHit(1 - chance, new Hitsplat(h.getDamage(), h.isAccurate()))
			);
		};
	}

	public static HitTransformer opalBolts(BoltContext ctx)
	{
		double chance = 0.05 * kandarinFactor(ctx);
		int bonusDmg = ctx.getRangedLvl() / (ctx.isZcb() ? 9 : 10);

		return bonusDamageTransform(ctx, chance, bonusDmg, false);
	}

	public static HitTransformer pearlBolts(BoltContext ctx)
	{
		double chance = 0.06 * kandarinFactor(ctx);
		int divisor = ctx.getMonster().getAttributes().contains(MonsterAttribute.FIERY) ? 15 : 20;
		int bonusDmg = ctx.getRangedLvl() / (ctx.isZcb() ? (divisor - 2) : divisor);

		return bonusDamageTransform(ctx, chance, bonusDmg, false);
	}

	public static HitTransformer diamondBolts(BoltContext ctx)
	{
		double chance = 0.1 * kandarinFactor(ctx);
		int effectMax = ctx.getMaxHit() * (ctx.isZcb() ? 126 : 115) / 100;

		HitDistribution effectDist = HitDistribution.linear(1.0, 0, effectMax);
		return h ->
		{
			if (h.isAccurate() && ctx.isZcb() && ctx.isSpec())
			{
				return effectDist;
			}
			return new HitDistribution(
				ImmutableList.<WeightedHit>builder()
					.addAll(effectDist.scaleProbability(chance).getHits())
					.add(new WeightedHit(1 - chance, new Hitsplat(h.getDamage(), h.isAccurate())))
					.build()
			);
		};
	}

	public static HitTransformer dragonstoneBolts(BoltContext ctx)
	{
		Set<MonsterAttribute> mattrs = ctx.getMonster().getAttributes();
		if (mattrs.contains(MonsterAttribute.FIERY) || mattrs.contains(MonsterAttribute.DRAGON))
		{
			// immune to dragonfire
			return h -> new HitDistribution(new WeightedHit(1.0, h));
		}

		double chance = 0.06 * kandarinFactor(ctx);
		int bonusDmg = ctx.getRangedLvl() * 2 / (ctx.isZcb() ? 9 : 10);

		return bonusDamageTransform(ctx, chance, bonusDmg, false);
	}

	public static HitTransformer onyxBolts(BoltContext ctx)
	{
		Set<MonsterAttribute> mattrs = ctx.getMonster().getAttributes();
		if (mattrs.contains(MonsterAttribute.UNDEAD))
		{
			// immune to life leech
			return h -> new HitDistribution(new WeightedHit(1.0, h));
		}

		double chance = 0.11 * kandarinFactor(ctx);
		int effectMax = ctx.getMaxHit() * (ctx.isZcb() ? 132 : 120) / 100;

		HitDistribution effectDist = HitDistribution.linear(1.0, 0, effectMax);
		return h ->
		{
			if (!h.isAccurate())
			{
				return new HitDistribution(new WeightedHit(1.0, h));
			}
			if (ctx.isZcb() && ctx.isSpec())
			{
				return effectDist;
			}
			return new HitDistribution(
				ImmutableList.<WeightedHit>builder()
					.addAll(effectDist.scaleProbability(chance).getHits())
					.add(new WeightedHit(1 - chance, h))
					.build()
			);
		};
	}

	public static HitTransformer rubyBolts(BoltContext ctx)
	{
		double chance = 0.06 * kandarinFactor(ctx);
		int cap = ctx.isZcb() ? 110 : 100;
		int effectDmg = ctx.getMonster().getInputs().getCurrentHp() * (ctx.isZcb() ? 22 : 20) / 100;
		HitDistribution effectHit = HitDistribution.single(1.0, new Hitsplat(Math.min(cap, effectDmg)));

		return h ->
		{
			if (h.isAccurate() && ctx.isZcb() && ctx.isSpec())
			{
				return effectHit;
			}
			return new HitDistribution(
				ImmutableList.<WeightedHit>builder()
					.addAll(effectHit.scaleProbability(chance).getHits())
					.add(new WeightedHit(1 - chance, h))
					.build()
			);
		};
	}

}
