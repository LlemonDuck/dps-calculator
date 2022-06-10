package com.duckblade.osrs.dpscalc.calc.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import net.runelite.api.EquipmentInventorySlot;

// utility class for populating ComputeContext without #put
// unlike ComputeInputs, this class provides defaults for ALL values
@Value
@Jacksonized
@Builder(toBuilder = true)
public class ComputeInput
{

	// attacker
	@Builder.Default
	private final Skills attackerSkills = Skills.EMPTY;

	@Singular
	private final Map<EquipmentInventorySlot, ItemStats> attackerItems;

	@Singular
	private final Set<Prayer> attackerPrayers;

	@Builder.Default
	private final AttackStyle attackStyle = WeaponCategory.UNARMED.getAttackStyles().get(0);

	@Builder.Default
	private final Spell spell = null;

	@Builder.Default
	private final ItemStats blowpipeDarts = ItemStats.EMPTY;

	@Builder.Default
	private final int attackerDistance = 1;

	// defender
	@Builder.Default
	private final Skills defenderSkills = Skills.EMPTY;

	@Builder.Default
	private final DefensiveBonuses defenderBonuses = DefensiveBonuses.EMPTY;

	@Builder.Default
	private final DefenderAttributes defenderAttributes = DefenderAttributes.EMPTY;

	// extras
	@Builder.Default
	private final boolean onSlayerTask = false;

	@Builder.Default
	private final boolean usingChargeSpell = false;

	@Builder.Default
	private final boolean usingMarkOfDarkness = false;

	@Builder.Default
	private final boolean inWilderness = false;

	@Builder.Default
	private final int raidPartySize = 1;

	public ComputeInputBuilder toBuilderDeep()
	{
		return toBuilder()
			.attackerSkills(attackerSkills.toBuilderDeep().build())
			.attackerItems(new HashMap<>(attackerItems))
			.attackerPrayers(new HashSet<>(attackerPrayers))
			.attackStyle(attackStyle.toBuilder().build())
			.defenderSkills(defenderSkills.toBuilderDeep().build())
			.defenderBonuses(defenderBonuses.toBuilder().build())
			.defenderAttributes(defenderAttributes.toBuilder().build());
	}
}
