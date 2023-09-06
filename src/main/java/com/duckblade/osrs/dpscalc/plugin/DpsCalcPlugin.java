package com.duckblade.osrs.dpscalc.plugin;

import com.duckblade.osrs.dpscalc.calc.DpsComputeModule;
import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.module.ComponentManager;
import com.duckblade.osrs.dpscalc.plugin.module.DpsPluginModule;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcDataProvider;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Binder;
import com.google.inject.Provides;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import javax.swing.SwingUtilities;
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

	private static final ListeningExecutorService dataLoadEs =
		MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

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
		CompletableFuture.allOf(
			injector.getInstance(NpcDataProvider.class).load(dataLoadEs),
			injector.getInstance(ItemStatsProvider.class).load(dataLoadEs)
		).thenRunAsync(() ->
			SwingUtilities.invokeLater(() ->
			{
				componentManager = injector.getInstance(ComponentManager.class);
				componentManager.onPluginStart();
			}), dataLoadEs);
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