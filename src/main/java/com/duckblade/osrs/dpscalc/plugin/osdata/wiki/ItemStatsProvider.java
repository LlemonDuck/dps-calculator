package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import java.util.Set;

public interface ItemStatsProvider
{

	Set<ItemStats> getAll();

	ItemStats getById(int itemId);

}
