package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.Spellbook;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SmokeBattlestaff implements GearBonusOperation
{

	private static final Set<Integer> SMOKE_BATTLESTAVES = ImmutableSet.of(
		ItemID.SMOKE_BATTLESTAFF,
		ItemID.MYSTIC_SMOKE_STAFF
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MAGIC &&
			SMOKE_BATTLESTAVES.contains(context.get(weapon).getItemId()) &&
			context.get(ComputeInputs.SPELL).getSpellbook() == Spellbook.STANDARD;
	}

	@Override
	public GearBonus compute(ComputeContext context)
	{
		return new GearBonus(new Multiplication(11, 10), new Multiplication(11, 10));
	}

}
