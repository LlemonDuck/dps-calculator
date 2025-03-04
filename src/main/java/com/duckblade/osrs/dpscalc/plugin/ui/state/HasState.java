package com.duckblade.osrs.dpscalc.plugin.ui.state;

import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;

interface HasState
{

	PanelStateManager getManager();

	default ComputeInput getState()
	{
		return getManager().currentState();
	}

}
