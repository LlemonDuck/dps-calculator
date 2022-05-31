package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.google.common.collect.ImmutableMap;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MeleeEffectiveAttackLevelComputableTest
{

	private final MeleeEffectiveAttackLevelComputable meleeEffectiveAttackLevelComputable = new MeleeEffectiveAttackLevelComputable();

	@Test
	void providesCorrectParametersToBaseClass()
	{
		assertEquals(Skill.ATTACK, meleeEffectiveAttackLevelComputable.getBaseSkill());
		assertEquals(Prayer.PrayerGroup.MELEE, meleeEffectiveAttackLevelComputable.getPrayerGroup());
		assertEquals(Prayer.PIETY.getAttackMod(), meleeEffectiveAttackLevelComputable.getPrayerMultiplier().applyAsDouble(Prayer.PIETY));
		assertEquals(8, meleeEffectiveAttackLevelComputable.getBaseBonus());
		assertEquals(
			ImmutableMap.of(CombatStyle.ACCURATE, 3, CombatStyle.CONTROLLED, 1),
			meleeEffectiveAttackLevelComputable.getCombatFocusBonuses()
		);
	}

}