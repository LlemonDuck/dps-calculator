package com.duckblade.osrs.dpscalc.plugin.osdata;

import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class NpcData
{
	private final Skills skills;
	private final DefensiveBonuses defensiveBonuses;
	private final DefenderAttributes attributes;
}
