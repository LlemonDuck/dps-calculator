package com.duckblade.osrs.dpscalc.plugin.config;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

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

	@ConfigItem(
		keyName = "enablePartyService",
		name = "Enable Party DPS",
		description = "Shares your DPS results with your party members who also have DPS Calculator installed.",
		position = 3
	)
	default boolean enablePartyService()
	{
		return true;
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
		keyName = "showPartyDps",
		name = "Show Party DPS",
		description = "Show cumulative party DPS in live overlay panel (requires party members to also have DPS Calculator installed).",
		position = 104
	)
	default boolean liveOverlayShowPartyDps()
	{
		return true;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "showMaxHit",
		name = "Show Max Hit",
		description = "Show max hit in live overlay panel.",
		position = 105
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
		position = 106
	)
	default boolean liveOverlayShowHitChance()
	{
		return true;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "minimizeDelay",
		name = "Minimize Delay",
		description = "Hide the live overlay after this long out without dealing damage.",
		position = 107
	)
	@Units(Units.SECONDS)
	@Range()
	default int liveOverlayMinimizeDelay()
	{
		return 60;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "minimizeIncomplete",
		name = "Minimize Incomplete",
		description = "Minimize the overlay when the DPS cannot be calculated.",
		position = 108
	)
	default boolean liveOverlayMinimizeIncomplete()
	{
		return true;
	}

	@ConfigItem(
		section = SECTION_LIVE_OVERLAY_FEATURES,
		keyName = "minimizeHotkey",
		name = "Toggle Hotkey",
		description = "Toggle the live dps overlay with this hotkey",
		position = 109
	)
	default Keybind liveOverlayMinimizeHotkey()
	{
		return Keybind.NOT_SET;
	}
}
