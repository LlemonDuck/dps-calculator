package com.duckblade.osrs.dpscalc.calc3.effects.gearbonus;

import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonus;
import com.duckblade.osrs.dpscalc.calc3.effects.gearbonus.GearBonusOperation;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Multiplication;
import com.duckblade.osrs.dpscalc.calc3.meta.math.Operation;
import com.duckblade.osrs.dpscalc.calc3.model.AttackType;
import com.duckblade.osrs.dpscalc.calc3.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc3.model.WeaponCategory;
import com.duckblade.osrs.dpscalc.calc3.util.Weapon;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChinchompaDistance implements GearBonusOperation
{

	@RequiredArgsConstructor
	@Getter
	private enum ChinchompaRange
	{
		SHORT(3, new Multiplication(1), new Multiplication(3, 4), new Multiplication(1, 2)),
		MEDIUM(6, new Multiplication(3, 4), new Multiplication(1), new Multiplication(3, 4)),
		LONG(Integer.MAX_VALUE, new Multiplication(1, 2), new Multiplication(3, 4), new Multiplication(1)),
		;

		private final int maxDistance;
		private final Operation shortFuseAccuracy;
		private final Operation mediumFuseAccuracy;
		private final Operation longFuseAccuracy;

		public static ChinchompaRange forRange(int range)
		{
			for (ChinchompaRange r : values())
			{
				if (range <= r.getMaxDistance())
				{
					return r;
				}
			}

			return ChinchompaRange.LONG;
		}
	}

	private static final Map<CombatStyle, Function<ChinchompaRange, Operation>> ACCURACY_BONUS_MAP = ImmutableMap.of(
		CombatStyle.ACCURATE, ChinchompaRange::getShortFuseAccuracy,
		CombatStyle.RAPID, ChinchompaRange::getMediumFuseAccuracy,
		CombatStyle.LONGRANGE, ChinchompaRange::getLongFuseAccuracy
	);

	private final Weapon weapon;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.RANGED &&
			context.get(weapon).getWeaponCategory() == WeaponCategory.CHINCHOMPAS;
	}

	@Override
	public GearBonus compute(ComputeContext context)
	{
		context.warn("Chinchompa calculation does not support splash damage. Results listed are for a single target.");

		Operation accuracy = ACCURACY_BONUS_MAP.get(context.get(ComputeInputs.ATTACK_STYLE).getCombatStyle())
			.apply(ChinchompaRange.forRange(context.get(ComputeInputs.ATTACK_DISTANCE)));
		return new GearBonus(accuracy, null);
	}
}
