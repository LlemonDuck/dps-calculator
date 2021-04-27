package com.duckblade.osrs.dpscalc.ui.util;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

public class CustomJCheckBox extends JPanel
{
	
	JCheckBox checkBox;
	JLabel label;
	
	public CustomJCheckBox(String text)
	{
		setLayout(new BorderLayout());
		
		checkBox = new JCheckBox();
		checkBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		add(checkBox, BorderLayout.WEST);
		
		label = new JLabel(text);
		label.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		add(label, BorderLayout.CENTER);
		
		final MouseAdapter clickListener = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				setValue(!getValue());
			}
		};
		label.addMouseListener(clickListener);
		addMouseListener(clickListener);
	}
	
	public boolean getValue()
	{
		return isVisible() && checkBox.isSelected();
	}
	
	public void setValue(boolean newValue)
	{
		checkBox.setSelected(newValue);
	}
	
	public void setEditable(boolean editable)
	{
		this.checkBox.setEnabled(editable);
	}
	
}
