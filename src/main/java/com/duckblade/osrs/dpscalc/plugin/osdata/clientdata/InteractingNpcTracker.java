package com.duckblade.osrs.dpscalc.plugin.osdata.clientdata;

import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcDataProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.InteractingChanged;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class InteractingNpcTracker implements PluginLifecycleComponent
{

	private final EventBus eventBus;
	private final Client client;

	private final NpcDataProvider npcDataProvider;

	@Getter
	private NpcData lastInteracted = null;

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
	public void onInteractingChanged(InteractingChanged e)
	{
		if (e.getSource() != client.getLocalPlayer())
		{
			return;
		}

		if (!(e.getTarget() instanceof NPC))
		{
			return;
		}

		int npcId = ((NPC) e.getTarget()).getId();
		lastInteracted = npcDataProvider.getById(npcId);

		log.debug("Setting last interacted to {}", lastInteracted == null ? "null" : lastInteracted.getAttributes().getName());
	}
}
