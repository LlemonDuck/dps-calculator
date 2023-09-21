package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PoweredStaffMaxHitComputable implements MagicMaxHitComputable
{

	private interface StaffMaxHitProvider extends Computable<Integer>
	{
	}

	private static final StaffMaxHitProvider SEAS = ctx ->
	{
		int magicLevel = ctx.get(ComputeInputs.ATTACKER_SKILLS).getTotals().get(Skill.MAGIC);
		return Math.max(1, (magicLevel - 75) / 3 + 20);
	};
	private static final StaffMaxHitProvider WARPED = ctx ->
	{
		int magicLevel = ctx.get(ComputeInputs.ATTACKER_SKILLS).getTotals().get(Skill.MAGIC);
		return Math.max(1, ((8 * magicLevel) + 96) / 37);
	};

	private static final StaffMaxHitProvider SWAMP = ctx -> SEAS.compute(ctx) + 3;
	private static final StaffMaxHitProvider SANGUINESTI = ctx -> SEAS.compute(ctx) + 4;
	private static final StaffMaxHitProvider SHADOW = ctx -> SEAS.compute(ctx) + 6;

	private static final Map<Integer, StaffMaxHitProvider> SPELL_MAP = ImmutableMap.<Integer, StaffMaxHitProvider>builder()
		.put(ItemID.TRIDENT_OF_THE_SEAS, SEAS)
		.put(ItemID.TRIDENT_OF_THE_SEAS_E, SEAS)
		.put(ItemID.TRIDENT_OF_THE_SEAS_FULL, SEAS)
		.put(ItemID.TRIDENT_OF_THE_SWAMP, SWAMP)
		.put(ItemID.TRIDENT_OF_THE_SWAMP_E, SWAMP)
		.put(ItemID.SANGUINESTI_STAFF, SANGUINESTI)
		.put(ItemID.HOLY_SANGUINESTI_STAFF, SANGUINESTI)
		.put(ItemID.TUMEKENS_SHADOW, SHADOW)
		.put(ItemID.CRYSTAL_STAFF_BASIC, ignored -> 23)
		.put(ItemID.CORRUPTED_STAFF_BASIC, ignored -> 23)
		.put(ItemID.CRYSTAL_STAFF_ATTUNED, ignored -> 31)
		.put(ItemID.CORRUPTED_STAFF_ATTUNED, ignored -> 31)
		.put(ItemID.CRYSTAL_STAFF_PERFECTED, ignored -> 39)
		.put(ItemID.CORRUPTED_STAFF_PERFECTED, ignored -> 39)
		.put(ItemID.WARPED_SCEPTRE, WARPED)
		.build();

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		return attackStyle.getAttackType() == AttackType.MAGIC && !attackStyle.isManualCast() &&
			context.get(weaponComputable).getWeaponCategory() == WeaponCategory.POWERED_STAFF;
	}

	@Override
	public Integer compute(ComputeContext context)
	{
		int weaponId = context.get(weaponComputable).getItemId();
		StaffMaxHitProvider staffMaxHitProvider = SPELL_MAP.get(weaponId);

		if (staffMaxHitProvider == null)
		{
			throw new IllegalArgumentException("Missing powered staff max hit provider for weapon id " + weaponId);
		}

		return staffMaxHitProvider.compute(context);
	}
}
