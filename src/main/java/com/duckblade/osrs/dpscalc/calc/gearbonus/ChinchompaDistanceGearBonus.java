package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChinchompaDistanceGearBonus implements GearBonusComputable
{

	@RequiredArgsConstructor
	@Getter
	private enum ChinchompaRange
	{
		SHORT(3, 1.0, 0.75, 0.5),
		MEDIUM(6, 0.75, 1.0, 0.75),
		LONG(Integer.MAX_VALUE, 0.5, 0.75, 1.0),
		;

		private final int maxDistance;
		private final double shortFuseAccuracy;
		private final double mediumFuseAccuracy;
		private final double longFuseAccuracy;

		public static ChinchompaRange forRange(int range)
		{
			// noinspection OptionalGetWithoutIsPresent
			return Arrays.stream(values())
				.filter(r -> range <= r.getMaxDistance())
				.findFirst()
				.get();
		}
	}

	private static final Map<CombatStyle, ToDoubleFunction<ChinchompaRange>> ACCURACY_BONUS_MAP = ImmutableMap.of(
		CombatStyle.ACCURATE, ChinchompaRange::getShortFuseAccuracy,
		CombatStyle.RAPID, ChinchompaRange::getMediumFuseAccuracy,
		CombatStyle.LONGRANGE, ChinchompaRange::getLongFuseAccuracy
	);

	private final WeaponComputable weaponComputable;

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return context.get(ComputeInputs.ATTACK_STYLE).getAttackType() == AttackType.RANGED &&
			context.get(weaponComputable).getWeaponCategory() == WeaponCategory.CHINCHOMPAS;
	}

	@Override
	public GearBonuses compute(ComputeContext context)
	{
		context.warn("Chinchompa calculation does not support splash damage. Results listed are for a single target.");

		double accuracy = ACCURACY_BONUS_MAP.get(context.get(ComputeInputs.ATTACK_STYLE).getCombatStyle())
			.applyAsDouble(ChinchompaRange.forRange(context.get(ComputeInputs.ATTACK_DISTANCE)));
		return GearBonuses.of(accuracy, 1.0);
	}
}
