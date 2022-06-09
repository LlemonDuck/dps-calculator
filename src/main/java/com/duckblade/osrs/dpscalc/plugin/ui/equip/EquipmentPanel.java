package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProviderThreadProxy;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class EquipmentPanel extends JPanel implements StateBoundComponent
{

	@Getter
	private final PanelStateManager manager;
	private final ClientDataProviderThreadProxy clientDataProviderThreadProxy;
	private final BlowpipeDartsSelectPanel blowpipeDartsSelectPanel;
	private final SpellSelectPanel spellSelectPanel;

	private final Set<StateBoundComponent> stateBoundComponents = new HashSet<>();
	private final Set<StateVisibleComponent> stateVisibleComponents = new HashSet<>();

	@Inject
	public EquipmentPanel(
		@Nullable ItemManager rlItemManager, ItemStatsProvider itemStatsProvider,
		PanelStateManager manager, ClientDataProviderThreadProxy clientDataProviderThreadProxy,
		EquipmentTotalsPanel equipmentTotalsPanel, AttackStyleSelectPanel attackStyleSelectPanel,
		InWildernessCheckBox inWildernessCheckBox, OnSlayerTaskCheckBox onSlayerTaskCheckBox,
		UsingChargeCheckBox usingChargeCheckBox, UsingMarkOfDarknessCheckBox usingMarkOfDarknessCheckBox,
		BlowpipeDartsSelectPanel blowpipeDartsSelectPanel, DharokHpPanel dharokHpPanel,
		ChinchompaDistancePanel chinchompaDistancePanel, SpellSelectPanel spellSelectPanel
	)
	{
		this.manager = manager;
		this.clientDataProviderThreadProxy = clientDataProviderThreadProxy;
		this.blowpipeDartsSelectPanel = blowpipeDartsSelectPanel;
		this.spellSelectPanel = spellSelectPanel;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 1000));

		JButton loadFromClientButton = new JButton("Load From Client");
		loadFromClientButton.addActionListener(e -> loadFromClient());
		loadFromClientButton.setAlignmentX(CENTER_ALIGNMENT);
		add(loadFromClientButton);

		add(Box.createVerticalStrut(10));

		JPanel slotPanel = new JPanel();
		slotPanel.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		slotPanel.setLayout(new BoxLayout(slotPanel, BoxLayout.Y_AXIS));
		slotPanel.setAlignmentX(CENTER_ALIGNMENT);
		add(slotPanel);

		for (EquipmentInventorySlot slot : EquipmentInventorySlot.values())
		{
			EquipmentSlotPanel innerPanel = new EquipmentSlotPanel(manager, rlItemManager, slot, itemStatsProvider);
			innerPanel.addCallback(this::fromState);
			addPanel(innerPanel, slotPanel);

			slotPanel.add(Box.createVerticalStrut(5));

			if (slot == EquipmentInventorySlot.HEAD)
			{
				addPanel(onSlayerTaskCheckBox, slotPanel);
			}

			if (slot == EquipmentInventorySlot.WEAPON)
			{
				addPanel(inWildernessCheckBox, slotPanel);
			}
		}
		add(Box.createVerticalStrut(5));

		addPanel(dharokHpPanel);
		dharokHpPanel.add(Box.createVerticalStrut(10));

		addPanel(chinchompaDistancePanel);
		chinchompaDistancePanel.add(Box.createVerticalStrut(10));

		addPanel(blowpipeDartsSelectPanel);
		blowpipeDartsSelectPanel.addCallback(this::fromState);

		addPanel(attackStyleSelectPanel);
		attackStyleSelectPanel.addCallback(this::fromState);

		addPanel(spellSelectPanel);
		spellSelectPanel.addCallback(this::fromState);

		addPanel(usingMarkOfDarknessCheckBox);
		usingMarkOfDarknessCheckBox.add(Box.createVerticalStrut(10));

		addPanel(usingChargeCheckBox);
		usingChargeCheckBox.add(Box.createVerticalStrut(10));

		addPanel(equipmentTotalsPanel);
	}

	public void loadFromClient()
	{
		clientDataProviderThreadProxy.tryAcquire(clientDataProvider ->
		{
			getState().setAttackerItems(new HashMap<>(clientDataProvider.getPlayerEquipment()));
			getState().setAttackStyle(clientDataProvider.getAttackStyle().toBuilder().build());
			getState().setBlowpipeDarts(clientDataProvider.getBlowpipeDarts());
			getState().setOnSlayerTask(clientDataProvider.playerIsOnSlayerTask());
			getState().setUsingChargeSpell(clientDataProvider.playerIsUsingChargeSpell());
			getState().setUsingMarkOfDarkness(clientDataProvider.playerIsUsingMarkOfDarkness());
			getState().setInWilderness(clientDataProvider.playerIsInWilderness());
			getState().getAttackerSkills().put(Skill.HITPOINTS, clientDataProvider.getPlayerSkills().getLevels().getOrDefault(Skill.HITPOINTS, 99));
			getState().getAttackerBoosts().put(Skill.HITPOINTS, clientDataProvider.getPlayerSkills().getBoosts().getOrDefault(Skill.HITPOINTS, 0));
			SwingUtilities.invokeLater(this::fromState);
		});
	}

	private void addPanel(Component child)
	{
		addPanel(child, this);
	}

	private void addPanel(Component child, JPanel parent)
	{
		parent.add(child);
		if (child instanceof StateBoundComponent)
		{
			stateBoundComponents.add((StateBoundComponent) child);
		}
		if (child instanceof StateVisibleComponent)
		{
			stateVisibleComponents.add((StateVisibleComponent) child);
		}
	}

	@Override
	public void toState()
	{
		stateBoundComponents.forEach(StateBoundComponent::toState);
	}

	@Override
	public void fromState()
	{
		stateBoundComponents.forEach(StateBoundComponent::fromState);
		stateVisibleComponents.forEach(StateVisibleComponent::updateVisibility);
	}

	public boolean isReady()
	{
		// ensure selected dart if using tbp
		if (blowpipeDartsSelectPanel.isVisible() && getState().getBlowpipeDarts() == null)
		{
			return false;
		}

		// ensure spell is selected if needed
		if (spellSelectPanel.isVisible() && getState().getSpell() == null)
		{
			return false;
		}

		// ensure selected attack style (wearing nothing is fine)
		return getState().getAttackStyle() != null;
	}

	public String getSummary()
	{
		if (!isReady())
		{
			return "Not Set";
		}

		ItemStats weapon = getState().getAttackerItems().get(EquipmentInventorySlot.WEAPON);
		String weaponName = weapon == null ? "Unarmed" : weapon.getName();
		return getState().getAttackStyle().getAttackType() + " - " + weaponName;
	}
}