package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import net.runelite.api.NpcID;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ToaArenaComputable implements Computable<ToaArena>
{
	private static final Set<Integer> TOA_PATHS_NPCS = ImmutableSet.of(
		NpcID.SCARAB, // Scabaras' path scarab
		NpcID.CROCODILE_11705, // Crondis' path scarab
		NpcID.BABOON_BRAWLER, // Small melee
		NpcID.BABOON_THROWER, // Small ranged
		NpcID.BABOON_MAGE, // Small mage
		NpcID.BABOON_BRAWLER_11712, // Big melee
		NpcID.BABOON_THROWER_11713, // Big ranged
		NpcID.BABOON_MAGE_11714, // Big mage
		NpcID.BABOON_SHAMAN,
		NpcID.VOLATILE_BABOON,
		NpcID.CURSED_BABOON,
		NpcID.BABOON_THRALL,
		NpcID.KEPHRI,
		NpcID.SCARAB_SWARM_11723,
		NpcID.SOLDIER_SCARAB, // Kephri fight melee scarab
		NpcID.SPITTING_SCARAB, // Kephri fight big ranged scarab
		NpcID.ARCANE_SCARAB, // Kephri fight mage scarab
		NpcID.AGILE_SCARAB, // Kephri fight small ranged scarab
		NpcID.ZEBAK_11730,
		NpcID.BABA,
		NpcID.BABOON, // Ba-ba fight ranged baboon
		NpcID.AKKHA,
		NpcID.AKKHAS_SHADOW
	);

	private static final Set<Integer> TOA_WARDENS_NPCS = ImmutableSet.of(
		NpcID.OBELISK_11751, // p1 obelisk
		// NpcID.ELIDINIS_WARDEN_11753, p2.Uses special scaling
		// NpcID.TUMEKENS_WARDEN_11756,
		NpcID.ELIDINIS_WARDEN_11761, // p3
		NpcID.TUMEKENS_WARDEN_11762 // p3
	);

	@Override
	public ToaArena compute(ComputeContext context)
	{
		int npcId = context.get(ComputeInputs.DEFENDER_ATTRIBUTES).getNpcId();
		if (TOA_WARDENS_NPCS.contains(npcId))
		{
			return ToaArena.FIGHTING_WARDENS;
		}
		else if (TOA_PATHS_NPCS.contains(npcId))
		{
			return ToaArena.FIGHTING_PATH_NPC;
		}
		else
		{
			return ToaArena.FIGHTING_OUTSIDE_TOA;
		}
	}
}
