package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.plugin.util.FutureUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WikiNpcDataProvider implements NpcDataProvider
{

	private static final String NPC_STATS_URL = "npcs.min.json";
	private static final String NPC_BASE_IDS_URL = "npc-base-ids.min.json";

	private final Gson gson;
	private final WikiDataLoader wikiDataLoader;

	private Map<Integer, NpcData> npcStatsMap;
	private Map<Integer, Integer> npcBaseIdsMap;

	@Override
	public CompletableFuture<?> load(ExecutorService es)
	{
		return FutureUtil.simpleCompletableFuture(es, () ->
		{
			wikiDataLoader.getReader(NPC_BASE_IDS_URL, reader ->
				npcBaseIdsMap = gson.fromJson(reader, new TypeToken<HashMap<Integer, Integer>>()
				{
				}.getType())
			);
			wikiDataLoader.getReader(NPC_STATS_URL, reader ->
				npcStatsMap = gson.fromJson(reader, new TypeToken<HashMap<Integer, NpcData>>()
				{
				}.getType())
			);
		});
	}

	public Set<NpcData> getAll()
	{
		return new HashSet<>(npcStatsMap.values());
	}

	public NpcData getById(int npcId)
	{
		return npcStatsMap.get(canonicalize(npcId));
	}

	public int canonicalize(int npcId)
	{
		return npcBaseIdsMap.getOrDefault(npcId, npcId);
	}

}