package com.duckblade.osrs.dpscalc.calc3;

import com.duckblade.osrs.dpscalc.calc3.core.Dpt;
import com.duckblade.osrs.dpscalc.calc3.core.MaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.DpsComputeModule3;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.model.ComputeInput;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc3.model.Skills;
import com.duckblade.osrs.dpscalc.calc3.model.WeaponCategory;
import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Inject;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;

public class Calc3Test
{
	
	@Inject
	private MaxHit maxHit;

	@Inject
	private Dpt dpt;

	public void start() throws Exception
	{
		ComputeInput input = ComputeInput.builder()
			.attackerSkills(Skills.builder()
				.level(Skill.ATTACK, 99)
				.level(Skill.STRENGTH, 99)
				.level(Skill.RANGED, 99)
				.level(Skill.MAGIC, 99)
				.build())
			.attackerItem(EquipmentInventorySlot.WEAPON, ItemStats.builder()
//				.itemId(ItemID.SCYTHE_OF_VITUR)
//				.accuracyStab(70)
//				.accuracySlash(110)
//				.accuracyCrush(30)
//				.strengthMelee(75)
//				.speed(5)
//				.weaponCategory(WeaponCategory.SCYTHE)
				.itemId(ItemID.DRAGON_CLAWS)
				.accuracyStab(41)
				.accuracySlash(57)
				.accuracyCrush(-4)
				.strengthMelee(56)
				.speed(4)
				.weaponCategory(WeaponCategory.CLAW)
				.build())
			.attackStyle(WeaponCategory.CLAW.getAttackStyles().get(1))
			.build();

		ComputeContext ctx = new ComputeContext(input);
		ctx.initDebug();

		Stopwatch sw = Stopwatch.createStarted();
		double dptOut = ctx.get(dpt);
		sw.stop();

		try (FileWriter fw = new FileWriter("graph.md"))
		{
			fw.write("# Calculation took " + sw.elapsed(TimeUnit.MILLISECONDS) + "ms\n");
			fw.write("```mermaid\n");
			fw.write(ctx.toGraph());
			fw.write("\n```");
		}
	}

	public static void main(String[] args) throws Exception
	{
		Guice.createInjector(new DpsComputeModule3())
			.getInstance(Calc3Test.class)
			.start();
	}

}
