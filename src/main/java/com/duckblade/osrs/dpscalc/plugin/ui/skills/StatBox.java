package com.duckblade.osrs.dpscalc.plugin.ui.skills;

import com.duckblade.osrs.dpscalc.plugin.ui.util.JTextFieldIntOnlyKeyAdapter;
import com.duckblade.osrs.dpscalc.plugin.ui.util.SelectAllFocusListener;
import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.runelite.client.util.ImageUtil;

public class StatBox extends JPanel
{

	private final JTextField valueField;

	public StatBox(String iconName, boolean editable)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		BufferedImage icon;
		try
		{
			icon = ImageUtil.loadImageResource(StatBox.class, "icon_" + iconName + ".png");
			icon = ImageUtil.resizeCanvas(icon, 25, 25);
		}
		catch (IllegalArgumentException e)
		{
			icon = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
		}
		JLabel iconLabel = new JLabel(new ImageIcon(icon));
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(iconLabel);

		valueField = new JTextField("0", 3);
		valueField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		valueField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		valueField.addFocusListener(new SelectAllFocusListener(valueField));
		valueField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());
		valueField.setEditable(editable);

		add(valueField);
	}

	public void setValue(int value)
	{
		SwingUtilities.invokeLater(() -> valueField.setText(Integer.toString(value)));
	}

	public int getValue()
	{
		if (valueField.getText().isEmpty())
			return 0;
		return Integer.parseInt(valueField.getText());
	}

	public void setEditable(boolean editable)
	{
		valueField.setEditable(editable);
	}
}
