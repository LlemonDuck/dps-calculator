package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class KerisGearBonus implements GearBonusOperation
{

	private static final Set<Integer> KERIS_WEAPONS = ImmutableSet.of(
		ItemID.KERIS,
		ItemID.KERISP,
		ItemID.KERISP_10583,
		ItemID.KERISP_10584,
		ItemID.KERIS_PARTISAN,
		ItemID.KERIS_PARTISAN_OF_BREACHING
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		return ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isKalphite() &&
			ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.MELEE &&
			KERIS_WEAPONS.contains(ctx.get(weapon).getItemId());
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		if (ctx.get(weapon).getItemId() == ItemID.KERIS_PARTISAN_OF_BREACHING)
		{
			return GearBonus.symmetric(Multiplication.ofPercent(33));
		}

		return new GearBonus(null, Multiplication.ofPercent(33));
	}

}
