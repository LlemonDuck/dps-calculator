package com.duckblade.osrs.dpscalc.plugin.module;

import com.duckblade.osrs.dpscalc.plugin.DpsMenuActionListener;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.InteractingNpcTracker;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.RuneLiteClientDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiNpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.overlay.LiveDpsOverlay;
import com.duckblade.osrs.dpscalc.plugin.overlay.OverlayMinimizerService;
import com.duckblade.osrs.dpscalc.plugin.ui.NavButtonManager;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DpsPluginModule extends AbstractModule
{

	@Override
	protected void configure()
	{
		Multibinder<PluginLifecycleComponent> lifecycleComponents = Multibinder.newSetBinder(binder(), PluginLifecycleComponent.class);
		lifecycleComponents.addBinding().to(DpsMenuActionListener.class);
		lifecycleComponents.addBinding().to(InteractingNpcTracker.class);
		lifecycleComponents.addBinding().to(LiveDpsOverlay.class);
		lifecycleComponents.addBinding().to(NavButtonManager.class);
		lifecycleComponents.addBinding().to(OverlayMinimizerService.class);

		bind(ItemStatsProvider.class).to(WikiItemStatsProvider.class);
		bind(NpcDataProvider.class).to(WikiNpcDataProvider.class);
		bind(ClientDataProvider.class).to(RuneLiteClientDataProvider.class);
	}

}
