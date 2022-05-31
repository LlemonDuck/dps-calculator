package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs.DEFENDER_ATTRIBUTES;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class KerisGearBonus implements GearBonusComputable
{

	public static final Set<Integer> KERIS_IDS = ImmutableSet.of(
		ItemID.KERIS,
		ItemID.KERISP,
		ItemID.KERISP_10583,
		ItemID.KERISP_10584,
		ItemID.KERIS_PARTISAN,
		ItemID.KERIS_PARTISAN_OF_BREACHING
	);

	private static final GearBonuses KERIS_STRENGTH_BONUS = GearBonuses.of(1.0, 1.33);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		boolean wieldingKeris = KERIS_IDS.contains(context.get(weaponComputable).getItemId());
		boolean usingMelee = context.get(ComputeInputs.ATTACK_STYLE).getAttackType().isMelee();
		return wieldingKeris && usingMelee;
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		if (!context.get(DEFENDER_ATTRIBUTES).isKalphite())
		{
			return GearBonuses.EMPTY;
		}

		return KERIS_STRENGTH_BONUS;
	}

}
