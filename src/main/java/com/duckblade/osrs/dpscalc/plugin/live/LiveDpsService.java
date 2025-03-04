package com.duckblade.osrs.dpscalc.plugin.live;

import com.duckblade.osrs.dpscalc.calc.CalcOpts;
import com.duckblade.osrs.dpscalc.calc.DpsCalc;
import com.duckblade.osrs.dpscalc.calc.DpsResultCache;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.InteractingNpcTracker;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class LiveDpsService implements PluginLifecycleComponent
{

	private final EventBus eventBus;

	private final ClientDataProvider clientDataProvider;
	private final InteractingNpcTracker interactingNpcTracker;

	private ExecutorService dpsEs;
	private ComputeInput lastInput;
	private int lastNpcIndex;

	@Override
	public void startUp()
	{
		dpsEs = Executors.newSingleThreadExecutor(
			new ThreadFactoryBuilder()
				.setNameFormat("dps-calc-worker")
				.build());
		eventBus.register(this);
	}

	@Override
	public void shutDown()
	{
		eventBus.unregister(this);
		dpsEs.shutdown();
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		ComputeInput input = clientDataProvider.toComputeInput();
		int npcIndex = interactingNpcTracker.getLastInteractedIndex();
		if (!input.equals(lastInput) || lastNpcIndex != npcIndex)
		{
			lastNpcIndex = npcIndex;
			lastInput = input;

			try
			{
				DpsCalc dpsCalc = new DpsCalc(input.getPlayer(), input.getMonster(), CalcOpts.builder().build());
				DpsResultCache resultCache = new DpsResultCache(dpsCalc);

				if (input.getMonster() == null || input.getPlayer() == null)
				{
					setDps(null, input, null);
				}
				else
				{
					TargetedDps newDps = new TargetedDps(npcIndex, resultCache.getDps());
					setDps(newDps, input, resultCache);
				}
			}
			catch (Exception e)
			{
				setDps(null, input, null);
			}
		}
	}

	public void setDps(TargetedDps newValue, ComputeInput input, DpsResultCache dpsResultCache)
	{
		eventBus.post(new TargetedDpsChanged(newValue, input, dpsResultCache));
	}
}
