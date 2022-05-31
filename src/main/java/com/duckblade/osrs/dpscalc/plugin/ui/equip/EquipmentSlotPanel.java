package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

public class EquipmentSlotPanel extends JPanel implements StateBoundComponent
{

	private static final ImageIcon CLEAR_ICON = new ImageIcon(ImageUtil.loadImageResource(EquipmentSlotPanel.class, "icon_clear.png"));

	private static List<ItemStats> getItemsForSlot(ItemStatsProvider itemStatsProvider, int slotIx)
	{
		return itemStatsProvider.getAll()
			.stream()
			.filter(is -> is.getSlot() == slotIx)
			.sorted(Comparator.comparing(ItemStats::getName))
			.collect(Collectors.toList());
	}

	@Getter
	private final PanelStateManager manager;
	private final ItemManager rlItemManager;

	private final ImageIcon defaultIcon;
	private final JLabel imageLabel;
	private final StateBoundJComboBox<ItemStats> comboBox;

	public EquipmentSlotPanel(PanelStateManager manager, ItemManager rlItemManager, EquipmentInventorySlot slot, ItemStatsProvider itemStatsProvider)
	{
		this.manager = manager;
		this.rlItemManager = rlItemManager;

		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 25));

		defaultIcon = new ImageIcon(ImageUtil.resizeImage(ImageUtil.loadImageResource(getClass(), "slot_" + slot.getSlotIdx() + ".png"), 25, 25));
		imageLabel = new JLabel(defaultIcon);
		imageLabel.setMaximumSize(new Dimension(25, 25));
		imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		add(imageLabel, BorderLayout.WEST);

		comboBox = new StateBoundJComboBox<>(
			getItemsForSlot(itemStatsProvider, slot.getSlotIdx()),
			ItemStats::getName,
			null,
			manager,
			(ps, v) ->
			{
				if (v == null)
				{
					ps.getAttackerItems().remove(slot);
				}
				else
				{
					ps.getAttackerItems().put(slot, v);
				}
			},
			ps -> ps.getAttackerItems().get(slot)
		);
		comboBox.enableAutocomplete();
		comboBox.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 75, 25));
		comboBox.addCallback(this::updateImage);
		add(comboBox, BorderLayout.CENTER);

		JButton clearButton = new JButton(CLEAR_ICON);
		clearButton.setPreferredSize(new Dimension(25, 25));
		SwingUtil.removeButtonDecorations(clearButton);
		clearButton.addActionListener(e ->
		{
			getState().getAttackerItems().remove(slot);
			fromState();
		});
		add(clearButton, BorderLayout.EAST);
	}

	private void updateImage()
	{
		SwingUtilities.invokeLater(() ->
		{
			ItemStats newValue = comboBox.getValue();
			if (newValue == null)
			{
				imageLabel.setIcon(defaultIcon);
			}
			else if (rlItemManager != null)
			{
				AsyncBufferedImage newIcon = rlItemManager.getImage(newValue.getItemId());
				BufferedImage resized = ImageUtil.resizeImage(newIcon, 25, 25); // if async, this does nothing
				newIcon.onLoaded(() ->
				{
					// only fires on async
					BufferedImage resizedDelayed = ImageUtil.resizeImage(newIcon, 25, 25);
					imageLabel.setIcon(new ImageIcon(resizedDelayed));
				});
				imageLabel.setIcon(new ImageIcon(resized));
			}
		});
	}

	@Override
	public void toState()
	{
		comboBox.toState();
	}

	@Override
	public void fromState()
	{
		comboBox.fromState();
		updateImage();
	}

	public void addCallback(Runnable r)
	{
		comboBox.addCallback(r);
	}
}
