package com.duckblade.osrs.dpscalc.calc.attack;

import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import java.util.Collections;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MageEffectiveAttackLevelComputableTest
{

	private final MageEffectiveAttackLevelComputable mageEffectiveAttackLevelComputable = new MageEffectiveAttackLevelComputable();

	@Test
	void providesCorrectParametersToBaseClass()
	{
		assertEquals(Skill.MAGIC, mageEffectiveAttackLevelComputable.getBaseSkill());
		assertEquals(Prayer.PrayerGroup.MAGE, mageEffectiveAttackLevelComputable.getPrayerGroup());
		assertEquals(Prayer.AUGURY.getAttackMod(), mageEffectiveAttackLevelComputable.getPrayerMultiplier().applyAsDouble(Prayer.AUGURY));
		assertEquals(9, mageEffectiveAttackLevelComputable.getBaseBonus());
		assertEquals(Collections.singletonMap(CombatStyle.ACCURATE, 2), mageEffectiveAttackLevelComputable.getCombatFocusBonuses());
	}

}