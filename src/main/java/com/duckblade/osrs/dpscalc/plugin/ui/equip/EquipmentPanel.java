package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.Player;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProviderThreadProxy;
import static com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.RuneLiteClientDataProvider.ALL_SLOTS;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.LoadFromClientButton;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.api.EquipmentInventorySlot;
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
		@Nullable ItemManager rlItemManager,
		PanelStateManager manager, ClientDataProviderThreadProxy clientDataProviderThreadProxy,
		EquipmentTotalsPanel equipmentTotalsPanel, PlayerCombatStyleSelectPanel playerCombatStyleSelectPanel,
		InWildernessCheckBox inWildernessCheckBox, OnSlayerTaskCheckBox onSlayerTaskCheckBox,
		UsingChargeCheckBox usingChargeCheckBox, UsingMarkOfDarknessCheckBox usingMarkOfDarknessCheckBox,
		BlowpipeDartsSelectPanel blowpipeDartsSelectPanel,
		ChinchompaDistancePanel chinchompaDistancePanel, SpellSelectPanel spellSelectPanel
	)
	{
		this.manager = manager;
		this.clientDataProviderThreadProxy = clientDataProviderThreadProxy;
		this.blowpipeDartsSelectPanel = blowpipeDartsSelectPanel;
		this.spellSelectPanel = spellSelectPanel;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 1000));

		add(new LoadFromClientButton(this::loadFromClient));

		add(Box.createVerticalStrut(10));

		JPanel slotPanel = new JPanel();
		slotPanel.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		slotPanel.setLayout(new BoxLayout(slotPanel, BoxLayout.Y_AXIS));
		slotPanel.setAlignmentX(CENTER_ALIGNMENT);
		add(slotPanel);

		for (EquipmentInventorySlot slot : ALL_SLOTS)
		{
			EquipmentSlotPanel innerPanel = new EquipmentSlotPanel(manager, rlItemManager, slot);
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

		addPanel(chinchompaDistancePanel);
		chinchompaDistancePanel.add(Box.createVerticalStrut(10));

		addPanel(blowpipeDartsSelectPanel);
		blowpipeDartsSelectPanel.addCallback(this::fromState);

		addPanel(playerCombatStyleSelectPanel);
		playerCombatStyleSelectPanel.addCallback(this::fromState);

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
			Player client = clientDataProvider.getPlayer();
			Player state = getState().getPlayer();

			state.setEquipment(client.getEquipment());
			state.setStyle(client.getStyle());
			state.setBuffs(client.getBuffs());

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
		Player player = getState().getPlayer();
		if (blowpipeDartsSelectPanel.isVisible() && player.getEquipment().getWeapon().getVars() == null)
		{
			return false;
		}

		// ensure spell is selected if needed
		if (spellSelectPanel.isVisible() && player.getSpell() == null)
		{
			return false;
		}

		// ensure selected attack style (wearing nothing is fine)
		return player.getStyle() != null;
	}

	public String getSummary()
	{
		if (!isReady())
		{
			return "Not Set";
		}

		Player player = getState().getPlayer();
		EquipmentPiece weapon = player.getEquipment().getWeapon();
		String weaponName = weapon == null ? "Unarmed" : weapon.getName();
		return player.getStyle().getType() + " - " + weaponName;
	}
}