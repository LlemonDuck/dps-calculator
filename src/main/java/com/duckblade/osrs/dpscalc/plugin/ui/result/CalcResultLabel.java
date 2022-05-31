package com.duckblade.osrs.dpscalc.plugin.ui.result;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.exceptions.MissingInputException;
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

	private final Function<ComputeContext, String> getter;
	private final JLabel valueLabel;

	public CalcResultLabel(String title, Function<ComputeContext, String> getter)
	{
		this.getter = getter;

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

	public void setValue(ComputeContext context)
	{
		if (context == null)
		{
			setDisplay(null);
			return;
		}

		try
		{
			setDisplay(getter.apply(context));
		}
		catch (DpsComputeException e)
		{
			setDisplay(null);
			if (e.getCause() instanceof MissingInputException)
			{
				// ignore
			}
		}
	}

	private void setDisplay(String newValue)
	{
		valueLabel.setText(newValue);
		setVisible(newValue != null);
	}

}
