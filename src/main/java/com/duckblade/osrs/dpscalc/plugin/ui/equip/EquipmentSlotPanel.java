package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.plugin.osdata.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
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
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

public class EquipmentSlotPanel extends JPanel
{

	private static final ImageIcon CLEAR_ICON = new ImageIcon(ImageUtil.loadImageResource(EquipmentSlotPanel.class, "icon_clear.png"));

	private final ItemManager rlItemManager;
	private final Runnable callback;

	private final ImageIcon defaultIcon;
	private final JLabel imageLabel;
	private final CustomJComboBox<ItemStats> comboBox;

	public EquipmentSlotPanel(ItemManager rlItemManager, ItemStatsProvider itemStatsProvider, EquipmentInventorySlot slot, Runnable callback)
	{
		this.rlItemManager = rlItemManager;
		this.callback = callback;

		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 25));

		defaultIcon = new ImageIcon(ImageUtil.resizeImage(ImageUtil.loadImageResource(getClass(), "slot_" + slot.getSlotIdx() + ".png"), 25, 25));
		imageLabel = new JLabel(defaultIcon);
		imageLabel.setMaximumSize(new Dimension(25, 25));
		imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		add(imageLabel, BorderLayout.WEST);

		int slotIx = slot.getSlotIdx();
		List<ItemStats> slotItems = itemStatsProvider.getAll()
			.stream()
			.filter(is -> is.getSlot() == slotIx)
			.sorted(Comparator.comparing(ItemStats::getName))
			.collect(Collectors.toList());
		comboBox = new CustomJComboBox<>(slotItems, ItemStats::getName, null);
		comboBox.enableAutocomplete();
		comboBox.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 75, 25));
		comboBox.setCallback(() ->
		{
			updateImage();
			callback.run();
		});
		add(comboBox, BorderLayout.CENTER);

		JButton clearButton = new JButton(CLEAR_ICON);
		clearButton.setPreferredSize(new Dimension(25, 25));
		SwingUtil.removeButtonDecorations(clearButton);
		clearButton.addActionListener(e -> setValue(null));
		add(clearButton, BorderLayout.EAST);
	}

	private void updateImage()
	{
		SwingUtilities.invokeLater(() ->
		{
			ItemStats newValue = getValue();
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

	public ItemStats getValue()
	{
		return comboBox.getValue();
	}

	public void setValue(ItemStats newValue)
	{
		comboBox.setValue(newValue);
		updateImage();
		callback.run();
	}
}
