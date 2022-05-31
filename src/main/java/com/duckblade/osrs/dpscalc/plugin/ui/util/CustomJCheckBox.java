package com.duckblade.osrs.dpscalc.plugin.ui.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

public class CustomJCheckBox extends JPanel
{

	JCheckBox checkBox;
	JLabel label;

	private final List<Runnable> callbacks = new ArrayList<>();

	public CustomJCheckBox(String text)
	{
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 25));

		checkBox = new JCheckBox();
		checkBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		checkBox.addActionListener(e -> invokeCallback());
		add(checkBox, BorderLayout.WEST);

		label = new JLabel(text);
		label.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		add(label, BorderLayout.CENTER);

		final MouseAdapter clickListener = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (checkBox.isEnabled())
				{
					setValue(!getValue());
					invokeCallback();
				}
			}
		};
		label.addMouseListener(clickListener);
		addMouseListener(clickListener);
	}

	private void invokeCallback()
	{
		callbacks.forEach(Runnable::run);
	}

	public boolean getValue()
	{
		return isVisible() && checkBox.isSelected();
	}

	public void setValue(boolean newValue)
	{
		checkBox.setSelected(newValue);
		invokeCallback();
	}

	public void setEditable(boolean editable)
	{
		this.checkBox.setEnabled(editable);
	}

	public void addCallback(Runnable r)
	{
		callbacks.add(r);
	}

}
