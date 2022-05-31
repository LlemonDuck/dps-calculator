package com.duckblade.osrs.dpscalc.plugin.ui.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SelectAllFocusListener implements FocusListener
{

	private final JTextField parentTextField;

	@Override
	public void focusGained(FocusEvent e)
	{
		parentTextField.select(0, parentTextField.getText().length());
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		parentTextField.select(0, 0);
	}
}
