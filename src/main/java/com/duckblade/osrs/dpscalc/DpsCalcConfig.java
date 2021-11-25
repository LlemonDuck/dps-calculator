package com.duckblade.osrs.dpscalc;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("dpscalculator")
public interface DpsCalcConfig extends Config
{
	@ConfigItem(
			keyName = "showMinimenuEntry",
			name = "Right-Click Entry",
			description = "Whether to add a right-click entry to NPCs to quickly load them into the side panel."
	)
	default boolean showMinimenuEntry()
	{
		return true;
	}

	@ConfigItem(
			keyName = "enableAutoSync",
			name = "Enable Auto Sync",
			description = "Automatically sync game state to the panel as it changes"
	)
	default boolean enableAutoSync()
	{
		return false;
	}
}
