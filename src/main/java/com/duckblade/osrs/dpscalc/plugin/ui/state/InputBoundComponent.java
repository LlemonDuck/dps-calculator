package com.duckblade.osrs.dpscalc.plugin.ui.state;

import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;

public interface InputBoundComponent<T>
{

	T getValue(ComputeInput state);

	void setValue(ComputeInput input);

	void update(T currentValue);

	default void update(ComputeInput state)
	{
		update(getValue(state));
	}

}
