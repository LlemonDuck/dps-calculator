package com.duckblade.osrs.dpscalc.calc;

import net.runelite.api.Skill;

public abstract class AbstractCalc
{
	private static final float SECONDS_PER_TICK = 0.6f;

	abstract int attackRoll(CalcInput input);

	abstract int defenseRoll(CalcInput input);

	abstract int maxHit(CalcInput input);

	CalcResult calculateDPS(CalcInput input)
	{
		int attRoll = attackRoll(input);
		int defRoll = defenseRoll(input);
		int maxHit = maxHit(input);
		float hitChance = hitChance(attRoll, defRoll);

		float weaponSpeed = input.getEquipmentStats().getSpeed();
		float dps = (maxHit * hitChance) / (2f * weaponSpeed * SECONDS_PER_TICK);
		
		int prayerTime = prayerTimer(input);
		int ttk = dps == 0 ? -1 : (int) Math.ceil((float) input.getNpcTarget().getLevelHp() / dps);
		
		return CalcResult.builder()
				.attackRoll(attRoll)
				.defenseRoll(defRoll)
				.maxHit(maxHit)
				.hitChance(hitChance)
				.hitRate(weaponSpeed * SECONDS_PER_TICK)
				.dps(dps)
				.prayerSeconds(prayerTime)
				.avgTtk(ttk)
				.build();
	}

	private float hitChance(int attRoll, int defRoll)
	{

		if (attRoll > defRoll)
			return 1f - ((defRoll + 2f) / (2f * attRoll + 1f));
		else
			return attRoll / (2f * defRoll + 1f);
	}

	private int prayerTimer(CalcInput input)
	{
		// each time drain surpasses resistance, lose one prayer level
		int prayerLevel = input.getPlayerSkills().get(Skill.PRAYER) + input.getPlayerBoosts().get(Skill.PRAYER);
		int resistance = 2 * input.getEquipmentStats().getPrayer() + 60;
		int drainPerTick = input.getPrayerDrain();
		
		if (drainPerTick == 0)
			return -1;
		
		int totalPrayerPoints = prayerLevel * resistance;
		int totalTicks = (int) Math.ceil((float) totalPrayerPoints / drainPerTick);
		return (int) (totalTicks * SECONDS_PER_TICK);
	}

}
