package com.duckblade.osrs.dpscalc.plugin.ui.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// only allows ascii digits and minus sign
public class JTextFieldIntOnlyKeyAdapter extends KeyAdapter
{

	private static boolean isDigit(char c)
	{
		// java.lang.Character.isDigit includes non-ascii codes
		return c >= '0' && c <= '9';
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		char targetKey = e.getKeyChar();
		if (!isDigit(targetKey) && targetKey != '-')
			e.consume();
	}
}
