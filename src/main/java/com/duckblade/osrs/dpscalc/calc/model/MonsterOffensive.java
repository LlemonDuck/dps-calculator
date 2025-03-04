package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class MonsterOffensive
{

	@Builder.Default
	int atk = 0;

	@Builder.Default
	int magic = 0;

	@Builder.Default
	int magic_str = 0;

	@Builder.Default
	int ranged = 0;

	@Builder.Default
	int ranged_str = 0;

	@Builder.Default
	int str = 0;

}
