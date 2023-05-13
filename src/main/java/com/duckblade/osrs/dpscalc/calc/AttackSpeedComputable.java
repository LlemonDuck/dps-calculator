package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.*;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AttackSpeedComputable implements Computable<Integer>
{

	private static final Set<Integer> FOUR_TICK_MAGIC_WEAPONS = ImmutableSet.of(ItemID.HARMONISED_NIGHTMARE_STAFF);

	private final WeaponComputable weaponComputable;

	@Override
	public Integer compute(ComputeContext context)
	{
		ItemStats weapon = context.get(weaponComputable);
		AttackStyle attackStyle = context.get(ComputeInputs.ATTACK_STYLE);
		switch (attackStyle.getAttackType())
		{
			// mage weapons' "speed" value is their melee speed, and spellbook spells are always 4 tick, except harm effect
			case MAGIC:
				if ((weapon.getWeaponCategory() == WeaponCategory.POWERED_STAFF || weapon.getWeaponCategory() == WeaponCategory.SALAMANDER) && !attackStyle.isManualCast())
				{
					return weapon.getSpeed();
				}

				// harm effect only works on standard spellbook
				Spell spell = context.get(ComputeInputs.SPELL);
				if (FOUR_TICK_MAGIC_WEAPONS.contains(weapon.getItemId()) &&
					spell != null && spell.getSpellbook() == Spellbook.STANDARD)
				{
					return 4;
				}

				return 5;

			case RANGED:
				if (attackStyle.getCombatStyle() == CombatStyle.RAPID)
				{
					return weapon.getSpeed() - 1;
				}

			default:
				return weapon.getSpeed();
		}
	}

}
