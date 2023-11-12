package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VampyreBane implements GearBonusOperation
{

	private static final Map<Integer, GearBonus> weaponToBonus = ImmutableMap.of(
		ItemID.IVANDIS_FLAIL, new GearBonus(null, Multiplication.ofPercent(20)),
		ItemID.BLISTERWOOD_SICKLE, new GearBonus(Multiplication.ofPercent(5), Multiplication.ofPercent(15)),
		ItemID.BLISTERWOOD_FLAIL, new GearBonus(Multiplication.ofPercent(5), Multiplication.ofPercent(25))
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext ctx)
	{
		int weaponId = ctx.get(weapon).getItemId();
		if (!weaponToBonus.containsKey(weaponId))
		{
			return false;
		}

		if (ctx.get(ComputeInputs.ATTACK_STYLE).getAttackType() != AttackType.MELEE)
		{
			return false;
		}

		if (!ctx.get(ComputeInputs.DEFENDER_ATTRIBUTES).isVampyre())
		{
			ctx.warn("Vampyrebane weapons against a non-vampyre target provide no bonuses.");
			return false;
		}

		return true;
	}

	@Override
	public GearBonus compute(ComputeContext ctx)
	{
		int weaponId = ctx.get(weapon).getItemId();
		return weaponToBonus.getOrDefault(weaponId, null);
	}
}
