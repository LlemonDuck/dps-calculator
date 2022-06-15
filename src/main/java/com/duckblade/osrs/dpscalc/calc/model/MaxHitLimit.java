package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor(staticName = "of")
public class MaxHitLimit
{
	public static final MaxHitLimit UNLIMITED = new MaxHitLimit(Integer.MAX_VALUE, null);

	@Getter
	private final int limit;

	@Getter
	private final String warning;
}
