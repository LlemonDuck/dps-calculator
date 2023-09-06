package com.duckblade.osrs.dpscalc.plugin.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class FutureUtil
{

	public static CompletableFuture<?> simpleCompletableFuture(ExecutorService es, ThrowableRunnable r)
	{
		CompletableFuture<?> f = new CompletableFuture<>();

		es.submit(() ->
		{
			try
			{
				r.run();
				f.complete(null);
			}
			catch (Throwable ex)
			{
				f.completeExceptionally(ex);
			}
		});

		return f;
	}

}
