package com.duckblade.osrs.dpscalc.plugin.ui.state.component;

import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatBox;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import lombok.Getter;

public class StateBoundStatBox extends StatBox implements StateBoundComponent
{

	@Getter
	private final PanelStateManager manager;

	private final ObjIntConsumer<PanelState> stateWriter;
	private final ToIntFunction<PanelState> stateReader;

	public StateBoundStatBox(PanelStateManager manager, String iconName, String title, boolean editable, ObjIntConsumer<PanelState> stateWriter, ToIntFunction<PanelState> stateReader)
	{
		super(iconName, title, editable);
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
		setValue(stateReader.applyAsInt(getState()));
	}
}
