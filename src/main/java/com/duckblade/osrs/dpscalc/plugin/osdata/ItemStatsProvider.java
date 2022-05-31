package com.duckblade.osrs.dpscalc.plugin.osdata;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import java.io.IOException;
import java.util.Set;

public abstract class ItemStatsProvider implements PluginLifecycleComponent
{

	@Override
	public void startUp()
	{
		try
		{
			load();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutDown()
	{
	}

	public abstract void load() throws IOException;

	public abstract Set<ItemStats> getAll();

	public abstract ItemStats getById(int itemId);

}
