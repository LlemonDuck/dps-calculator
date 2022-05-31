package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.util.EffectiveLevelComputable;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import javax.inject.Singleton;
import net.runelite.api.Skill;

@Singleton
public class MageEffectiveAttackLevelComputable extends EffectiveLevelComputable
{

	@Override
	protected Skill getBaseSkill()
	{
		return Skill.MAGIC;
	}

	@Override
	protected Prayer.PrayerGroup getPrayerGroup()
	{
		return Prayer.PrayerGroup.MAGE;
	}

	@Override
	protected ToDoubleFunction<Prayer> getPrayerMultiplier()
	{
		return Prayer::getAttackMod;
	}

	@Override
	protected int getBaseBonus()
	{
		return 9;
	}

	@Override
	protected Map<CombatStyle, Integer> getCombatFocusBonuses()
	{
		return ImmutableMap.of(
			CombatStyle.ACCURATE, 2
		);
	}
}
