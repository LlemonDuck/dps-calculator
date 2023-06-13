package com.duckblade.osrs.dpscalc.calc3.maxhit;

import com.duckblade.osrs.dpscalc.calc3.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.util.EquipmentStats;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class MeleeMaxHit implements ContextValue<Integer>
{

	private final EffectiveStrength effectiveStrength;
	private final EquipmentStats equipmentStats;

	public static final String NAME_GEAR_BONUSES = "MeleeMaxHitGearBonuses";
	private final List<GearBonusOperation> meleeMaxHitGearBonuses;

	@Inject
	public MeleeMaxHit(
		EffectiveStrength effectiveStrength,
		EquipmentStats equipmentStats,
		@Named(NAME_GEAR_BONUSES) List<GearBonusOperation> meleeMaxHitGearBonuses
	)
	{
		this.effectiveStrength = effectiveStrength;
		this.equipmentStats = equipmentStats;
		this.meleeMaxHitGearBonuses = meleeMaxHitGearBonuses;
	}

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int effectiveStr = ctx.get(effectiveStrength);
		int equipmentBonus = ctx.get(equipmentStats).getStrengthMelee();
		int maxHit = (effectiveStr * (equipmentBonus + 64) + 320) / 640;

		for (GearBonusOperation b : meleeMaxHitGearBonuses)
		{
			maxHit = b.applyStrength(ctx, maxHit);
		}

		return maxHit;
	}

}
