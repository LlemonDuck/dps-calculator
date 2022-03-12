package com.duckblade.osrs.dpscalc;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class WikiDataLoader
{

	private static final String ITEM_STATS_URL = "https://raw.githubusercontent.com/LlemonDuck/runelite-wiki-scraper/wiki-data/items-dps-calc.min.json";
	private static final String NPC_STATS_URL = "https://raw.githubusercontent.com/LlemonDuck/runelite-wiki-scraper/wiki-data/npcs.min.json";
	private static final String NPC_BASE_IDS_URL = "https://raw.githubusercontent.com/LlemonDuck/runelite-wiki-scraper/wiki-data/npc-base-ids.min.json";

	private final OkHttpClient okHttpClient;
	private final ItemDataManager itemDataManager;
	private final NpcDataManager npcDataManager;

	private void getReader(String url, Consumer<Reader> callback) throws IOException
	{
		try (Response response = okHttpClient.newCall(new Request.Builder().url(url).build()).execute())
		{
			log.debug(response.toString());

			if (!response.isSuccessful() && response.code() != 304)
			{
				throw new IOException(String.format("Request to [%s] failed: %s", url, response));
			}

			ResponseBody body = response.body();
			if (body == null)
			{
				throw new IOException(String.format("Received null body from request to [%s]", url));
			}
			
			callback.accept(body.charStream());
		}
	}

	public boolean load()
	{
		try
		{
			getReader(ITEM_STATS_URL, itemDataManager::init);
			getReader(NPC_STATS_URL, npcDataManager::initStats);
			getReader(NPC_BASE_IDS_URL, npcDataManager::initBaseIds);
			return true;
		}
		catch (Exception e)
		{
			log.error("Failed to load wiki data", e);
			return false;
		}
	}

}