package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public interface NpcDataProvider
{

	CompletableFuture<?> load(ExecutorService es);

	Set<NpcData> getAll();

	NpcData getById(int npcId);

	int canonicalize(int npcId);

}
