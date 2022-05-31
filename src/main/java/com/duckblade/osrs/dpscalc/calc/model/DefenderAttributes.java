package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class DefenderAttributes
{

	public static final DefenderAttributes EMPTY = DefenderAttributes.builder().build();

	@Builder.Default
	private final int npcId = -1;

	@Builder.Default
	private final String name = null;

	@Builder.Default
	private final boolean isDemon = false; // demonbane

	@Builder.Default
	private final boolean isDragon = false; // dhl/dhcb

	@Builder.Default
	private final boolean isKalphite = false; // keris

	@Builder.Default
	private final boolean isLeafy = false; // leaf-bladed

	@Builder.Default
	private final boolean isUndead = false; // salve

	@Builder.Default
	private final boolean isVampyre = false; // silver/vampyre-bane

	@Builder.Default
	private final int size = 1; // scythe

	@Builder.Default
	private final int accuracyMagic = 0; // tbow

}
