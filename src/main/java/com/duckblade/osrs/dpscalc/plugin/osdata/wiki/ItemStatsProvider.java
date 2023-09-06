package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public interface ItemStatsProvider
{

	CompletableFuture<?> load(ExecutorService es);

	Set<ItemStats> getAll();

	ItemStats getById(int itemId);

}
