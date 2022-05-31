package com.duckblade.osrs.dpscalc.plugin.module;

import com.duckblade.osrs.dpscalc.plugin.DpsMenuActionListener;
import com.duckblade.osrs.dpscalc.plugin.osdata.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.dev.DevItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.dev.DevNpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.NavButtonManager;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class DpsPluginModule extends AbstractModule
{

	@Override
	protected void configure()
	{
		Multibinder<PluginLifecycleComponent> lifecycleComponents = Multibinder.newSetBinder(binder(), PluginLifecycleComponent.class);
		lifecycleComponents.addBinding().to(DpsMenuActionListener.class);
		lifecycleComponents.addBinding().to(NavButtonManager.class);

		Class<? extends ItemStatsProvider> itemStatsProvider = DevItemStatsProvider.class; // swap in dev if desired
		lifecycleComponents.addBinding().to(itemStatsProvider);
		bind(ItemStatsProvider.class).to(itemStatsProvider);

		Class<? extends NpcDataProvider> npcDataProvider = DevNpcDataProvider.class; // swap in dev if desired
		lifecycleComponents.addBinding().to(npcDataProvider);
		bind(NpcDataProvider.class).to(npcDataProvider);
	}

}
