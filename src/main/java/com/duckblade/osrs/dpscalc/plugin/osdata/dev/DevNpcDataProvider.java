package com.duckblade.osrs.dpscalc.plugin.osdata.dev;

import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcDataProvider;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;

@Singleton
public class DevNpcDataProvider extends NpcDataProvider
{

	private final Map<Integer, NpcData> localMap = new HashMap<>();

	@Override
	public void load()
	{
		localMap.put(
			NpcID.ZULRAH, NpcData.builder()
				.skills(Skills.builder()
					.level(Skill.HITPOINTS, 500)
					.level(Skill.ATTACK, 1)
					.level(Skill.STRENGTH, 1)
					.level(Skill.DEFENCE, 300)
					.level(Skill.MAGIC, 300)
					.level(Skill.RANGED, 300)
					.build())
				.defensiveBonuses(DefensiveBonuses.builder()
					.defenseMagic(-45)
					.defenseRanged(50)
					.build())
				.attributes(DefenderAttributes.builder()
					.size(5)
					.name("Zulrah")
					.accuracyMagic(50)
					.npcId(NpcID.ZULRAH)
					.build())
				.build()
		);
	}

	@Override
	public Set<NpcData> getAll()
	{
		return new HashSet<>(localMap.values());
	}

	@Override
	public NpcData getById(int npcId)
	{
		return localMap.get(npcId);
	}
}
