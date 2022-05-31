package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.gearbonus.AggregateGearBonusesComputable;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MageMaxHitComputable implements Computable<Integer>
{

	private final AttackerItemStatsComputable attackerItemStatsComputable;
	private final AggregateGearBonusesComputable aggregateGearBonusesComputable;

	private final PoweredStaffMaxHitComputable poweredStaffMaxHitComputable;
	private final MagicSalamanderMaxHitComputable magicSalamanderMaxHitComputable;
	private final SpellcastingMaxHitBonusComputable spellcastingMaxHitBonusComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		boolean manualCasting = context.get(ComputeInputs.ATTACK_STYLE).isManualCast();

		ItemStats attackerItemStats = context.get(attackerItemStatsComputable);
		double magDmgBonus = 1 + attackerItemStats.getStrengthMagic() / 100.0;
		double gearBonus = context.get(aggregateGearBonusesComputable).getStrengthBonus();
		double bonus = magDmgBonus * gearBonus;

		switch (attackerItemStats.getWeaponCategory())
		{
			case POWERED_STAFF:
				if (!manualCasting)
				{
					return (int) (context.get(poweredStaffMaxHitComputable) * bonus);
				}

			case SALAMANDER:
				if (!manualCasting)
				{
					return (int) (context.get(magicSalamanderMaxHitComputable) * bonus);
				}

			default:
				int baseMaxHit = context.get(ComputeInputs.SPELL).getBaseMaxHit();
				int spellBonus = context.get(spellcastingMaxHitBonusComputable);
				return (int) ((baseMaxHit + spellBonus) * bonus);
		}
	}
}
