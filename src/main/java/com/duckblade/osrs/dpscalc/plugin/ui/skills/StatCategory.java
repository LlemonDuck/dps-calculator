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

		int rows = (int) Math.ceil(innerStats.size() / 3.0);
		setPreferredSize(new Dimension(200, 24 + 70 * rows));
		setMaximumSize(new Dimension(200, 24 + 70 * rows));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(titleLabel);

		for (int i = 0; i < innerStats.size(); i += 3)
		{
			List<? extends StatBox> rowStats = innerStats.subList(i, Math.min(i + 3, innerStats.size()));
			JPanel rowPanel = buildSubPanel(rowStats);
			if (i + 3 < innerStats.size())
			{
				rowPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
			}
			add(rowPanel);
		}
	}
}
