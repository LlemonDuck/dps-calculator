package com.duckblade.osrs.dpscalc.plugin.ui.state.component;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import lombok.Getter;

public class StateBoundJComboBox<T> extends CustomJComboBox<T> implements StateBoundComponent
{

	@Getter
	private final PanelStateManager manager;

	private final BiConsumer<PanelState, T> stateWriter;
	private final Function<PanelState, T> stateReader;

	public StateBoundJComboBox(List<T> items, Function<T, String> displayMapper, String title, PanelStateManager manager, BiConsumer<PanelState, T> stateWriter, Function<PanelState, T> stateReader)
	{
		super(items, displayMapper, title);
		this.manager = manager;
		this.stateWriter = stateWriter;
		this.stateReader = stateReader;
		addCallback(this::toState);
	}

	@Override
	public void toState()
	{
		if (stateWriter != null)
		{
			stateWriter.accept(getState(), getValue());
		}
	}

	@Override
	public void fromState()
	{
		setValue(stateReader.apply(getState()));
	}
}
