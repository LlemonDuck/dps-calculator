package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import net.runelite.api.Skill;

public class TestNpcStats
{

	public static void verzikP2(ComputeContext context)
	{
		context.put(ComputeInputs.DEFENDER_SKILLS, Skills.builder()
			.level(Skill.HITPOINTS, 3250)
			.level(Skill.DEFENCE, 200)
			.level(Skill.MAGIC, 400)
			.build()
		);
		context.put(ComputeInputs.DEFENDER_BONUSES, DefensiveBonuses.builder()
			.defenseStab(100)
			.defenseSlash(60)
			.defenseCrush(100)
			.defenseMagic(70)
			.defenseRanged(250)
			.build());
		context.put(ComputeInputs.DEFENDER_ATTRIBUTES, DefenderAttributes.builder()
			.size(3)
			.accuracyMagic(80)
			.build());
	}

	public static void sire(ComputeContext context)
	{
		context.put(ComputeInputs.DEFENDER_SKILLS, Skills.builder()
			.level(Skill.HITPOINTS, 400)
			.level(Skill.DEFENCE, 250)
			.level(Skill.MAGIC, 200)
			.build()
		);
		context.put(ComputeInputs.DEFENDER_BONUSES, DefensiveBonuses.builder()
			.defenseStab(40)
			.defenseSlash(60)
			.defenseCrush(50)
			.defenseMagic(20)
			.defenseRanged(60)
			.build());
		context.put(ComputeInputs.DEFENDER_ATTRIBUTES, DefenderAttributes.builder()
			.size(3)
			.accuracyMagic(0)
			.build());
	}

	public static void kurask(ComputeContext context)
	{
		context.put(ComputeInputs.DEFENDER_SKILLS, Skills.builder()
			.level(Skill.HITPOINTS, 97)
			.level(Skill.DEFENCE, 105)
			.level(Skill.MAGIC, 1)
			.build()
		);
		context.put(ComputeInputs.DEFENDER_BONUSES, DefensiveBonuses.builder()
			.defenseSlash(20)
			.defenseCrush(20)
			.build());
		context.put(ComputeInputs.DEFENDER_ATTRIBUTES, DefenderAttributes.builder()
			.size(3)
			.isLeafy(true)
			.build());
	}

}
