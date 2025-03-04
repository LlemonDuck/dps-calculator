package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class EquipmentStats
{

	@Builder.Default
	PlayerCombatStyleStats offensive = PlayerCombatStyleStats.builder().build();

	@Builder.Default
	PlayerCombatStyleStats defensive = PlayerCombatStyleStats.builder().build();

	@Builder.Default
	PlayerBonuses bonuses = PlayerBonuses.builder().build();

	public EquipmentStats merge(EquipmentStats other)
	{
		return EquipmentStats.builder()
			.offensive(offensive.merge(other.offensive))
			.defensive(defensive.merge(other.defensive))
			.bonuses(bonuses.merge(other.bonuses))
			.build();
	}

}
