package com.duckblade.osrs.dpscalc.plugin;

import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiNpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.NavButtonManager;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DpsMenuActionListener implements PluginLifecycleComponent
{

	private final EventBus eventBus;
	private final Client client;

	private final WikiNpcDataProvider wikiNpcDataProvider;
	private final NavButtonManager navButtonManager;

	@Override
	public Predicate<DpsCalcConfig> isConfigEnabled()
	{
		return DpsCalcConfig::showMinimenuEntry;
	}

	@Override
	public void startUp()
	{
		eventBus.register(this);
	}

	@Override
	public void shutDown()
	{
		eventBus.unregister(this);
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded e)
	{
		if (MenuAction.of(e.getType()) == MenuAction.EXAMINE_NPC)
		{
			NPC npc = client.getCachedNPCs()[e.getIdentifier()];
			int npcId = npc.getId();

			NpcData stats = wikiNpcDataProvider.getById(npcId);
			if (stats != null)
			{
				client.createMenuEntry(-1)
					.setOption("Dps")
					.setTarget(e.getTarget())
					.setType(MenuAction.RUNELITE)
					.setParam0(e.getActionParam0())
					.setParam1(e.getActionParam1())
					.setIdentifier(e.getIdentifier())
					.onClick(me ->
					{
						// todo
						navButtonManager.openPanel();
					});
			}
		}
	}

	public void onMenuOptionClicked()
	{

	}

}
