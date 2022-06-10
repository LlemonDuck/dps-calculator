package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.ammo.AmmoItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.ammo.AmmolessRangedAmmoItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.ammo.BlowpipeDartsItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.gearbonus.BlackMaskGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.CrystalGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.DragonHunterGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.GearBonusComputable;
import com.duckblade.osrs.dpscalc.calc.gearbonus.InquisitorsGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.KerisGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.LeafyGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.MageDemonbaneGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.MeleeDemonbaneGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.RevenantWeaponGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.SalveAmuletGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.SmokeBattlestaffGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.TbowGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.TomesGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.VampyreBaneGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.VoidGearBonus;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.CombatStyleImmunityMaxHitLimiter;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.MaxHitLimiter;
import com.duckblade.osrs.dpscalc.calc.maxhit.limiters.ZulrahMaxHitLimiter;
import com.duckblade.osrs.dpscalc.calc.multihit.DharoksDptComputable;
import com.duckblade.osrs.dpscalc.calc.multihit.KarilsDptComputable;
import com.duckblade.osrs.dpscalc.calc.multihit.KerisDptComputable;
import com.duckblade.osrs.dpscalc.calc.multihit.MultiHitDptComputable;
import com.duckblade.osrs.dpscalc.calc.multihit.ScytheDptComputable;
import com.duckblade.osrs.dpscalc.calc.multihit.VeracsDptComputable;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class DpsComputeModule extends AbstractModule
{

	@Override
	protected void configure()
	{
		Multibinder<AmmoItemStatsComputable> ammoItemStatsComputables = Multibinder.newSetBinder(binder(), AmmoItemStatsComputable.class);
		ammoItemStatsComputables.addBinding().to(AmmolessRangedAmmoItemStatsComputable.class);
		ammoItemStatsComputables.addBinding().to(BlowpipeDartsItemStatsComputable.class);

		Multibinder<GearBonusComputable> gearBonusComputables = Multibinder.newSetBinder(binder(), GearBonusComputable.class);
		gearBonusComputables.addBinding().to(BlackMaskGearBonus.class);
		gearBonusComputables.addBinding().to(CrystalGearBonus.class);
		gearBonusComputables.addBinding().to(MageDemonbaneGearBonus.class);
		gearBonusComputables.addBinding().to(DragonHunterGearBonus.class);
		gearBonusComputables.addBinding().to(InquisitorsGearBonus.class);
		gearBonusComputables.addBinding().to(KerisGearBonus.class);
		gearBonusComputables.addBinding().to(LeafyGearBonus.class);
		gearBonusComputables.addBinding().to(MeleeDemonbaneGearBonus.class);
		gearBonusComputables.addBinding().to(RevenantWeaponGearBonus.class);
		gearBonusComputables.addBinding().to(SalveAmuletGearBonus.class);
		gearBonusComputables.addBinding().to(SmokeBattlestaffGearBonus.class);
		gearBonusComputables.addBinding().to(TbowGearBonus.class);
		gearBonusComputables.addBinding().to(TomesGearBonus.class);
		gearBonusComputables.addBinding().to(VampyreBaneGearBonus.class);
		gearBonusComputables.addBinding().to(VoidGearBonus.class);

		Multibinder<MaxHitLimiter> maxHitLimiters = Multibinder.newSetBinder(binder(), MaxHitLimiter.class);
		maxHitLimiters.addBinding().to(CombatStyleImmunityMaxHitLimiter.class);
		maxHitLimiters.addBinding().to(ZulrahMaxHitLimiter.class);

		Multibinder<MultiHitDptComputable> multiHitDptComputables = Multibinder.newSetBinder(binder(), MultiHitDptComputable.class);
		multiHitDptComputables.addBinding().to(DharoksDptComputable.class);
		multiHitDptComputables.addBinding().to(KarilsDptComputable.class);
		multiHitDptComputables.addBinding().to(KerisDptComputable.class);
		multiHitDptComputables.addBinding().to(ScytheDptComputable.class);
		multiHitDptComputables.addBinding().to(VeracsDptComputable.class);

		bind(DptComputable.class).asEagerSingleton();
	}

}
