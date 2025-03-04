package com.duckblade.osrs.dpscalc.plugin.ui.result;

import com.duckblade.osrs.dpscalc.calc.DpsResultCache;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.function.Function;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

public class CalcResultLabel extends JPanel
{

	private final Function<DpsResultCache, String> getter;
	private final JLabel valueLabel;

	public CalcResultLabel(String title, Function<DpsResultCache, String> getter)
	{
		this.getter = getter;

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH - 12, 20));
		setVisible(false);

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(FontManager.getRunescapeBoldFont());
		titleLabel.setForeground(Color.white);
		add(titleLabel);

		Component glue = Box.createHorizontalGlue();
		glue.setBackground(Color.pink);
		add(glue);

		valueLabel = new JLabel(title);
		valueLabel.setFont(FontManager.getRunescapeBoldFont());
		valueLabel.setForeground(Color.white);
		add(valueLabel);
	}

	public void setValue(DpsResultCache dpsResultCache)
	{
		if (dpsResultCache == null)
		{
			setDisplay(null);
			return;
		}

		try
		{
			setDisplay(getter.apply(dpsResultCache));
		}
		catch (Exception e)
		{
			setDisplay(null);
		}
	}

	private void setDisplay(String newValue)
	{
		valueLabel.setText(newValue);
		setVisible(newValue != null);
	}

}
