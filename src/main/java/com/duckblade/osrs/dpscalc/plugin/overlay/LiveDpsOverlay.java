package com.duckblade.osrs.dpscalc.plugin.overlay;

import com.duckblade.osrs.dpscalc.calc.DpsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.maxhit.MaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProvider;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

@Singleton
@Slf4j
public class LiveDpsOverlay extends OverlayPanel implements PluginLifecycleComponent
{

	private static final DecimalFormat DPS_FORMAT = new DecimalFormat("#.##");
	private static final DecimalFormat MAX_HIT_FORMAT = new DecimalFormat("#.##");
	private static final DecimalFormat HIT_CHANCE_FORMAT = new DecimalFormat("#.##%");

	private final OverlayManager overlayManager;
	private final EventBus eventBus;

	private final DpsCalcConfig config;
	private final ClientDataProvider clientDataProvider;

	private final DpsComputable dpsComputable;
	private final MaxHitComputable maxHitComputable;
	private final HitChanceComputable hitChanceComputable;

	private boolean ticked = true;
	private ComputeInput lastInput;
	private ComputeContext context;

	@Inject
	public LiveDpsOverlay(
		OverlayManager overlayManager, EventBus eventBus,
		DpsCalcConfig config, ClientDataProvider clientDataProvider,
		DpsComputable dpsComputable, MaxHitComputable maxHitComputable, HitChanceComputable hitChanceComputable
	)
	{
		this.overlayManager = overlayManager;
		this.eventBus = eventBus;
		this.config = config;
		this.clientDataProvider = clientDataProvider;
		this.dpsComputable = dpsComputable;
		this.maxHitComputable = maxHitComputable;
		this.hitChanceComputable = hitChanceComputable;

		setPosition(OverlayPosition.BOTTOM_LEFT);
	}

	@Override
	public Predicate<DpsCalcConfig> isConfigEnabled()
	{
		return DpsCalcConfig::showLiveOverlay;
	}

	@Override
	public void startUp()
	{
		overlayManager.add(this);
		eventBus.register(this);
	}

	@Override
	public void shutDown()
	{
		overlayManager.remove(this);
		eventBus.unregister(this);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (config.liveOverlayShowTitle())
		{
			getPanelComponent().getChildren().add(
				TitleComponent.builder()
					.text("DPS Calc")
					.build()
			);
		}

		ComputeInput input;
		if (ticked && !(input = clientDataProvider.toComputeInput()).equals(lastInput))
		{
			lastInput = input;
			context = new ComputeContext(input);
		}
		ticked = false;

		if (config.liveOverlayShowDps())
		{
			getPanelComponent().getChildren().add(
				LineComponent.builder()
					.left("DPS")
					.right(buildDpsString(context))
					.build()
			);
		}

		if (config.liveOverlayShowMaxHit())
		{
			getPanelComponent().getChildren().add(
				LineComponent.builder()
					.left("Max Hit")
					.right(buildMaxHitString(context))
					.build()
			);
		}

		if (config.liveOverlayShowHitChance())
		{
			getPanelComponent().getChildren().add(
				LineComponent.builder()
					.left("Hit Chance")
					.right(buildHitChanceString(context))
					.build()
			);
		}

		return super.render(graphics);
	}

	@Subscribe
	public void onGameTick(GameTick e)
	{
		ticked = true;
	}

	private String buildDpsString(ComputeContext context)
	{
		try
		{
			return DPS_FORMAT.format(context.get(dpsComputable));
		}
		catch (DpsComputeException e)
		{
			return "???";
		}
	}

	private String buildMaxHitString(ComputeContext context)
	{
		try
		{
			return MAX_HIT_FORMAT.format(context.get(maxHitComputable));
		}
		catch (DpsComputeException e)
		{
			return "???";
		}
	}

	private String buildHitChanceString(ComputeContext context)
	{
		try
		{
			return HIT_CHANCE_FORMAT.format(context.get(hitChanceComputable));
		}
		catch (DpsComputeException e)
		{
			return "???";
		}
	}
}
