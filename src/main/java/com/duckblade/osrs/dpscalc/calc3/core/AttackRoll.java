package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class AttackRoll implements ContextValue<Integer>
{

	private final EffectiveAccuracyLevel effectiveAccuracyLevel;
	private final EquipmentStats equipmentStats;
	private final List<GearBonusOperation> gearBonuses;

	@Inject
	public AttackRoll(
		EffectiveAccuracyLevel effectiveAccuracyLevel,
		EquipmentStats equipmentStats,
		@Named(GearBonusOperation.GEAR_BONUSES) List<GearBonusOperation> gearBonuses
	)
	{
		this.effectiveAccuracyLevel = effectiveAccuracyLevel;
		this.equipmentStats = equipmentStats;
		this.gearBonuses = gearBonuses;
	}

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int effectiveAtk = ctx.get(effectiveAccuracyLevel);
		int equipmentBonus = getAccuracyBonus(ctx);
		int atkRoll = effectiveAtk * (equipmentBonus + 64);

		for (GearBonusOperation b : gearBonuses)
		{
			atkRoll = b.applyAccuracy(ctx, atkRoll);
		}

		return atkRoll;
	}

	private int getAccuracyBonus(ComputeContext ctx)
	{
		AttackStyle attackStyle = ctx.get(ComputeInputs.ATTACK_STYLE);
		ItemStats equipment = ctx.get(equipmentStats);
		switch (attackStyle.getAttackType())
		{
			case MELEE:
				switch (attackStyle.getMeleeAttackType())
				{
					case STAB:
						return equipment.getAccuracyStab();

					case SLASH:
						return equipment.getAccuracySlash();

					case CRUSH:
						return equipment.getAccuracyCrush();
				}

			case RANGED:
				return equipment.getAccuracyRanged();

			case MAGIC:
				return equipment.getAccuracyMagic();
		}

		return 0;
	}

}
