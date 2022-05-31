package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.plugin.DpsCalcPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DPSCalculatorPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DpsCalcPlugin.class);
		RuneLite.main(args);
	}
}
