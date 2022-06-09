package com.duckblade.osrs.dpscalc.plugin.ui.util;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FocusLostAdapter extends FocusAdapter
{

	private final Consumer<FocusEvent> handler;

	@Override
	public final void focusLost(FocusEvent e)
	{
		handler.accept(e);
	}
}
