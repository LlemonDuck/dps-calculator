package com.duckblade.osrs.dpscalc.plugin.ui;

import java.awt.Color;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;

@Singleton
public class LoadingScreen extends JPanel
{

	public LoadingScreen()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel title = new JLabel("Loading...");
		title.setFont(FontManager.getRunescapeFont().deriveFont(32.0f));
		title.setForeground(Color.yellow);
		add(title);

		add(Box.createVerticalStrut(10));

		JLabel detail = new JLabel("If loading does not succeed, you may have a firewall preventing access to github.com.");
		detail.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		detail.setHorizontalAlignment(SwingConstants.CENTER);
		detail.setAlignmentX(SwingConstants.CENTER);
		add(detail);
	}

}
