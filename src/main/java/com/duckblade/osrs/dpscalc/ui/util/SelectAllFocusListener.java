package com.duckblade.osrs.dpscalc.ui.util;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
