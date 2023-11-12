package com.duckblade.osrs.dpscalc.calc3.core.standard;

import com.duckblade.osrs.dpscalc.calc3.core.EffectiveStrengthLevel;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc3.util.EquipmentStats;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class StandardMaxHit implements ContextValue<Integer>
{

	private final EffectiveStrengthLevel effectiveStrengthLevel;
	private final EquipmentStats equipmentStats;
	private final List<GearBonusOperation> gearBonuses;

	@Inject
	public StandardMaxHit(
		EffectiveStrengthLevel effectiveStrengthLevel,
		EquipmentStats equipmentStats,
		@Named(GearBonusOperation.GEAR_BONUSES) List<GearBonusOperation> gearBonuses
	)
	{
		this.effectiveStrengthLevel = effectiveStrengthLevel;
		this.equipmentStats = equipmentStats;
		this.gearBonuses = gearBonuses;
	}

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int effectiveStr = ctx.get(effectiveStrengthLevel);
		int equipmentBonus = getEquipmentBonus(ctx);
		int maxHit = (effectiveStr * (equipmentBonus + 64) + 320) / 640;

		for (GearBonusOperation b : gearBonuses)
		{
			maxHit = b.applyStrength(ctx, maxHit);
		}

		return maxHit;
	}

	private int getEquipmentBonus(ComputeContext ctx)
	{
		AttackStyle attackStyle = ctx.get(ComputeInputs.ATTACK_STYLE);
		ItemStats equipment = ctx.get(equipmentStats);
		switch (attackStyle.getAttackType())
		{
			case MELEE:
				return equipment.getStrengthMelee();

			case RANGED:
				return equipment.getStrengthRanged();

			case MAGIC:
				return equipment.getStrengthMagic();
		}

		return 0;
	}

}
