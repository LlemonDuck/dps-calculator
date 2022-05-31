package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.plugin.osdata.ItemStatsProvider;
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
public class WikiItemStatsProvider extends ItemStatsProvider
{

	private static final Gson GSON = new Gson();
	private static final String ITEM_STATS_URL = "https://raw.githubusercontent.com/LlemonDuck/runelite-wiki-scraper/wiki-data/items-dps-calc.min.json";

	private Map<Integer, ItemStats> itemStatsMap;

	private final WikiDataLoader wikiDataLoader;

	@Override
	public void load() throws IOException
	{
		wikiDataLoader.getReader(ITEM_STATS_URL, reader ->
			itemStatsMap = GSON.fromJson(reader, new TypeToken<HashMap<Integer, ItemStats>>()
			{
			}.getType())
		);
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