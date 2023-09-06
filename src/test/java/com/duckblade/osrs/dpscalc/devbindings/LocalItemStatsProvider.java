package com.duckblade.osrs.dpscalc.devbindings;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ItemStatsProvider;
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
public class LocalItemStatsProvider implements ItemStatsProvider
{

	private static final Gson GSON = new Gson();
	private static final String ITEM_STATS_FILE = "/osdata/items.min.json";

	private Map<Integer, ItemStats> itemStatsMap;

	@Override
	public CompletableFuture<?> load(ExecutorService es)
	{
		return FutureUtil.simpleCompletableFuture(es, () ->
		{
			Reader reader = new InputStreamReader(getClass().getResourceAsStream(ITEM_STATS_FILE));
			itemStatsMap = GSON.fromJson(reader, new TypeToken<HashMap<Integer, ItemStats>>()
			{
			}.getType());
		});
	}

	@Override
	public Set<ItemStats> getAll()
	{
		return new HashSet<>(itemStatsMap.values());
	}

	@Override
	public ItemStats getById(int npcId)
	{
		return itemStatsMap.get(npcId);
	}

}