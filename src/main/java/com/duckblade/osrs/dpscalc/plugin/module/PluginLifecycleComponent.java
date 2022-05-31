package com.duckblade.osrs.dpscalc.plugin.module;

import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import java.util.function.Predicate;

public interface PluginLifecycleComponent
{

	default Predicate<DpsCalcConfig> isConfigEnabled()
	{
		return ignored -> true;
	}

	void startUp();

	void shutDown();

}
