package com.duckblade.osrs.dpscalc.plugin.live.overlay;

import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.util.HotkeyListener;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class OverlayMinimizerService implements PluginLifecycleComponent
{

	private final EventBus eventBus;
	private final KeyManager keyManager;

	private final DpsCalcConfig config;

	private HotkeyListener toggleListener;
	private boolean forceMinimized = false;

	private Instant minimizeTimeout = Instant.now();
	private boolean timeoutMinimized = false;

	@Override
	public void startUp()
	{
		eventBus.register(this);
		keyManager.registerKeyListener(toggleListener = new HotkeyListener(config::liveOverlayMinimizeHotkey)
		{
			@Override
			public void hotkeyPressed()
			{
				forceMinimized = !forceMinimized;
			}
		});
	}

	@Override
	public void shutDown()
	{
		eventBus.unregister(this);
		keyManager.unregisterKeyListener(toggleListener);
	}

	public boolean isMinimized()
	{
		if (!timeoutMinimized)
		{
			timeoutMinimized = minimizeTimeout.isBefore(Instant.now());
		}

		return forceMinimized || timeoutMinimized;
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied e)
	{
		if (e.getHitsplat().isMine())
		{
			timeoutMinimized = false;
			minimizeTimeout = Instant.now()
				.plus(config.liveOverlayMinimizeDelay(), ChronoUnit.SECONDS);
		}
	}
}
