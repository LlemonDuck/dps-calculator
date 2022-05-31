package com.duckblade.osrs.dpscalc.plugin.module;

import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class ComponentManager
{

	private final EventBus eventBus;
	private final DpsCalcConfig config;
	private final Set<PluginLifecycleComponent> components;

	private final Map<PluginLifecycleComponent, Boolean> states = new HashMap<>();

	public void onPluginStart()
	{
		eventBus.register(this);
		components.forEach(c -> states.put(c, false));
		components.stream()
			.filter(c -> c.isConfigEnabled().test(config))
			.forEach(this::tryStartUp);
	}

	public void onPluginStop()
	{
		eventBus.unregister(this);
		components.stream()
			.filter(states::get)
			.forEach(this::tryShutDown);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e)
	{
		if (!DpsCalcConfig.CONFIG_GROUP.equals(e.getGroup()))
		{
			return;
		}

		components.forEach(c ->
		{
			boolean shouldBeEnabled = c.isConfigEnabled().test(config);
			boolean isEnabled = states.get(c);
			if (shouldBeEnabled == isEnabled)
			{
				return;
			}

			if (shouldBeEnabled)
			{
				tryStartUp(c);
			}
			else
			{
				tryShutDown(c);
			}
		});
	}

	private void tryStartUp(PluginLifecycleComponent component)
	{
		if (states.get(component))
		{
			return;
		}

		try
		{
			component.startUp();
			states.put(component, true);
		}
		catch (Exception e)
		{
			log.error("Failed to start DPS Calc component [{}]", component.getClass().getName());
		}
	}

	private void tryShutDown(PluginLifecycleComponent component)
	{
		if (!states.get(component))
		{
			return;
		}

		try
		{
			component.shutDown();
		}
		catch (Exception e)
		{
			log.error("Failed to cleanly shut down component [{}]", component.getClass().getName());
		}
		finally
		{
			states.put(component, false);
		}
	}

}
