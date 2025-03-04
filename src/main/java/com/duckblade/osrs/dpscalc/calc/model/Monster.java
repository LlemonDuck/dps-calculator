package com.duckblade.osrs.dpscalc.calc.model;

import java.util.EnumSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class Monster
{

	@Builder.Default
	int id = -1;

	@Builder.Default
	String name = "Custom Monster";

	@Builder.Default
	String version = null;

	@Builder.Default
	int size = 1;

	@Builder.Default
	MonsterSkills skills = MonsterSkills.builder().build();

	@Builder.Default
	MonsterOffensive offensive = MonsterOffensive.builder().build();

	@Builder.Default
	MonsterDefensive defensive = MonsterDefensive.builder().build();

	@Builder.Default
	Set<MonsterAttribute> attributes = EnumSet.noneOf(MonsterAttribute.class);

	@Builder.Default
	ElementalWeakness weakness = null;

	@Builder.Default
	MonsterInputs inputs = MonsterInputs.builder().build();

}
