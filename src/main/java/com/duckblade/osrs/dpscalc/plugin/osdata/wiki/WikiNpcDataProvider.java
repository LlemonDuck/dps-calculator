package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class WikiNpcDataProvider implements NpcDataProvider
{

	private static final Gson GSON = new Gson();
	private static final String NPC_STATS_URL = "npcs.min.json";
	private static final String NPC_BASE_IDS_URL = "npc-base-ids.min.json";

	private Map<Integer, NpcData> npcStatsMap;
	private Map<Integer, Integer> npcBaseIdsMap;

	@Inject
	public WikiNpcDataProvider(WikiDataLoader wikiDataLoader) throws IOException
	{
		wikiDataLoader.getReader(NPC_BASE_IDS_URL, reader ->
			npcBaseIdsMap = GSON.fromJson(reader, new TypeToken<HashMap<Integer, Integer>>()
			{
			}.getType())
		);
		wikiDataLoader.getReader(NPC_STATS_URL, reader ->
			npcStatsMap = GSON.fromJson(reader, new TypeToken<HashMap<Integer, NpcData>>()
			{
			}.getType())
		);
	}

	public Set<NpcData> getAll()
	{
		return new HashSet<>(npcStatsMap.values());
	}

	public NpcData getById(int npcId)
	{
		int baseId = npcBaseIdsMap.getOrDefault(npcId, npcId);
		return npcStatsMap.get(baseId);
	}

}