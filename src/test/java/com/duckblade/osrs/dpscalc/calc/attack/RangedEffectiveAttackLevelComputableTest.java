package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import java.util.Collections;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class RangedEffectiveAttackLevelComputableTest
{

	private final RangedEffectiveAttackLevelComputable rangedEffectiveAttackLevelComputable = new RangedEffectiveAttackLevelComputable();

	@Test
	void providesCorrectParametersToBaseClass()
	{
		assertEquals(Skill.RANGED, rangedEffectiveAttackLevelComputable.getBaseSkill());
		assertEquals(Prayer.PrayerGroup.RANGED, rangedEffectiveAttackLevelComputable.getPrayerGroup());
		assertEquals(Prayer.RIGOUR.getAttackMod(), rangedEffectiveAttackLevelComputable.getPrayerMultiplier().applyAsDouble(Prayer.RIGOUR));
		assertEquals(8, rangedEffectiveAttackLevelComputable.getBaseBonus());
		assertEquals(Collections.singletonMap(CombatStyle.ACCURATE, 3), rangedEffectiveAttackLevelComputable.getCombatFocusBonuses());
	}

}