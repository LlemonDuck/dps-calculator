package com.duckblade.osrs.dpscalc.calc.model;

import static com.duckblade.osrs.dpscalc.calc.Constants.DEFAULT_ATTACK_SPEED;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class EquipmentPiece
{

	String name;
	int id;

	@Builder.Default
	PlayerCombatStyleStats offensive = PlayerCombatStyleStats.builder().build();

	@Builder.Default
	PlayerCombatStyleStats defensive = PlayerCombatStyleStats.builder().build();

	@Builder.Default
	PlayerBonuses bonuses = PlayerBonuses.builder().build();

	@Builder.Default
	String version = null;

	String slot; // todo EIS?

	@Builder.Default
	int speed = DEFAULT_ATTACK_SPEED;

	@Builder.Default
	EquipmentCategory category = EquipmentCategory.UNARMED;

	@Builder.Default
	boolean isTwoHanded = false;

	@Builder.Default
	ItemVars vars = null;

	public EquipmentStats getStats()
	{
		return EquipmentStats.builder()
			.offensive(offensive)
			.defensive(defensive)
			.bonuses(bonuses)
			.build();
	}
}
