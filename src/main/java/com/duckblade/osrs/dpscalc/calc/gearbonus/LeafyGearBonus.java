package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
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
public class LeafyGearBonus implements GearBonusComputable
{

	private static final Set<Integer> LEAF_BLADED_WEAPONS = ImmutableSet.of(
		ItemID.LEAFBLADED_BATTLEAXE,
		ItemID.LEAFBLADED_SPEAR,
		ItemID.LEAFBLADED_SPEAR_4159, // unused id
		ItemID.LEAFBLADED_SWORD
	);

	// gets bonus
	private static final Set<Integer> LEAF_BLADED_ENHANCED = ImmutableSet.of(
		ItemID.LEAFBLADED_BATTLEAXE
	);

	private static final Set<Integer> LEAF_BLADED_AMMO = ImmutableSet.of(
		ItemID.BROAD_ARROWS, // also unused, i think
		ItemID.BROAD_ARROWS_4160,
		ItemID.BROAD_BOLTS,
		ItemID.AMETHYST_BROAD_BOLTS
	);

	private static final Set<Spell> LEAF_BLADED_SPELLS = ImmutableSet.of(
		Spell.MAGIC_DART
	);

	private final EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.DEFENDER_ATTRIBUTES).isLeafy();
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		// we have to ensure to 0 out damage here on failure paths
		// since leafy monsters are immune to non-leafy attacks
		switch (context.get(ComputeInputs.ATTACK_STYLE).getAttackType())
		{
			case MAGIC:
				Spell spell = context.get(ComputeInputs.SPELL);
				if (spell != null && LEAF_BLADED_SPELLS.contains(spell))
				{
					return GearBonuses.EMPTY;
				}

				context.warn("Leafy creatures are immune to magic spells other than Magic Dart.");
				return GearBonuses.symmetric(0);

			case RANGED:
				int ammo = context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.AMMO);
				if (LEAF_BLADED_AMMO.contains(ammo))
				{
					return GearBonuses.EMPTY;
				}

				context.warn("Leafy creatures are immune to ranged unless using broad bolts/arrows.");
				return GearBonuses.symmetric(0);

			default:
				int weapon = context.get(equipmentItemIdsComputable).get(EquipmentInventorySlot.WEAPON);
				if (LEAF_BLADED_WEAPONS.contains(weapon))
				{
					return LEAF_BLADED_ENHANCED.contains(weapon) ? GearBonuses.symmetric(1.175) : GearBonuses.EMPTY;
				}

				context.warn("Leafy creatures are immune to melee unless using a leaf-bladed weapon.");
				return GearBonuses.symmetric(0);
		}
	}

}
