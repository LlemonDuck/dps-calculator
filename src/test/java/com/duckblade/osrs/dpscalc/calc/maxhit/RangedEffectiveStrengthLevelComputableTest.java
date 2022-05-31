package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import java.util.Collections;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class RangedEffectiveStrengthLevelComputableTest
{

	private final RangedEffectiveStrengthLevelComputable rangedEffectiveStrengthLevelComputable = new RangedEffectiveStrengthLevelComputable();

	@Test
	void providesCorrectParametersToBaseClass()
	{
		assertEquals(Skill.RANGED, rangedEffectiveStrengthLevelComputable.getBaseSkill());
		assertEquals(Prayer.PrayerGroup.RANGED, rangedEffectiveStrengthLevelComputable.getPrayerGroup());
		assertEquals(Prayer.RIGOUR.getStrengthMod(), rangedEffectiveStrengthLevelComputable.getPrayerMultiplier().applyAsDouble(Prayer.RIGOUR));
		assertEquals(8, rangedEffectiveStrengthLevelComputable.getBaseBonus());
		assertEquals(Collections.singletonMap(CombatStyle.ACCURATE, 3), rangedEffectiveStrengthLevelComputable.getCombatFocusBonuses());
	}

}