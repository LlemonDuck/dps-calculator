package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.ui.DpsPluginPanel;
import com.google.inject.Provides;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginInstantiationException;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@Slf4j
@Singleton
@PluginDescriptor(
	name = "DPS Calculator"
)
public class DpsCalcPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientToolbar toolbar;

	@Inject
	private PluginManager pluginManager;

	@Inject
	private DpsCalcConfig config;

	@Inject
	private WikiDataLoader wikiDataLoader;

	@Inject
	private NpcDataManager npcDataManager;

	private DpsPluginPanel panel;

	private NavigationButton navButton;

	private static final String ACTION_DPS_NPC = "DPS";

	@Override
	protected void startUp() throws PluginInstantiationException
	{
		if (!wikiDataLoader.load())
		{
			pluginManager.stopPlugin(this);
		}
		
		panel = injector.getInstance(DpsPluginPanel.class);

		navButton = NavigationButton.builder()
			.priority(5)
			.icon(ImageUtil.loadImageResource(getClass(), "ui/equip/slot_0.png"))
			.tooltip("DPS Calculator")
			.panel(panel)
			.build();
		toolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown()
	{
		toolbar.removeNavigation(navButton);
		log.info("DPS Calculator stopped!");
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		if (!config.showMinimenuEntry())
		{
			return;
		}

		if (MenuAction.of(event.getType()) == MenuAction.EXAMINE_NPC)
		{
			NPC npc = client.getCachedNPCs()[event.getIdentifier()];
			int npcId = npc.getId();
			NpcStats stats = npcDataManager.getNpcStatsById(npcId);
			if (stats != null)
			{
				client.createMenuEntry(-1)
					.setOption(ACTION_DPS_NPC)
					.setTarget(event.getTarget())
					.setType(MenuAction.RUNELITE)
					.setParam0(event.getActionParam0())
					.setParam1(event.getActionParam1())
					.setIdentifier(event.getIdentifier())
					.onClick(this::onMenuOptionClicked);
			}
		}
	}

	public void onMenuOptionClicked(MenuEntry event)
	{
		if (event.getType() != MenuAction.RUNELITE || !event.getOption().equals(ACTION_DPS_NPC))
		{
			return;
		}

		NPC npc = client.getCachedNPCs()[event.getIdentifier()];
		int npcId = npc.getId();
		NpcStats stats = npcDataManager.getNpcStatsById(npcId);
		if (stats == null)
		{
			return;
		}

		panel.openNpcStats(stats);

		if (!navButton.isSelected())
		{
			SwingUtilities.invokeLater(() -> navButton.getOnSelect().run());
		}
	}

	@Provides
	DpsCalcConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DpsCalcConfig.class);
	}
}