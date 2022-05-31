package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
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
public class TomesGearBonus implements GearBonusComputable
{

	private static final Set<Integer> TOME_OF_FIRE = ImmutableSet.of(
		ItemID.TOME_OF_FIRE
	);

	private static final Set<Spell> FIRE_SPELLS = ImmutableSet.of(
		Spell.FIRE_STRIKE,
		Spell.FIRE_BOLT,
		Spell.FIRE_BLAST,
		Spell.FIRE_WAVE,
		Spell.FIRE_SURGE
	);

	private static final Set<Integer> TOME_OF_WATER = ImmutableSet.of(
		ItemID.TOME_OF_WATER
	);

	private static final Set<Spell> WATER_SPELLS = ImmutableSet.of(
		Spell.WATER_STRIKE,
		Spell.WATER_BOLT,
		Spell.WATER_BLAST,
		Spell.WATER_WAVE,
		Spell.WATER_SURGE
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		int offHand = context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.SHIELD);
		boolean usingMagic = context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC;
		return usingMagic && (TOME_OF_FIRE.contains(offHand) || TOME_OF_WATER.contains(offHand));
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		int offHand = context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.SHIELD);
		Spell spell = context.get(ComputeInputs.SPELL);
		if (TOME_OF_FIRE.contains(offHand) && FIRE_SPELLS.contains(spell))
		{
			return GearBonuses.of(1.0, 1.5);
		}
		else if (TOME_OF_WATER.contains(offHand) && WATER_SPELLS.contains(spell))
		{
			return GearBonuses.symmetric(1.2);
		}

		return GearBonuses.EMPTY;
	}

}
