package com.duckblade.osrs.dpscalc.plugin.ui.skills;

import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatCategory extends JPanel
{

	private static JPanel buildSubPanel(List<? extends StatBox> elements)
	{
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));
		elements.forEach(subPanel::add);
		return subPanel;
	}

	public StatCategory(String title, List<? extends StatBox> innerStats)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_COLOR, 5));
		setPreferredSize(new Dimension(200, 134));
		setMaximumSize(new Dimension(200, 134));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(titleLabel);

		List<? extends StatBox> upperStats = innerStats.subList(0, 3);
		JPanel upperPanel = buildSubPanel(upperStats);
		upperPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		add(upperPanel);

		List<? extends StatBox> lowerStats = innerStats.subList(3, innerStats.size());
		add(buildSubPanel(lowerStats));
	}
}
