package com.duckblade.osrs.dpscalc.calc3.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class AttackStyle
{

	public static AttackStyle MANUAL_CAST = AttackStyle.builder()
		.varpValue(-1)
		.displayName("Manual Cast")
		.attackType(AttackType.MAGIC)
		.combatStyle(CombatStyle.AUTOCAST)
		.isManualCast(true)
		.build();

	@Builder.Default
	private final int varpValue = -1;

	@Builder.Default
	private final String displayName = null;

	private final AttackType attackType;

	@Builder.Default
	private final MeleeAttackType meleeAttackType = null;

	@Builder.Default
	private final CombatStyle combatStyle = null;

	@Builder.Default
	private final boolean isManualCast = false;

	public String toString()
	{
		return displayName;
	}

}