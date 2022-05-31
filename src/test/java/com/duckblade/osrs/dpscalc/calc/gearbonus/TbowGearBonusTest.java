package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.gearbonus.TbowGearBonus.tbowFormula;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.MAX_MAGIC;
import static com.duckblade.osrs.dpscalc.calc.testutil.DefenderAttributesUtil.MIN_MAGIC;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TbowGearBonusTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private TbowGearBonus tbowGearBonus;

	@Test
	void isApplicableWhenUsingTbow()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.TWISTED_BOW));

		assertTrue(tbowGearBonus.isApplicable(context));
	}

	@Test
	void isNotApplicableWhenNotUsingTbow()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.MAGIC_SHORTBOW));

		assertFalse(tbowGearBonus.isApplicable(context));
	}

	@Test
	void warnsWhenTargetMagicIsLow()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(MIN_MAGIC);
		when(context.get(ComputeInputs.DEFENDER_SKILLS)).thenReturn(ofSkill(Skill.MAGIC, 50));

		assertEquals(GearBonuses.of(tbowFormula(50, true), tbowFormula(50, false)), tbowGearBonus.compute(context));
		verify(context, times(1)).warn("Using the twisted bow against low-magic targets incurs negative bonuses.");
	}

	@Test
	void maxesScalingAt250()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(MAX_MAGIC);
		when(context.get(ComputeInputs.DEFENDER_SKILLS)).thenReturn(ofSkill(Skill.MAGIC, 350));

		assertEquals(
			GearBonuses.of(tbowFormula(250, true), tbowFormula(250, false)),
			tbowGearBonus.compute(context)
		);
	}

	@Test
	void usesMagicLevelIfLargerThanMagicAccuracy()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(MAX_MAGIC);
		when(context.get(ComputeInputs.DEFENDER_SKILLS)).thenReturn(ofSkill(Skill.MAGIC, 100));

		assertEquals(
			GearBonuses.of(tbowFormula(250, true), tbowFormula(250, false)),
			tbowGearBonus.compute(context)
		);
	}

	@Test
	void usesMagicAccuracyIfLargerThanMagicLevel()
	{
		when(context.get(ComputeInputs.DEFENDER_ATTRIBUTES)).thenReturn(MIN_MAGIC);
		when(context.get(ComputeInputs.DEFENDER_SKILLS)).thenReturn(ofSkill(Skill.MAGIC, 250));

		assertEquals(
			GearBonuses.of(tbowFormula(250, true), tbowFormula(250, false)),
			tbowGearBonus.compute(context)
		);
	}

}