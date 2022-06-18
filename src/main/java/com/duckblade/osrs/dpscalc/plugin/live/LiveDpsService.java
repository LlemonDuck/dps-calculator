package com.duckblade.osrs.dpscalc.plugin.live;

import com.duckblade.osrs.dpscalc.calc.DpsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.exceptions.MissingInputException;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProvider;
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
	private final DpsComputable dpsComputable;

	private ComputeInput lastInput;

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
	public void onGameTick(GameTick tick)
	{
		ComputeInput input = clientDataProvider.toComputeInput();
		if (!input.equals(lastInput))
		{
			lastInput = input;
			ComputeContext context = new ComputeContext(input);
			try
			{
				DefenderAttributes defenderAttributes = input.getDefenderAttributes();
				if (defenderAttributes == null || defenderAttributes.getNpcId() == -1)
				{
					setDps(null, input, context);
				}
				else
				{
					TargetedDps newDps = new TargetedDps(defenderAttributes.getNpcId(), context.get(dpsComputable));
					setDps(newDps, input, context);
				}
			}
			catch (Exception e)
			{
				setDps(null, input, context);
				if (!(e.getCause() instanceof MissingInputException))
				{
					throw e;
				}
			}
		}
	}

	public void setDps(TargetedDps newValue, ComputeInput input, ComputeContext context)
	{
		eventBus.post(new TargetedDpsChanged(newValue, input, context));
	}
}
