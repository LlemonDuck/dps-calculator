package com.duckblade.osrs.dpscalc.ui.result;

import com.duckblade.osrs.dpscalc.calc.CalcResult;
import java.awt.Color;
import java.awt.Component;
import java.util.function.Function;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.FontManager;

public class CalcResultLabel extends JPanel
{

	private final Function<CalcResult, String> displayMapper;
	private final JLabel valueLabel;

	public CalcResultLabel(String title, Function<CalcResult, String> displayMapper)
	{
		this.displayMapper = displayMapper;

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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

	public void setValue(CalcResult result)
	{
		if (result == null)
			setDisplay(null);
		else
			setDisplay(displayMapper.apply(result));
	}

	private void setDisplay(String newValue)
	{
		valueLabel.setText(newValue);
		setVisible(newValue != null);
	}

}
