package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc3.util.EquipmentIds;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SalveAmulet implements GearBonusOperation
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

	private static final GearBonus UNENHANCED_MELEE_RANGED = GearBonus.symmetric(new Multiplication(7, 6));
	private static final GearBonus UNENHANCED_MAGE = GearBonus.symmetric(new Multiplication(23, 20));
	private static final GearBonus ENHANCED = GearBonus.symmetric(new Multiplication(6, 5));

	private final EquipmentIds equipmentIds;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		DefenderAttributes attributes = ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES);
		if (!attributes.isUndead())
		{
			ctx.warn("Salve amulet against a non-undead target provides no bonuses.");
			return false;
		}

		return SALVE_ALL.contains(ctx.get(equipmentIds).get(EquipmentInventorySlot.AMULET));
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		int amulet = ctx.get(equipmentIds).get(EquipmentInventorySlot.AMULET);
		AttackType attackType = ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType();
		GearBonus bonus = salveBonus(attackType, amulet);

		if (bonus == null)
		{
			ctx.warn("Unimbued salve amulets provide no bonuses for mage/ranged.");
		}

		return bonus;
	}

	private GearBonus salveBonus(AttackType attackType, int amulet)
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
				return null;

			case RANGED:
				if (imbued)
				{
					if (enhanced)
					{
						return ENHANCED;
					}
					return UNENHANCED_MELEE_RANGED;
				}
				return null;

			default:
				if (enhanced)
				{
					return ENHANCED;
				}
				return UNENHANCED_MELEE_RANGED;
		}
	}

}
