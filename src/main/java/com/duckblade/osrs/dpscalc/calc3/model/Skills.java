package com.duckblade.osrs.dpscalc.calc3.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
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

	public Skills.SkillsBuilder toBuilderDeep()
	{
		return toBuilder()
			.boosts(new HashMap<>(boosts))
			.levels(new HashMap<>(levels));
	}

	public Map<Skill, Integer> getTotals()
	{
		// when using jacksonized data, these can be null
		Map<Skill, Integer> levels = this.levels != null ? this.levels : Collections.emptyMap();
		Map<Skill, Integer> boosts = this.boosts != null ? this.boosts : Collections.emptyMap();
		return Arrays.stream(Skill.values())
			.collect(Collectors.toMap(
				skill -> skill,
				skill -> levels.getOrDefault(skill, 0) + boosts.getOrDefault(skill, 0)
			));
	}

}
