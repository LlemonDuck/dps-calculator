package com.duckblade.osrs.dpscalc.plugin.ui.state.component;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJCheckBox;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import lombok.Getter;

public class StateBoundJCheckBox extends CustomJCheckBox implements StateBoundComponent
{

	@Getter
	private final PanelStateManager manager;

	private final BiConsumer<PanelState, Boolean> stateWriter;
	private final Predicate<PanelState> stateReader;

	public StateBoundJCheckBox(String text, PanelStateManager manager, BiConsumer<PanelState, Boolean> stateWriter, Predicate<PanelState> stateReader)
	{
		super(text);
		this.manager = manager;
		this.stateWriter = stateWriter;
		this.stateReader = stateReader;
		addCallback(this::toState);
	}

	@Override
	public void toState()
	{
		stateWriter.accept(getState(), getValue());
	}

	@Override
	public void fromState()
	{
		setValue(stateReader.test(getState()));
	}
}
