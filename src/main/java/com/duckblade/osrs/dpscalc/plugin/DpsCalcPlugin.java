package com.duckblade.osrs.dpscalc.plugin;

import com.duckblade.osrs.dpscalc.calc.DpsComputeModule;
import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.module.ComponentManager;
import com.duckblade.osrs.dpscalc.plugin.module.DpsPluginModule;
import com.google.inject.Binder;
import com.google.inject.Provides;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.slayer.SlayerPlugin;

@Slf4j
@Singleton
@PluginDependency(SlayerPlugin.class)
@PluginDescriptor(
	name = "DPS Calculator"
)
public class DpsCalcPlugin extends Plugin
{

	private ComponentManager componentManager;

	@Override
	public void configure(Binder binder)
	{
		binder.install(new DpsComputeModule());
		binder.install(new DpsPluginModule());
	}

	@Override
	protected void startUp()
	{
		componentManager = injector.getInstance(ComponentManager.class);
		componentManager.onPluginStart();
	}

	@Override
	protected void shutDown()
	{
		componentManager.onPluginStop();
	}

	@Provides
	DpsCalcConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DpsCalcConfig.class);
	}
}