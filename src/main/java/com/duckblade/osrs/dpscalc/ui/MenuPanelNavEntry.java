package com.duckblade.osrs.dpscalc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import net.runelite.client.plugins.config.ConfigPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

public class MenuPanelNavEntry extends JPanel
{

	private static final ImageIcon NAVIGATION_ICON;

	static
	{
		BufferedImage image = ImageUtil.loadImageResource(ConfigPlugin.class, "/util/arrow_right.png");
		image = ImageUtil.resizeImage(image, 25, 25);
		NAVIGATION_ICON = new ImageIcon(image);
	}

	private final JLabel descriptionLabel;

	public MenuPanelNavEntry(String title, String description, Runnable onClick)
	{
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorScheme.DARKER_GRAY_COLOR.darker());
		setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 40));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JButton navButton = new JButton(NAVIGATION_ICON);
		SwingUtil.removeButtonDecorations(navButton);
		add(navButton, BorderLayout.EAST);

		JPanel splitPanel = new JPanel();
		splitPanel.setLayout(new BoxLayout(splitPanel, BoxLayout.Y_AXIS));
		splitPanel.setOpaque(false);
		add(splitPanel, BorderLayout.CENTER);

		JLabel titleLabel = new JLabel(title);
		titleLabel.setForeground(Color.white);
		titleLabel.setFont(FontManager.getRunescapeBoldFont());
		splitPanel.add(titleLabel);

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBackground(ColorScheme.LIGHT_GRAY_COLOR);
		splitPanel.add(separator);

		descriptionLabel = new JLabel(description);
		descriptionLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		descriptionLabel.setFont(FontManager.getRunescapeSmallFont());
		splitPanel.add(descriptionLabel);

		final MouseAdapter m = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				onClick.run();
			}
		};
		titleLabel.addMouseListener(m);
		descriptionLabel.addMouseListener(m);
		splitPanel.addMouseListener(m);
		this.addMouseListener(m);
		navButton.addActionListener(e -> onClick.run());
	}

	public void setDescription(String text)
	{
		this.descriptionLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		this.descriptionLabel.setText(text);
	}

}
