package com.duckblade.osrs.dpscalc.plugin.config;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

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
		keyName = "defaultBlowpipeDarts",
		name = "Blowpipe Darts",
		description = "Default darts to use in calculations for the Toxic Blowpipe.",
		position = 2,
		hidden = true
	)
	default BlowpipeDarts defaultBlowpipeDarts()
	{
		return BlowpipeDarts.DRAGON;
	}

	@ConfigSection(
		name = "Live Overlay",
		description = "",
		position = 100
	)
	String SECTION_LIVE_OVERLAY_FEATURES = "liveOverlayFeatures";

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "showLiveOverlay",
		name = "Live Overlay Panel",
		description = "Show live overlay panel with DPS results against your current target.",
		position = 101
	)
	default boolean showLiveOverlay()
	{
		return true;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "showTitle",
		name = "Show Title",
		description = "Show 'DPS Calc' title bar in live overlay panel.",
		position = 102
	)
	default boolean liveOverlayShowTitle()
	{
		return true;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "showDps",
		name = "Show DPS",
		description = "Show DPS in live overlay panel.",
		position = 103
	)
	default boolean liveOverlayShowDps()
	{
		return true;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "showMaxHit",
		name = "Show Max Hit",
		description = "Show max hit in live overlay panel.",
		position = 104
	)
	default boolean liveOverlayShowMaxHit()
	{
		return true;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "showHitChance",
		name = "Show Hit %",
		description = "Show hit chance in live overlay panel.",
		position = 105
	)
	default boolean liveOverlayShowHitChance()
	{
		return true;
	}
}
