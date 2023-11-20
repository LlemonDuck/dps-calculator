package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardEquipmentStats;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class EquipmentStats extends FirstContextValue<ItemStats>
{

	public static final String EQUIPMENT_STATS_PROVIDERS = "EquipmentStatsProviders";

	@Inject
	public EquipmentStats(
		@Named(EQUIPMENT_STATS_PROVIDERS) List<ContextValue<ItemStats>> providers,
		StandardEquipmentStats standardEquipmentStats
	)
	{
		super(providers, standardEquipmentStats);
	}

}
