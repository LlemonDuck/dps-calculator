package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJCheckBox;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;

@Singleton
public class UsingMarkOfDarknessCheckBox extends StateBoundJCheckBox implements StateVisibleComponent
{

	@Inject
	public UsingMarkOfDarknessCheckBox(PanelStateManager manager)
	{
		super(
			"Using Mark of Darkness",
			manager,
			(ps, v) -> ps.getPlayer().getBuffs().setMarkOfDarknessSpell(v),
			ps -> ps.getPlayer().getBuffs().isMarkOfDarknessSpell()
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
		Spell spell = getState().getPlayer().getSpell();
		setVisible(spell != null);
	}
}
