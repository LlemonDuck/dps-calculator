package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJCheckBox;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;

@Singleton
public class OnSlayerTaskCheckBox extends StateBoundJCheckBox implements StateVisibleComponent
{

	@Inject
	public OnSlayerTaskCheckBox(PanelStateManager manager)
	{
		super(
			"On Slayer Task",
			manager,
			(ps, v) -> ps.getPlayer().getBuffs().setOnSlayerTask(v),
			ps -> ps.getPlayer().getBuffs().isOnSlayerTask()
		);

		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setValue(false);
		setEditable(true);
		setVisible(false);
	}

	@Override
	public void updateVisibility()
	{
		// todo
		setVisible(true);
	}
}
