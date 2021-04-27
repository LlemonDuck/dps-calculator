package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class NpcDataManager
{

	private static final Gson GSON = new Gson();

	private final Map<Integer, NpcStats> NPC_MAP;
	private final Map<Integer, Integer> ID_MERGE_MAP;

	@Inject
	public NpcDataManager()
	{
		try (InputStream fsNpcData = getClass().getResourceAsStream("npcs.min.json"); InputStreamReader readerNpcData = new InputStreamReader(fsNpcData); InputStream fsIdMap = getClass().getResourceAsStream("npc-base-ids.min.json"); InputStreamReader readerIdMap = new InputStreamReader(fsIdMap);
		)
		{
			NPC_MAP = GSON.fromJson(readerNpcData, new TypeToken<HashMap<Integer, NpcStats>>()
			{
			}.getType());

			ID_MERGE_MAP = GSON.fromJson(readerIdMap, new TypeToken<HashMap<Integer, Integer>>()
			{
			}.getType());
		}
		catch (IOException e)
		{
			log.error("Failed to load NPC data", e);
			throw new IllegalStateException(e);
		}
	}

	public List<NpcStats> getAll()
	{
		return NPC_MAP.values()
				.stream()
				.sorted(Comparator.comparing(NpcStats::getName))
				.collect(Collectors.toList());
	}

	public NpcStats getNpcStatsById(int npcId)
	{
		int baseId = ID_MERGE_MAP.getOrDefault(npcId, npcId);
		return NPC_MAP.get(baseId);
	}

}
