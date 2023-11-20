package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardDefenderStats;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import com.duckblade.osrs.dpscalc.calc3.model.DefensiveStats;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class DefenderStats extends FirstContextValue<DefensiveStats>
{

	public static final String DEFENSIVE_STATS_PROVIDERS = "DefensiveStatsProviders";

	@Inject
	public DefenderStats(
		@Named(DEFENSIVE_STATS_PROVIDERS) List<ContextValue<DefensiveStats>> providers,
		StandardDefenderStats fallback
	)
	{
		super(providers, fallback);
	}

}
