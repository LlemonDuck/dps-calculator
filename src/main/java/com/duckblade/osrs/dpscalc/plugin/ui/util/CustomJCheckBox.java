package com.duckblade.osrs.dpscalc.plugin.ui.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Setter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

public class CustomJCheckBox extends JPanel
{

	JCheckBox checkBox;
	JLabel label;

	@Setter
	private Runnable callback;

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
		if (callback != null)
			callback.run();
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

}
