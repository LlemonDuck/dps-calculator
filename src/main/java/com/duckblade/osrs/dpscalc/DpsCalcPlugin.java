package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.ui.DpsPluginPanel;
import com.google.inject.Provides;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.util.Objects;

@Slf4j
@Singleton
@PluginDescriptor(
		name = "DPS Calculator"
)
public class DpsCalcPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientToolbar toolbar;

	@Inject
	private DpsCalcConfig config;

	@Inject
	private NpcDataManager npcDataManager;

	private DpsPluginPanel panel;

	private NavigationButton navButton;

	private static final String ACTION_DPS_NPC = "DPS";

	@Override
	protected void startUp()
	{
		panel = injector.getInstance(DpsPluginPanel.class);

		navButton = NavigationButton.builder()
				.priority(5)
				.icon(ImageUtil.loadImageResource(getClass(), "ui/equip/slot_0.png"))
				.tooltip("DPS Calculator")
				.panel(panel)
				.build();
		toolbar.addNavigation(navButton);

		log.info("DPS Calculator started!");
	}

	@Override
	protected void shutDown()
	{
		toolbar.removeNavigation(navButton);

		log.info("DPS Calculator stopped!");
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		if (!config.showMinimenuEntry())
			return;

		if (MenuAction.of(event.getType()) == MenuAction.EXAMINE_NPC)
		{
			NPC npc = client.getCachedNPCs()[event.getIdentifier()];
			int npcId = npc.getId();
			NpcStats stats = npcDataManager.getNpcStatsById(npcId);
			if (stats != null)
			{
				MenuEntry loadToDpsPanelEntry = new MenuEntry();
				loadToDpsPanelEntry.setOption(ACTION_DPS_NPC);
				loadToDpsPanelEntry.setTarget(event.getTarget());
				loadToDpsPanelEntry.setType(MenuAction.RUNELITE.getId());
				loadToDpsPanelEntry.setParam0(event.getActionParam0());
				loadToDpsPanelEntry.setParam1(event.getActionParam1());
				loadToDpsPanelEntry.setIdentifier(event.getIdentifier());

				MenuEntry[] currentEntries = client.getMenuEntries();
				MenuEntry[] newEntries = new MenuEntry[currentEntries.length + 1];
				System.arraycopy(currentEntries, 0, newEntries, 0, currentEntries.length);
				newEntries[newEntries.length - 1] = loadToDpsPanelEntry;
				client.setMenuEntries(newEntries);
			}
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.getMenuAction() != MenuAction.RUNELITE || !event.getMenuOption().equals(ACTION_DPS_NPC))
			return;

		NPC npc = client.getCachedNPCs()[event.getId()];
		int npcId = npc.getId();
		NpcStats stats = npcDataManager.getNpcStatsById(npcId);
		if (stats == null)
			return;

		panel.openNpcStats(stats);

		if (!navButton.isSelected())
			navButton.getOnSelect().run();
	}

	// subscribe to equipment changes
	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (!config.enableAutoSync()) return;

		if (event.getContainerId() == InventoryID.EQUIPMENT.getId()) {
			panel.onEquipmentChanged();
		}
	}

	// subscribe to attack style changes
	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		if (!config.enableAutoSync()) return;

		if (event.getIndex() == VarPlayer.ATTACK_STYLE.getId()) {
			panel.onEquipmentChanged();
		}

		// Unsure which varbit this actually is, or if its even defined anywhere
		if (event.getIndex() == 83) {
			panel.onPrayersChanged();
		}
	}

	// listen for updates to combat skills
	@Subscribe
	public void onStatChanged(StatChanged statChanged)
	{
		if (!config.enableAutoSync()) return;

		if (statChanged.getSkill() == Skill.ATTACK ||
			statChanged.getSkill() == Skill.STRENGTH ||
			statChanged.getSkill() == Skill.DEFENCE ||
			statChanged.getSkill() == Skill.MAGIC ||
			statChanged.getSkill() == Skill.RANGED ||
			statChanged.getSkill() == Skill.PRAYER
		) {
			panel.onStatChanged();
		}
	}


	// listen for changes to the users targeted
	@Subscribe
	public void onInteractingChanged(InteractingChanged interactingChanged)
	{
		if (!config.enableAutoSync()) return;

		Player player = client.getLocalPlayer();
		if (player == null) return;

		Actor source = interactingChanged.getSource();
		if (source == null) return;

		Actor target = interactingChanged.getTarget();
		if (target == null) return;

		// if the source is the current player, and there is a target
		if (Objects.equals(player.getName(), source.getName()) && target instanceof NPC) {
			NPC npc = (NPC) target;
			int npcId = npc.getId();
			NpcStats stats = npcDataManager.getNpcStatsById(npcId);
			if (stats == null) return;

			panel.onTargetChanged(stats);
		}
	}

	@Provides
	DpsCalcConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DpsCalcConfig.class);
	}
}
