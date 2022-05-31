package com.duckblade.osrs.dpscalc.plugin.ui.state;

public interface StateBoundComponent extends HasState
{

	default void toState()
	{
	}

	void fromState();

}
