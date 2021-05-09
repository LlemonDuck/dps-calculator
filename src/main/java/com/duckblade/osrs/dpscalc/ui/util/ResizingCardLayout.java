package com.duckblade.osrs.dpscalc.ui.util;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

public class ResizingCardLayout extends CardLayout
{

	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		Component current = getCurrentComponent(parent);
		if (current == null)
			return super.preferredLayoutSize(parent);

		Insets insets = parent.getInsets();
		Dimension size = new Dimension(current.getPreferredSize());
		size.width += insets.left + insets.right;
		size.height += insets.top + insets.bottom;
		return size;
	}
	
	private Component getCurrentComponent(Container parent)
	{
		for (Component child : parent.getComponents())
		{
			if (child.isVisible())
			{
				return child;
			}
		}
		return null;
	}
}
