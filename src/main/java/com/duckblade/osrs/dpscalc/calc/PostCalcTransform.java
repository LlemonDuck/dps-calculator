package com.duckblade.osrs.dpscalc.calc;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static com.duckblade.osrs.dpscalc.calc.AbstractCalc.SECONDS_PER_TICK;

public enum PostCalcTransform
{

	DHAROKS_SET_EFFECT(EquipmentRequirement.DHAROKS, (input, result) ->
	{
		int maxHp = input.getMaxHp();
		int currentHp = input.getActiveHp();
		
		// wiki says "the following damage modifier is applied to a player's hit after the damage roll"
		// but the operation is commutative with regular dps calculation
		float dharokMod = 1 + ((maxHp - currentHp) / 100f) * (maxHp / 100f);
		return result.withDps(result.getDps() * dharokMod)
				.withMaxHit((int) (result.getMaxHit() * dharokMod));
	}),
	KARILS_SET_EFFECT(EquipmentRequirement.KARIL, (input, result) ->
	{
		if (!EquipmentRequirement.AMULET_DAMNED.isSatisfied(input))
			return result;

		// 0.75 * dps + 0.25 * (1.5 * dps) = 1.125 * dps
		return result.withDps(result.getDps() * 1.125f);
	}),
	VERACS_SET_EFFECT(EquipmentRequirement.VERACS, (input, result) ->
	{
		float oldDps = result.getDps();

		// ignores hit chance and adds one to damage
		float weaponSpeed = input.getEquipmentStats().getSpeed();
		float veracDps = ((result.getMaxHit() / 2f) + 1) / (weaponSpeed * SECONDS_PER_TICK);
		float combinedDps = oldDps * 0.75f + veracDps * 0.25f;

		return result.withDps(combinedDps);
	}),
	SCYTHE(EquipmentRequirement.SCYTHE, (input, result) ->
	{
		if (input.getNpcTarget().getSize() < 2)
		{
			return result;
		}
		
		float oldDps = result.getDps();
		float weaponSpeed = input.getEquipmentStats().getSpeed();

		// bonus 50% hitsplat on size 2+, another bonus 25% hitsplat on size 3+, all roll independently
		int secondMaxHit = result.getMaxHit() / 2;
		float secondHitDps = (secondMaxHit * result.getHitChance()) / (2f * weaponSpeed * SECONDS_PER_TICK);
		int thirdMaxHit = result.getMaxHit() / 4;
		float thirdHitDps = (thirdMaxHit * result.getHitChance()) / (2f * weaponSpeed * SECONDS_PER_TICK);
		
		boolean thirdHit = input.getNpcTarget().getSize() > 2;
		float bonusDps = secondHitDps + (thirdHit ? thirdHitDps : 0);
		
		return result.withDps(oldDps + bonusDps);
	}),
	KERIS_TRIPLE_HIT(EquipmentRequirement.KERIS, (input, result) ->
	{
		if (!input.getNpcTarget().isKalphite())
		{
			return result;
		}
		
		// 1/51 chance to deal triple damage
		float weaponSpeed = input.getEquipmentStats().getSpeed();
		float tripleDamageMaxHit = result.getMaxHit() * 3;
		float tripleDamageDps = (tripleDamageMaxHit * result.getHitChance()) / (2f * weaponSpeed * SECONDS_PER_TICK);
		
		return result.withDps((50f / 51f) * result.getDps() + (1f / 51f) * tripleDamageDps);
	})
	;

	private final BiFunction<CalcInput, CalcResult, CalcResult> mapper;

	PostCalcTransform(BiFunction<CalcInput, CalcResult, CalcResult> mapper)
	{
		this.mapper = mapper;
	}

	PostCalcTransform(BiPredicate<CalcInput, CalcResult> filter, BiFunction<CalcInput, CalcResult, CalcResult> mapper)
	{
		this((i, r) -> filter.test(i, r) ? mapper.apply(i, r) : r);
	}

	PostCalcTransform(EquipmentRequirement filter, BiFunction<CalcInput, CalcResult, CalcResult> mapper)
	{
		this((i, r) -> filter.isSatisfied(i), mapper);
	}

	public static CalcResult processAll(CalcInput input, CalcResult intermediaryResult)
	{
		for (PostCalcTransform transform : values())
		{
			intermediaryResult = transform.mapper.apply(input, intermediaryResult);
		}

		return intermediaryResult;
	}

}