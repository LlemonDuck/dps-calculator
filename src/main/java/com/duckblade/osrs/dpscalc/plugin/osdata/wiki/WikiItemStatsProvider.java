package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
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
public class WikiItemStatsProvider implements ItemStatsProvider
{

	private static final String ITEM_STATS_URL = "items.min.json";

	private Map<Integer, ItemStats> itemStatsMap;

	private final Gson gson;
	private final WikiDataLoader wikiDataLoader;

	public CompletableFuture<?> load(ExecutorService es)
	{
		return FutureUtil.simpleCompletableFuture(es, () ->
		{
			wikiDataLoader.getReader(ITEM_STATS_URL, reader ->
				itemStatsMap = gson.fromJson(reader, new TypeToken<HashMap<Integer, ItemStats>>()
				{
				}.getType())
			);
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