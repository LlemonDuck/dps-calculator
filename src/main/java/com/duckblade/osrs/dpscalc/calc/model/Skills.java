package com.duckblade.osrs.dpscalc.calc.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import net.runelite.api.Skill;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class Skills
{

	public static final Skills EMPTY = Skills.builder().build();

	@Singular
	private final Map<Skill, Integer> levels;

	@Singular
	private final Map<Skill, Integer> boosts;

	@Getter(lazy = true)
	private final Map<Skill, Integer> totals = Arrays.stream(Skill.values())
		.collect(Collectors.toMap(
			skill -> skill,
			skill -> getLevels().getOrDefault(skill, 0) + getBoosts().getOrDefault(skill, 0)
		));

	public SkillsBuilder toBuilderDeep()
	{
		return toBuilder()
			.boosts(new HashMap<>(boosts))
			.levels(new HashMap<>(levels));
	}

}
