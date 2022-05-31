package com.duckblade.osrs.dpscalc.calc.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ItemStats
{

	public static final ItemStats EMPTY = ItemStats.builder().build();

	@Builder.Default
	private final int itemId = -1;

	@Builder.Default
	private final String name = null;

	@Builder.Default
	private final int accuracyStab = 0;

	@Builder.Default
	private final int accuracySlash = 0;

	@Builder.Default
	private final int accuracyCrush = 0;

	@Builder.Default
	private final int accuracyMagic = 0;

	@Builder.Default
	private final int accuracyRanged = 0;

	@Builder.Default
	private final int strengthMelee = 0;

	@Builder.Default
	private final int strengthRanged = 0;

	@Builder.Default
	private final int strengthMagic = 0;

	@Builder.Default
	private final int prayer = 0;

	// start weapon stats
	@Builder.Default
	private final int speed = 4;

	@Builder.Default
	private final int slot = -1;

	@Builder.Default
	private final boolean is2h = false;

	@Builder.Default
	private final WeaponCategory weaponCategory = WeaponCategory.UNARMED;

}
