package com.duckblade.osrs.dpscalc.calc.maxhit;

import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.google.common.collect.ImmutableMap;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MeleeEffectiveStrengthLevelComputableTest
{

	private final MeleeEffectiveStrengthLevelComputable meleeEffectiveStrengthLevelComputable = new MeleeEffectiveStrengthLevelComputable();

	@Test
	void providesCorrectParametersToBaseClass()
	{
		assertEquals(Skill.STRENGTH, meleeEffectiveStrengthLevelComputable.getBaseSkill());
		assertEquals(Prayer.PrayerGroup.MELEE, meleeEffectiveStrengthLevelComputable.getPrayerGroup());
		assertEquals(Prayer.PIETY.getStrengthMod(), meleeEffectiveStrengthLevelComputable.getPrayerMultiplier().applyAsDouble(Prayer.RIGOUR));
		assertEquals(8, meleeEffectiveStrengthLevelComputable.getBaseBonus());
		assertEquals(
			ImmutableMap.<CombatStyle, Integer>builder()
				.put(CombatStyle.AGGRESSIVE, 3)
				.put(CombatStyle.CONTROLLED, 1)
				.build(),
			meleeEffectiveStrengthLevelComputable.getCombatFocusBonuses()
		);
	}

}