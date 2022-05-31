package com.duckblade.osrs.dpscalc.plugin.ui.state;

interface HasState
{

	PanelStateManager getManager();

	default PanelState getState()
	{
		return getManager().currentState();
	}

}
