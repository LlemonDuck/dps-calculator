package com.duckblade.osrs.dpscalc.calc3.meta;

import com.duckblade.osrs.dpscalc.calc3.core.Accuracy;
import com.duckblade.osrs.dpscalc.calc3.core.AttackDist;
import com.duckblade.osrs.dpscalc.calc3.core.DefenceRoll;
import com.duckblade.osrs.dpscalc.calc3.core.DefenderStats;
import com.duckblade.osrs.dpscalc.calc3.core.EquipmentStats;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.core.standard.magicmaxhit.MageMaxHit;
import com.duckblade.osrs.dpscalc.calc3.core.standard.magicmaxhit.SpellbookMaxHit;
import com.duckblade.osrs.dpscalc.calc3.effects.accuracy.OsmumtensFangAccuracy;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.BerserkerNecklace;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.BlackMask;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.DragonHunter;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.Golembane;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.Inquisitors;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.Keris;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.LeafBladedBattleAxe;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.MeleeDemonbane;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.ObsidianArmor;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.RevenantWeapons;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.SalveAmulet;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.VampyreBane;
import com.duckblade.osrs.dpscalc.calc3.effects.hitdist.KerisDistribution;
import com.duckblade.osrs.dpscalc.calc3.effects.hitdist.OsmumtensFangDistribution;
import com.duckblade.osrs.dpscalc.calc3.effects.hitdist.ScytheDistribution;
import com.duckblade.osrs.dpscalc.calc3.effects.maxhit.ColossalBlade;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.math.outcomes.AttackDistribution;
import com.duckblade.osrs.dpscalc.calc3.model.DefensiveStats;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
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
			keris,
			revenantWeapons,
			vampyreBane,
			leafBladedBattleAxe,
			inquisitors
		);
	}

	@Provides
	@Named(AttackDist.ATTACK_DIST_PROVIDERS)
	@Inject
	public List<ContextValue<AttackDistribution>> getAttackDistributionProviders(
		KerisDistribution kerisDistribution,
		OsmumtensFangDistribution osmumtensFangDistribution,
		ScytheDistribution scytheDist
	)
	{
		return List.of(
			kerisDistribution,
			osmumtensFangDistribution,
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
		return List.of(
			osmumtensFangAccuracy
		);
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
	@Named(DefenderStats.DEFENSIVE_STATS_PROVIDERS)
	@Inject
	public List<ContextValue<DefensiveStats>> getDefensiveStatsProviders(
	)
	{
		return List.of();
	}

	@Provides
	@Named(EquipmentStats.EQUIPMENT_STATS_PROVIDERS)
	@Inject
	public List<ContextValue<ItemStats>> getEquipmentStatsProviders(
	)
	{
		return List.of();
	}

	@Provides
	@Named(MageMaxHit.MAGE_MAX_HIT_PROVIDERS)
	@Inject
	public List<ContextValue<Integer>> getMageMaxHitProviders(
	)
	{
		return List.of();
	}

	@Provides
	@Named(MaxHit.MAX_HIT_PROVIDERS)
	@Inject
	public List<ContextValue<Integer>> getMaxHitProviders(
		ColossalBlade colossalBlade,
		SpellbookMaxHit spellbookMaxHit
	)
	{
		return List.of(
			colossalBlade,
			spellbookMaxHit
		);
	}

}
