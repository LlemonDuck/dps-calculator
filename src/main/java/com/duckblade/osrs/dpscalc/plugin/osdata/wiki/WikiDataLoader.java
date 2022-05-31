package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Slf4j
@Singleton
public class WikiDataLoader
{

	private static final String BASE_URL = "https://raw.githubusercontent.com/LlemonDuck/runelite-wiki-scraper/wiki-data-v2/";

	private final OkHttpClient okHttpClient;

	@Inject
	public WikiDataLoader(OkHttpClient okHttpClient)
	{
		this.okHttpClient = okHttpClient.newBuilder()
			.addInterceptor(chain ->
				chain.proceed(
					chain.request()
						.newBuilder()
						.header("User-Agent", "RuneLite plugin dps-calculator (GitHub/LlemonDuck)")
						.build()
				))
			.build();
	}

	void getReader(String part, Consumer<Reader> callback) throws IOException
	{
		String url = BASE_URL + part;
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

}