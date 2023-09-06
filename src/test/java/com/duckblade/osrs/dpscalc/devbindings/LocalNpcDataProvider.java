package com.duckblade.osrs.dpscalc.devbindings;

import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.util.FutureUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import javax.inject.Singleton;

@Singleton
public class LocalNpcDataProvider implements NpcDataProvider
{

	private static final Gson GSON = new Gson();
	private static final String NPC_STATS_FILE = "/osdata/npcs.min.json";
	private static final String NPC_BASE_IDS_FILE = "/osdata/npc-base-ids.min.json";

	private Map<Integer, NpcData> npcStatsMap;
	private Map<Integer, Integer> npcBaseIdsMap;

	@Override
	public CompletableFuture<?> load(ExecutorService es)
	{
		return FutureUtil.simpleCompletableFuture(es, () ->
		{
			Reader baseIdsReader = new InputStreamReader(getClass().getResourceAsStream(NPC_BASE_IDS_FILE));
			npcBaseIdsMap = GSON.fromJson(baseIdsReader, new TypeToken<HashMap<Integer, Integer>>()
			{
			}.getType());

			Reader npcDataReader = new InputStreamReader(getClass().getResourceAsStream(NPC_STATS_FILE));
			npcStatsMap = GSON.fromJson(npcDataReader, new TypeToken<HashMap<Integer, NpcData>>()
			{
			}.getType());
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