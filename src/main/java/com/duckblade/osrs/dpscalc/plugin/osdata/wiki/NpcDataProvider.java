package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import java.util.Set;

public interface NpcDataProvider
{

	Set<NpcData> getAll();

	NpcData getById(int npcId);

}
