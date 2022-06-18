package com.duckblade.osrs.dpscalc.plugin.live.overlay;

import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.maxhit.TrueMaxHitComputable;
import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.live.TargetedDps;
import com.duckblade.osrs.dpscalc.plugin.live.TargetedDpsChanged;
import com.duckblade.osrs.dpscalc.plugin.live.party.PartyDpsService;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.ComputeUtil;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
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

	private final EventBus eventBus;
	private final OverlayManager overlayManager;
	private final DpsCalcConfig config;

	private final OverlayMinimizerService overlayMinimizerService;
	private final PartyDpsService partyDpsService;

	private final TrueMaxHitComputable trueMaxHitComputable;
	private final HitChanceComputable hitChanceComputable;

	// boxed types so we can store null
	private TargetedDps targetedDps;
	private Integer maxHit;
	private Double hitChance;

	@Inject
	public LiveDpsOverlay(
		OverlayManager overlayManager, EventBus eventBus, DpsCalcConfig config,
		OverlayMinimizerService overlayMinimizerService, PartyDpsService partyDpsService,
		TrueMaxHitComputable trueMaxHitComputable, HitChanceComputable hitChanceComputable
	)
	{
		this.eventBus = eventBus;
		this.overlayManager = overlayManager;
		this.config = config;

		this.overlayMinimizerService = overlayMinimizerService;
		this.partyDpsService = partyDpsService;

		this.trueMaxHitComputable = trueMaxHitComputable;
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
		eventBus.register(this);
		overlayManager.add(this);
	}

	@Override
	public void shutDown()
	{
		eventBus.unregister(this);
		overlayManager.remove(this);
	}

	@Subscribe
	public void onTargetedDpsChanged(TargetedDpsChanged e)
	{
		targetedDps = e.getTargetedDps();

		ComputeContext context = e.getContext();
		maxHit = ComputeUtil.tryCompute(() -> context.get(trueMaxHitComputable));
		hitChance = ComputeUtil.tryCompute(() -> context.get(hitChanceComputable));
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (overlayMinimizerService.isMinimized())
		{
			return null;
		}

		if (targetedDps == null && config.liveOverlayMinimizeIncomplete())
		{
			return null;
		}

		if (config.liveOverlayShowTitle())
		{
			getPanelComponent().getChildren().add(
				TitleComponent.builder()
					.text("DPS Calc")
					.build()
			);
		}

		if (config.liveOverlayShowDps())
		{
			addLineComponent("DPS", targetedDps == null ? "???" : DPS_FORMAT.format(targetedDps.getDps()));
		}

		if (config.liveOverlayShowPartyDps() && partyDpsService.hasDps())
		{
			double partyDps = partyDpsService.getPartyDps(targetedDps.getNpcTarget());
			addLineComponent("Party DPS", targetedDps == null ? "???" : DPS_FORMAT.format(partyDps));
		}

		if (config.liveOverlayShowMaxHit())
		{
			addLineComponent("Max Hit", maxHit == null ? "???" : MAX_HIT_FORMAT.format(maxHit));
		}

		if (config.liveOverlayShowHitChance())
		{
			addLineComponent("Hit Chance", hitChance == null ? "???" : HIT_CHANCE_FORMAT.format(hitChance));
		}

		return super.render(graphics);
	}

	private void addLineComponent(String left, String right)
	{
		getPanelComponent().getChildren().add(
			LineComponent.builder()
				.left(left)
				.right(right)
				.build()
		);
	}

}
