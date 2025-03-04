package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.eventbus.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Singleton
@Slf4j
public class ScraperWikiDataProvider extends WikiDataProvider
{

	private static final String BASE_URL = "https://raw.githubusercontent.com/weirdgloop/osrs-dps-calc/refs/heads/main/cdn/json/";
	private static final String EQUIPMENT_URL = BASE_URL + "equipment.json";
	private static final String ALIASES_URL = BASE_URL + "equipment_aliases.json";
	private static final String MONSTERS_URL = BASE_URL + "monsters.json";

	private final OkHttpClient client;
	private final Gson gson;

	@Getter(onMethod_ = @Override)
	private final EventBus eventBus;

	@Getter(onMethod_ = @Override)
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(
		new ThreadFactoryBuilder()
			.setNameFormat("dps-calc-data-fetcher")
			.build());

	private int errors = 0;

	@Inject
	public ScraperWikiDataProvider(
		OkHttpClient client,
		EventBus eventBus,
		Gson gson
	)
	{
		this.client = client.newBuilder()
			.addNetworkInterceptor(chain ->
				chain.proceed(chain.request().newBuilder()
					.header("User-Agent", "runelite/dps-calculator github/LlemonDuck/dps-calculator")
					.build())
			).build();

		this.eventBus = eventBus;
		this.gson = gson;
	}

	@Override
	protected void loadEquipment(Consumer<List<EquipmentPiece>> callback)
	{
		// @CHECKSTYLE:OFF
		loadGeneric(
			EQUIPMENT_URL, callback, new TypeToken<>()
			{
			}
		);
		// @CHECKSTYLE:ON
	}

	@Override
	protected void loadEquipmentAliases(Consumer<Map<Integer, Integer>> callback)
	{
		// @CHECKSTYLE:OFF
		loadGeneric(
			ALIASES_URL, callback, new TypeToken<>()
			{
			}
		);
		// @CHECKSTYLE:ON
	}

	@Override
	protected void loadMonsters(Consumer<List<Monster>> callback)
	{
		// @CHECKSTYLE:OFF
		loadGeneric(
			MONSTERS_URL, callback, new TypeToken<>()
			{
			}
		);
		// @CHECKSTYLE:ON
	}

	private <T> void loadGeneric(String url, Consumer<T> callback, TypeToken<T> typeToken)
	{
		client.newCall(new Request.Builder()
				.url(url)
				.get()
				.build())
			.enqueue(new Callback()
			{
				private void onFailure(Exception e)
				{
					if (errors < 3)
					{
						log.warn("Failed to load wiki data from {}. Will retry in 10 minutes", url, e);
					}
					else
					{
						log.debug("Failed to load wiki data from {}. Will retry in 10 minutes", url, e);
					}
					errors++;

					callback.accept(null);
					executor.schedule(
						() -> loadGeneric(url, callback, typeToken),
						10, TimeUnit.MINUTES
					);
				}

				@Override
				public void onFailure(Call call, IOException e)
				{
					onFailure(e);
				}

				@Override
				public void onResponse(Call call, Response response)
				{
					// todo checkstyle doesn't like the space-aligned resources
					// @CHECKSTYLE:OFF
					try (ResponseBody body = response.body();
						 InputStream stream = body != null ? body.byteStream() : null;
						 InputStreamReader isr = stream != null ? new InputStreamReader(stream) : null)
					{
						// @CHECKSTYLE:ON
						if (stream == null)
						{
							onFailure(new RuntimeException("empty body"));
							return;
						}

						callback.accept(gson.fromJson(isr, typeToken.getType()));
					}
					catch (Exception e)
					{
						onFailure(e);
					}
				}
			});
	}
}
