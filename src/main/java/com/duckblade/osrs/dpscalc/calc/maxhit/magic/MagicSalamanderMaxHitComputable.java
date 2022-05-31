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
public class MagicSalamanderMaxHitComputable implements Computable<Integer>
{

	private static final Map<Integer, Integer> SALAMANDER_BASES = ImmutableMap.of(
		ItemID.SWAMP_LIZARD, 56,
		ItemID.ORANGE_SALAMANDER, 59,
		ItemID.RED_SALAMANDER, 77,
		ItemID.BLACK_SALAMANDER, 92
	);

	private final WeaponComputable weaponComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		int weaponId = context.get(weaponComputable).getItemId();
		Integer salamanderBase = SALAMANDER_BASES.get(weaponId);
		if (salamanderBase == null)
		{
			throw new IllegalArgumentException("Weapon id " + weaponId + " is not a salamander.");
		}

		Skills skills = context.get(ComputeInputs.ATTACKER_SKILLS);
		int magicLevel = skills.getTotals().get(Skill.MAGIC);

		return magicLevel * (salamanderBase + 64) / 640;
	}
}
