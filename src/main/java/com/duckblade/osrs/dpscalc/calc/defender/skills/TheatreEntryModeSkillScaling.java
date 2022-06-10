package com.duckblade.osrs.dpscalc.calc.defender.skills;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TheatreEntryModeSkillScaling implements SkillScaling
{

	private static final Set<Integer> TOB_EM_NPCS = ImmutableSet.of(
		NpcID.THE_MAIDEN_OF_SUGADINTI_10814, // 100%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10815, // 70%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10816, // 50%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10817, // 30%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10818, // death 1
		NpcID.THE_MAIDEN_OF_SUGADINTI_10819, // death 2
		NpcID.NYLOCAS_MATOMENOS_10820, // death 2
		NpcID.BLOOD_SPAWN_10821, // death 2

		NpcID.PESTILENT_BLOAT_10812,

		NpcID.NYLOCAS_ISCHYROS_10774, // small melee
		NpcID.NYLOCAS_TOXOBOLOS_10775, // small range
		NpcID.NYLOCAS_HAGIOS_10776, // small mage
		NpcID.NYLOCAS_ISCHYROS_10777, // big melee
		NpcID.NYLOCAS_TOXOBOLOS_10778, // big range
		NpcID.NYLOCAS_HAGIOS_10779, // big mage
		NpcID.NYLOCAS_ISCHYROS_10780, // aggro melee
		NpcID.NYLOCAS_TOXOBOLOS_10781, // aggro range
		NpcID.NYLOCAS_HAGIOS_10782, // aggro mage
		NpcID.NYLOCAS_ISCHYROS_10783, // big aggro melee
		NpcID.NYLOCAS_TOXOBOLOS_10784, // big aggro range
		NpcID.NYLOCAS_HAGIOS_10785, // big aggro mage
		NpcID.NYLOCAS_VASILIAS_10787, // melee boss
		NpcID.NYLOCAS_VASILIAS_10788, // mage boss (yes, really, this isn't swapped with 10789)
		NpcID.NYLOCAS_VASILIAS_10789, // range boss

		NpcID.SOTETSEG_10864, // immune (maze) 
		NpcID.SOTETSEG_10865, // attackable

		NpcID.XARPUS_10767, // p1
		NpcID.XARPUS_10768, // p2

		NpcID.VERZIK_VITUR_10830, // pre-fight
		NpcID.VERZIK_VITUR_10831, // p1
		NpcID.VERZIK_VITUR_10832, // p1 -> p2
		NpcID.VERZIK_VITUR_10833, // p2
		NpcID.VERZIK_VITUR_10834, // p2 -> p3
		NpcID.VERZIK_VITUR_10835, // p3
		NpcID.WEB_10837, // p3 web
		NpcID.NYLOCAS_ISCHYROS_10841, // melee nylo
		NpcID.NYLOCAS_TOXOBOLOS_10842, // range nylo
		NpcID.NYLOCAS_HAGIOS_10843, // mage nylo
		NpcID.NYLOCAS_ATHANATOS_10844, // purple healer
		NpcID.NYLOCAS_MATOMENOS_10845 // red healer
	);

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		return TOB_EM_NPCS.contains(context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getNpcId());
	}

	@Override
	public int scale(ComputeContext context, Skill skill, int base)
	{
		if (skill != Skill.HITPOINTS)
		{
			return base;
		}

		double scale = Math.min(5, Math.max(1, context.get(ComputeInputs.RAID_PARTY_SIZE))) / 5.0;
		return (int) (scale * base);
	}

}
