package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.plugin.osdata.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcDataProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WikiNpcDataProvider extends NpcDataProvider
{

	private static final Gson GSON = new Gson();
	private static final String NPC_STATS_URL = "https://raw.githubusercontent.com/LlemonDuck/runelite-wiki-scraper/wiki-data/npcs.min.json";
	private static final String NPC_BASE_IDS_URL = "https://raw.githubusercontent.com/LlemonDuck/runelite-wiki-scraper/wiki-data/npc-base-ids.min.json";

	private Map<Integer, NpcData> npcStatsMap;
	private Map<Integer, Integer> npcBaseIdsMap;

	private final WikiDataLoader wikiDataLoader;

	public void load() throws IOException
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