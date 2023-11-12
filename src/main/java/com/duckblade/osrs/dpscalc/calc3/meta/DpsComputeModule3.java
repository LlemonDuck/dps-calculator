package com.duckblade.osrs.dpscalc.calc3.meta;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.DefenceRoll;
import com.duckblade.osrs.dpscalc.calc3.core.HitDistributions;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.core.standard.SpellbookMaxHit;
import com.duckblade.osrs.dpscalc.calc3.dist.ScytheDist;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.BerserkerNecklace;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.BlackMask;
import com.duckblade.osrs.dpscalc.calc3.effects.maxhit.ColossalBlade;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.DragonHunter;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.Golembane;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.Inquisitors;
import com.duckblade.osrs.dpscalc.calc3.effects.hitdist.KerisHitDist;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.LeafBladedBattleAxe;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.MeleeDemonbane;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.ObsidianArmor;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.RevenantWeapons;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.SalveAmulet;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.VampyreBane;
import com.duckblade.osrs.dpscalc.calc3.effects.accuracy.OsmumtensFangAccuracy;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.HitDistribution;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class DpsComputeModule3 extends AbstractModule
{

	@Override
	protected void configure()
	{
//		bind(Dpt.class).asEagerSingleton();
	}

	@Provides
	@Named(GearBonusOperation.GEAR_BONUSES)
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
		KerisHitDist kerisHitDist
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
			kerisHitDist
		);
	}

	@Provides
	@Named(HitDistributions.HIT_DIST_PROVIDERS)
	@Inject
	public List<ContextValue<List<HitDistribution>>> getWeaponHitDistributions(
		ScytheDist scytheDist
	)
	{
		return List.of(
			scytheDist
		);
	}

	@Provides
	@Named(Accuracy.ACCURACY_PROVIDERS)
	@Inject
	public List<ContextValue<Double>> getAccuracyProviders(
		OsmumtensFangAccuracy osmumtensFangAccuracy
	)
	{
		return List.of(osmumtensFangAccuracy);
	}

	@Provides
	@Named(DefenceRoll.DEFENCE_ROLL_PROVIDERS)
	@Inject
	public List<ContextValue<Integer>> getDefenceRollProviders(
	)
	{
		return List.of();
	}

	@Provides
	@Named(MaxHit.MAX_HIT_PROVIDERS)
	@Inject
	public List<ContextValue<Integer>> getMaxHitProviders(
		SpellbookMaxHit spellbookMaxHit
	)
	{
		return List.of(
			spellbookMaxHit
		);
	}

}
