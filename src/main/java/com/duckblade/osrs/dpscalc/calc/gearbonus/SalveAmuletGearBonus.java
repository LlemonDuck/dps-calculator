package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SalveAmuletGearBonus implements GearBonusComputable
{

	private static final Set<Integer> SALVE_BASE = ImmutableSet.of(
		ItemID.SALVE_AMULET
	);

	private static final Set<Integer> SALVE_IMBUED = ImmutableSet.of(
		ItemID.SALVE_AMULETI,
		ItemID.SALVE_AMULETI_25250,
		ItemID.SALVE_AMULETI_26763,
		ItemID.SALVE_AMULETEI,
		ItemID.SALVE_AMULETEI_25278,
		ItemID.SALVE_AMULETEI_26782
	);

	private static final Set<Integer> SALVE_ENHANCED = ImmutableSet.of(
		ItemID.SALVE_AMULET_E,
		ItemID.SALVE_AMULETEI,
		ItemID.SALVE_AMULETEI_25278,
		ItemID.SALVE_AMULETEI_26782
	);

	private static final Set<Integer> SALVE_ALL = Sets.union(SALVE_IMBUED, Sets.union(SALVE_ENHANCED, SALVE_BASE));

	private static final GearBonuses UNENHANCED_MELEE_RANGED = GearBonuses.symmetric(7.0 / 6.0);
	private static final GearBonuses UNENHANCED_MAGE = GearBonuses.symmetric(1.15);
	private static final GearBonuses ENHANCED = GearBonuses.symmetric(6.0 / 5.0);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return SALVE_ALL.contains(context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.AMULET));
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		DefenderAttributes attributes = context.get(ComputeInputs.DEFENDER_ATTRIBUTES);
		if (!attributes.isUndead())
		{
			context.warn("Salve amulet against a non-undead target provides no bonuses.");
			return GearBonuses.EMPTY;
		}

		int amulet = context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.AMULET);
		AttackType attackType = context.get(ComputeInputs.ATTACK_STYLE).getAttackType();
		GearBonuses bonus = salveBonus(attackType, amulet);
		if (bonus == GearBonuses.EMPTY)
		{
			context.warn("Unimbued salve amulets provide no bonuses for mage/ranged.");
		}

		return bonus;
	}

	private GearBonuses salveBonus(AttackType attackType, int amulet)
	{
		boolean imbued = SALVE_IMBUED.contains(amulet);
		boolean enhanced = SALVE_ENHANCED.contains(amulet);
		switch (attackType)
		{
			case MAGIC:
				if (imbued)
				{
					if (enhanced)
					{
						return ENHANCED;
					}
					return UNENHANCED_MAGE;
				}
				return GearBonuses.EMPTY;

			case RANGED:
				if (imbued)
				{
					if (enhanced)
					{
						return ENHANCED;
					}
					return UNENHANCED_MELEE_RANGED;
				}
				return GearBonuses.EMPTY;

			default:
				if (enhanced)
				{
					return ENHANCED;
				}
				return UNENHANCED_MELEE_RANGED;
		}
	}

}
