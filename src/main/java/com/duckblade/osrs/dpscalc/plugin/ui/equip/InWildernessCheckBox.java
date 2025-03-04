package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJCheckBox;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;

@Singleton
public class InWildernessCheckBox extends StateBoundJCheckBox implements StateVisibleComponent
{

	@Inject
	public InWildernessCheckBox(PanelStateManager manager)
	{
		super(
			"In Wilderness",
			manager,
			(ps, v) -> ps.getPlayer().getBuffs().setInWilderness(true),
			ps -> ps.getPlayer().getBuffs().isInWilderness()
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
