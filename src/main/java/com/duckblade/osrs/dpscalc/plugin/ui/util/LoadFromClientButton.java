package com.duckblade.osrs.dpscalc.plugin.ui.util;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LoadFromClientButton extends JPanel
{

	public LoadFromClientButton(Runnable callback)
	{
		this("Load From Client", callback);
	}

	public LoadFromClientButton(String text, Runnable callback)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton loadFromClientButton = new JButton(text);
		loadFromClientButton.addActionListener(e -> callback.run());
		loadFromClientButton.setAlignmentX(CENTER_ALIGNMENT);
		add(loadFromClientButton);

		add(Box.createVerticalStrut(10));
	}
}
