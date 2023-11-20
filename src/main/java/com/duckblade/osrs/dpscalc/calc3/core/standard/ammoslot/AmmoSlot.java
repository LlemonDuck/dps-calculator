package com.duckblade.osrs.dpscalc.calc3.core.standard.ammoslot;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class AmmoSlot extends FirstContextValue<ItemStats>
{

	public static final String AMMO_SLOT_STATS_PROVIDERS = "AmmoSlotStatsProviders";

	public AmmoSlot(
		@Named(AMMO_SLOT_STATS_PROVIDERS) List<ContextValue<ItemStats>> providers,
		StandardAmmoSlot standardAmmoSlot
	)
	{
		super(providers, standardAmmoSlot);
	}
}
