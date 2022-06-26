package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
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
public class MagicSalamanderMaxHitComputable implements MagicMaxHitComputable
{

	private static final Map<Integer, Integer> SALAMANDER_BASES = ImmutableMap.of(
		ItemID.SWAMP_LIZARD, 56,
		ItemID.ORANGE_SALAMANDER, 59,
		ItemID.RED_SALAMANDER, 77,
		ItemID.BLACK_SALAMANDER, 92
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		return attackStyle.getAttackType() == AttackType.MAGIC && !attackStyle.isManualCast() &&
			context.get(weaponComputable).getWeaponCategory() == WeaponCategory.SALAMANDER;
	}

	@Override
	public Integer compute(ComputeContext context)
	{
		int weaponId = context.get(weaponComputable).getItemId();
		Integer salamanderBase = SALAMANDER_BASES.get(weaponId);
		if (salamanderBase == null)
		{
			throw new IllegalArgumentException("Missing salamander base for weapon id " + weaponId);
		}

		Skills skills = context.get(ComputeInputs.ATTACKER_SKILLS);
		int magicLevel = skills.getTotals().get(Skill.MAGIC);

		return magicLevel * (salamanderBase + 64) / 640;
	}
}
