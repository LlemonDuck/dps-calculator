package com.duckblade.osrs.dpscalc.calc3.meta;

import com.duckblade.osrs.dpscalc.calc3.core.Dpt;
import com.duckblade.osrs.dpscalc.calc3.dist.BaseHitDistribution;
import com.duckblade.osrs.dpscalc.calc3.dist.LinearDistribution;
import com.duckblade.osrs.dpscalc.calc3.dist.ScytheDistribution;
import com.duckblade.osrs.dpscalc.calc3.dist.transformers.TransformedHitDistribution;
import com.duckblade.osrs.dpscalc.calc3.dist.transformers.VerzikP1HitLimiter;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.BerserkerNecklace;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.BlackMask;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.ColossalBlade;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.DragonHunter;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.Golembane;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.Inquisitors;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.Keris;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.LeafBladedBattleAxe;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.MeleeDemonbane;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.ObsidianArmor;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.RevenantWeapons;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.SalveAmulet;
import com.duckblade.osrs.dpscalc.calc3.gearbonus.VampyreBane;
import com.duckblade.osrs.dpscalc.calc3.maxhit.MeleeMaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class DpsComputeModule3 extends AbstractModule
{

	@Override
	protected void configure()
	{
		bind(Dpt.class).asEagerSingleton();
	}

	@Provides
	@Named(MeleeMaxHit.NAME_GEAR_BONUSES)
	@Inject
	public List<GearBonusOperation> getMeleeMaxHitBonuses(
		SalveAmulet salveAmulet,
		BlackMask blackMask,
		MeleeDemonbane demonbane,
		ObsidianArmor obsidianArmour,
		BerserkerNecklace berserkerNecklace,
		Golembane golembane,
		DragonHunter dragonHunter,
		RevenantWeapons revenantWeapons,
		VampyreBane vampyreBane,
		LeafBladedBattleAxe leafBladedBattleAxe,
		ColossalBlade colossalBlade,
		Inquisitors inquisitors,
		Keris keris
	)
	{
		return ImmutableList.of(
			salveAmulet,
			blackMask,
			demonbane,
			obsidianArmour,
			berserkerNecklace,
			golembane,
			dragonHunter,
			revenantWeapons,
			vampyreBane,
			leafBladedBattleAxe,
			colossalBlade,
			inquisitors,
			keris
		);
	}

	@Provides
	@Named(BaseHitDistribution.WEAPON_HIT_DISTRIBUTIONS)
	@Inject
	public List<ContextValue<List<HitDistribution>>> getWeaponHitDistributions(
		ScytheDistribution scytheDistribution,
		LinearDistribution linearDistribution
	)
	{
		return Arrays.asList(
			scytheDistribution,
			linearDistribution
		);
	}

	@Provides
	@Named(TransformedHitDistribution.TRANSFORMERS)
	@Inject
	public List<TransformedHitDistribution.Transformer> getHitDistributionTransformers(
		VerzikP1HitLimiter verzikP1HitLimiter
	)
	{
		return Arrays.asList(
			verzikP1HitLimiter
		);
	}

}
