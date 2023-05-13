package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.calc.model.Spellbook;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SmokeBattlestaffGearBonus implements GearBonusComputable
{

	private static final Set<Integer> SMOKE_BATTLESTAVES = ImmutableSet.of(
		ItemID.SMOKE_BATTLESTAFF,
		ItemID.MYSTIC_SMOKE_STAFF
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		Spell spell = context.get(ComputeInputs.SPELL);
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC &&
			SMOKE_BATTLESTAVES.contains(context.get(weaponComputable).getItemId()) &&
			spell != null &&
			spell.getSpellbook() == Spellbook.STANDARD;
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		return GearBonuses.symmetric(1.1);
	}

}
