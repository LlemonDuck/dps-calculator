package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class NpcDataManager
{

	private static final Gson GSON = new Gson();

	@Getter
	private boolean loaded;
	private Map<Integer, NpcStats> npcStatsMap;
	private Map<Integer, Integer> npcBaseIdsMap;

	public void initStats(Reader reader)
	{
		npcStatsMap = GSON.fromJson(reader, new TypeToken<HashMap<Integer, NpcStats>>()
		{
		}.getType());
	}

	public void initBaseIds(Reader reader)
	{
		npcBaseIdsMap = GSON.fromJson(reader, new TypeToken<HashMap<Integer, Integer>>()
		{
		}.getType());
	}

	public List<NpcStats> getAll()
	{
		return npcStatsMap.values()
			.stream()
			.sorted(Comparator.comparing(NpcStats::getName))
			.collect(Collectors.toList());
	}

	public NpcStats getNpcStatsById(int npcId)
	{
		int baseId = npcBaseIdsMap.getOrDefault(npcId, npcId);
		return npcStatsMap.get(baseId);
	}

}