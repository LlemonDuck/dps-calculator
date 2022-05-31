package com.duckblade.osrs.dpscalc.plugin.config;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(DpsCalcConfig.CONFIG_GROUP)
public interface DpsCalcConfig extends Config
{
	String CONFIG_GROUP = "dpscalculator";

	@ConfigItem(
		keyName = "showMinimenuEntry",
		name = "Right-Click Entry",
		description = "Whether to add a right-click entry to NPCs to quickly load them into the side panel.",
		position = 1
	)
	default boolean showMinimenuEntry()
	{
		return true;
	}

	@ConfigItem(
		keyName = "defaultTbpDarts",
		name = "Blowpipe Darts",
		description = "Default darts to use in calculations for the Toxic Blowpipe.",
		position = 2
	)
	default BlowpipeDarts defaultTbpDarts()
	{
		return BlowpipeDarts.DRAGON;
	}

}
