package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PoweredStaffMaxHitComputable implements Computable<Integer>
{

	private interface StaffMaxHitProvider extends Computable<Integer>
	{
	}

	private static int magicLevel(ComputeContext context)
	{
		Skills skills = context.get(ComputeInputs.ATTACKER_SKILLS);
		return skills.getTotals().get(Skill.MAGIC);
	}

	private static final StaffMaxHitProvider SEAS = ctx -> Math.max(1, (magicLevel(ctx) - 75) / 3 + 20);
	private static final StaffMaxHitProvider SWAMP = ctx -> SEAS.compute(ctx) + 3;
	private static final StaffMaxHitProvider SANGUINESTI = ctx -> SEAS.compute(ctx) + 4;
	private static final StaffMaxHitProvider MAGIC_DART = ctx -> magicLevel(ctx) / 10 + 10;
	private static final StaffMaxHitProvider MAGIC_DART_E = ctx ->
		ctx.get(ComputeInputs.ON_SLAYER_TASK) ? (magicLevel(ctx) / 6 + 13) : MAGIC_DART.compute(ctx);

	private static final Map<Integer, StaffMaxHitProvider> SPELL_MAP = ImmutableMap.<Integer, StaffMaxHitProvider>builder()
		.put(ItemID.TRIDENT_OF_THE_SEAS, SEAS)
		.put(ItemID.TRIDENT_OF_THE_SEAS_E, SEAS)
		.put(ItemID.TRIDENT_OF_THE_SEAS_FULL, SEAS)
		.put(ItemID.TRIDENT_OF_THE_SWAMP, SWAMP)
		.put(ItemID.TRIDENT_OF_THE_SWAMP_E, SWAMP)
		.put(ItemID.SANGUINESTI_STAFF, SANGUINESTI)
		.put(ItemID.HOLY_SANGUINESTI_STAFF, SANGUINESTI)
		.put(ItemID.SLAYERS_STAFF, MAGIC_DART)
		.put(ItemID.SLAYERS_STAFF_E, MAGIC_DART_E)
		.put(ItemID.CRYSTAL_STAFF_BASIC, ignored -> 23)
		.put(ItemID.CRYSTAL_STAFF_ATTUNED, ignored -> 31)
		.put(ItemID.CRYSTAL_STAFF_PERFECTED, ignored -> 39)
		.build();

	private final WeaponComputable weaponComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		int weaponId = context.get(weaponComputable).getItemId();
		StaffMaxHitProvider staffMaxHitProvider = SPELL_MAP.get(weaponId);

		if (staffMaxHitProvider == null)
		{
			throw new IllegalArgumentException("Missing powered staff max hit provider for item id " + weaponId);
		}

		return staffMaxHitProvider.compute(context);
	}
}
