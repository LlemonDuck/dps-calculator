package com.duckblade.osrs.dpscalc.plugin.module;

import com.duckblade.osrs.dpscalc.plugin.DpsMenuActionListener;
import com.duckblade.osrs.dpscalc.plugin.live.LiveDpsService;
import com.duckblade.osrs.dpscalc.plugin.live.overlay.LiveDpsOverlay;
import com.duckblade.osrs.dpscalc.plugin.live.overlay.OverlayMinimizerService;
import com.duckblade.osrs.dpscalc.plugin.live.party.PartyDpsService;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.InteractingNpcTracker;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.RuneLiteClientDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ScraperWikiDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.DpsCalcPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.DpsPluginPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.NavButtonManager;
import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DpsPluginModule extends AbstractModule
{

	@Override
	protected void configure()
	{
		bind(ClientDataProvider.class).to(RuneLiteClientDataProvider.class);
		bind(WikiDataProvider.class).to(ScraperWikiDataProvider.class);
	}

	@Provides
	public Set<PluginLifecycleComponent> provideLifecycleComponents(
		DpsPluginPanel dpsPluginPanel,
		DpsMenuActionListener dpsMenuActionListener,
		InteractingNpcTracker interactingNpcTracker,
		LiveDpsService liveDpsService,
		LiveDpsOverlay liveDpsOverlay,
		PartyDpsService partyDpsService,
		NavButtonManager navButtonManager,
		OverlayMinimizerService overlayMinimizerService
	)
	{
		return ImmutableSet.of(
			dpsPluginPanel,
			dpsMenuActionListener,
			interactingNpcTracker,
			liveDpsService,
			liveDpsOverlay,
			partyDpsService,
			navButtonManager,
			overlayMinimizerService
		);
	}

}
