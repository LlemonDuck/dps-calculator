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
public class TheatreSkillScaling implements SkillScaling
{

	private static final Set<Integer> TOB_NPCS = ImmutableSet.of(
		NpcID.THE_MAIDEN_OF_SUGADINTI, // 100%
		NpcID.THE_MAIDEN_OF_SUGADINTI_8361, // 70%
		NpcID.THE_MAIDEN_OF_SUGADINTI_8362, // 50%
		NpcID.THE_MAIDEN_OF_SUGADINTI_8363, // 30%
		NpcID.THE_MAIDEN_OF_SUGADINTI_8364, // death 1
		NpcID.THE_MAIDEN_OF_SUGADINTI_8365, // death 2
		NpcID.NYLOCAS_MATOMENOS, // maiden red nylo
		NpcID.BLOOD_SPAWN, // maiden spawn

		NpcID.PESTILENT_BLOAT,

		NpcID.NYLOCAS_ISCHYROS_8342, // small melee
		NpcID.NYLOCAS_TOXOBOLOS_8343, // small range
		NpcID.NYLOCAS_HAGIOS, // small mage
		NpcID.NYLOCAS_ISCHYROS_8345, // big melee
		NpcID.NYLOCAS_TOXOBOLOS_8346, // big range
		NpcID.NYLOCAS_HAGIOS_8347, // big mage
		NpcID.NYLOCAS_ISCHYROS_8348, // aggro melee
		NpcID.NYLOCAS_TOXOBOLOS_8349, // aggro range
		NpcID.NYLOCAS_HAGIOS_8350, // aggro mage
		NpcID.NYLOCAS_ISCHYROS_8351, // big aggro melee
		NpcID.NYLOCAS_TOXOBOLOS_8352, // big aggro range
		NpcID.NYLOCAS_HAGIOS_8353, // big aggro mage
		NpcID.NYLOCAS_VASILIAS_8355, // melee boss
		NpcID.NYLOCAS_VASILIAS_8356, // mage boss (yes, really, this isn't swapped with 8357)
		NpcID.NYLOCAS_VASILIAS_8357, // range boss

		NpcID.SOTETSEG_10864, // immune (maze) 
		NpcID.SOTETSEG_10865, // attackable

		NpcID.XARPUS_8339, // healing
		NpcID.XARPUS_8340, // p2+screech

		NpcID.VERZIK_VITUR_8369, // pre-fight
		NpcID.VERZIK_VITUR_8370, // p1
		NpcID.VERZIK_VITUR_8371, // p1 -> p2
		NpcID.VERZIK_VITUR_8372, // p2
		NpcID.VERZIK_VITUR_8373, // p2 -> p3
		NpcID.VERZIK_VITUR_8374, // p3
		NpcID.WEB, // p3 web
		NpcID.NYLOCAS_ISCHYROS_8381, // verz melee nylo
		NpcID.NYLOCAS_TOXOBOLOS_8382, // verz range nylo
		NpcID.NYLOCAS_HAGIOS_8383, // verz mage nylo
		NpcID.NYLOCAS_ATHANATOS, // verz purple nylo
		NpcID.NYLOCAS_MATOMENOS_8385 // verz red nylo
	);

	private static final Set<Integer> TOB_HM_NPCS = ImmutableSet.of(
		NpcID.THE_MAIDEN_OF_SUGADINTI_10822, // 100%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10823, // 70%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10824, // 50%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10825, // 30%
		NpcID.THE_MAIDEN_OF_SUGADINTI_10826, // death
		NpcID.THE_MAIDEN_OF_SUGADINTI_10827, // death
		NpcID.NYLOCAS_MATOMENOS_10828, // maiden red nylo
		NpcID.BLOOD_SPAWN_10829, // maiden spawn

		NpcID.PESTILENT_BLOAT_10813,

		NpcID.NYLOCAS_ISCHYROS_10791, // small melee
		NpcID.NYLOCAS_TOXOBOLOS_10792, // small range
		NpcID.NYLOCAS_HAGIOS_10793, // small mage
		NpcID.NYLOCAS_ISCHYROS_10794, // big melee
		NpcID.NYLOCAS_TOXOBOLOS_10795, // big range
		NpcID.NYLOCAS_HAGIOS_10796, // big mage
		NpcID.NYLOCAS_ISCHYROS_10797, // aggro melee
		NpcID.NYLOCAS_TOXOBOLOS_10798, // aggro range
		NpcID.NYLOCAS_HAGIOS_10799, // aggro mage
		NpcID.NYLOCAS_ISCHYROS_10800, // big aggro melee
		NpcID.NYLOCAS_TOXOBOLOS_10801, // big aggro range
		NpcID.NYLOCAS_HAGIOS_10802, // big aggro mage
		NpcID.NYLOCAS_PRINKIPAS_10804, // demiboss melee
		NpcID.NYLOCAS_PRINKIPAS_10805, // demiboss mage
		NpcID.NYLOCAS_PRINKIPAS_10806, // demiboss range
		NpcID.NYLOCAS_VASILIAS_10808, // melee boss
		NpcID.NYLOCAS_VASILIAS_10809, // mage boss (yes, really, this isn't swapped with 10810)
		NpcID.NYLOCAS_VASILIAS_10810, // range boss

		NpcID.SOTETSEG_10867, // maze
		NpcID.SOTETSEG_10868, // fight

		NpcID.XARPUS_10770, // pre
		NpcID.XARPUS_10771, // p1
		NpcID.XARPUS_10772, // p2

		NpcID.VERZIK_VITUR_10847, // pre
		NpcID.VERZIK_VITUR_10848, // throne
		NpcID.VERZIK_VITUR_10849, // p1->p2
		NpcID.VERZIK_VITUR_10850, // p2
		NpcID.VERZIK_VITUR_10851, // p2->p3
		NpcID.VERZIK_VITUR_10852, // p3
		NpcID.WEB_10854, // p3 web
		NpcID.NYLOCAS_ISCHYROS_10858, // melee nylo
		NpcID.NYLOCAS_TOXOBOLOS_10859, // range nylo
		NpcID.NYLOCAS_HAGIOS_10860, // mage nylo
		NpcID.NYLOCAS_ATHANATOS_10861, // purple healer
		NpcID.NYLOCAS_MATOMENOS_10862 // red healer
	);

	@Override
	public boolean isApplicable(ComputeContext context)
	{
		int npcId = context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getNpcId();
		return TOB_NPCS.contains(npcId) || TOB_HM_NPCS.contains(npcId);
	}

	@Override
	public int scale(ComputeContext context, Skill skill, int base)
	{
		if (skill != Skill.HITPOINTS)
		{
			return base;
		}

		int partySize = Math.min(5, Math.max(3, context.get(ComputeInputs.RAID_PARTY_SIZE)));
		switch (partySize)
		{
			case 3:
				return (int) (0.75 * base);

			case 4:
				return (int) (0.875 * base);

			default:
				return base;
		}
	}

}
