package com.duckblade.osrs.dpscalc.calc.model;

import static com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory.UNARMED;
import static com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory.getCombatStylesForCategory;
import java.util.EnumSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class Player
{

	@Builder.Default
	PlayerCombatStyle style = getCombatStylesForCategory(UNARMED).get(0);

	@Builder.Default
	PlayerSkills skills = PlayerSkills.MAXED;

	@Builder.Default
	PlayerSkills boosts = PlayerSkills.builder().build();

	@Builder.Default
	PlayerEquipment equipment = PlayerEquipment.builder().build();

	@Builder.Default
	Integer attackSpeed = null; // for manual override only

	@Builder.Default
	Set<Prayer> prayers = EnumSet.noneOf(Prayer.class);

	@Builder.Default
	PlayerBuffs buffs = PlayerBuffs.builder().build();

	@Builder.Default
	Spell spell = null;

	public PlayerSkills getSkillTotals()
	{
		return skills.add(boosts);
	}

}
