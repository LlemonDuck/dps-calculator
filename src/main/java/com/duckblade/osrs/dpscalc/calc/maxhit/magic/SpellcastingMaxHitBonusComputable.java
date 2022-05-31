package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SpellcastingMaxHitBonusComputable implements Computable<Integer>
{

	private static final int CHAOS_GAUNTLETS = ItemID.CHAOS_GAUNTLETS;
	private static final Set<Spell> BOLT_SPELLS = ImmutableSet.of(
		Spell.WIND_BOLT,
		Spell.WATER_BOLT,
		Spell.EARTH_BOLT,
		Spell.FIRE_BOLT
	);

	public static final Set<Spell> GOD_SPELLS = ImmutableSet.of(
		Spell.CLAWS_OF_GUTHIX,
		Spell.SARADOMIN_STRIKE,
		Spell.FLAMES_OF_ZAMORAK
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		Spell spell = context.get(ComputeInputs.SPELL);
		if (BOLT_SPELLS.contains(spell))
		{
			if (CHAOS_GAUNTLETS == context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.GLOVES))
			{
				return 3;
			}
		}
		else if (GOD_SPELLS.contains(spell))
		{
			if (context.get(ComputeInputs.USING_CHARGE_SPELL))
			{
				return 10;
			}
		}

		return 0;
	}
}
