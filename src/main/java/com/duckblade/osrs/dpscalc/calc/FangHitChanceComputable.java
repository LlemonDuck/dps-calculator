package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.attack.AttackRollComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.DefenseRollComputable;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.Set;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FangHitChanceComputable implements Computable<Double>
{
	private final EquipmentItemIdsComputable equipmentItemIdsComputable;
	private final AttackRollComputable attackRollComputable;
	private final DefenseRollComputable defenseRollComputable;
	private final ToaArenaComputable toaArenaComputable;

	private static final Set<Integer> FANG_IDS = ImmutableSet.of(
		ItemID.OSMUMTENS_FANG,
		ItemID.OSMUMTENS_FANG_OR
	);

	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee() &&
			FANG_IDS.contains(context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.WEAPON));
	}

	@Override
	public Double compute(ComputeContext context)
	{
		int attRoll = context.get(attackRollComputable);
		int defRoll = context.get(defenseRollComputable);

		if (context.get(toaArenaComputable) == ToaArena.FIGHTING_OUTSIDE_TOA)
		{
			return hitChanceOutsideToa(attRoll, defRoll); // Roll only attack roll twice outside ToA
		}
		else
		{
			return hitChanceInsideToa(attRoll, defRoll); // Roll both attack and def rolls twice inside ToA
		}
	}

	private double hitChanceInsideToa(int attRoll, int defRoll)
	{
		double baseHitChance;

		if (attRoll > defRoll)
		{
			baseHitChance = 1.0 - ((defRoll + 2.0) / (2.0 * (attRoll + 1.0)));
		}
		else
		{
			baseHitChance = attRoll / (2.0 * (defRoll + 1.0));
		}

		return 1 - Math.pow(1 - baseHitChance, 2);
	}

	private double hitChanceOutsideToa(int attRoll, int defRoll)
	{
		if (attRoll > defRoll)
		{
			return 1.0 - ((defRoll + 2.0) * (2 * defRoll + 3.0) / (6.0 * Math.pow(attRoll + 1.0, 2.0)));
		}
		else
		{
			return attRoll * (4 * attRoll + 5.0) / (6.0 * (attRoll + 1.0) * (defRoll + 1.0));
		}
	}
}
