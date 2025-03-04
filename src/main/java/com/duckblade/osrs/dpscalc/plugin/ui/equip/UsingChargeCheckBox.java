package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJCheckBox;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;

@Singleton
public class UsingChargeCheckBox extends StateBoundJCheckBox implements StateVisibleComponent
{

	@Inject
	public UsingChargeCheckBox(PanelStateManager manager)
	{
		super(
			"Using Charge",
			manager,
			(ps, v) -> ps.getPlayer().getBuffs().setChargeSpell(v),
			ps -> ps.getPlayer().getBuffs().isChargeSpell()
		);

		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setValue(false);
		setEditable(true);
		setVisible(false);
	}

	@Override
	public void updateVisibility()
	{
		Spell spell = getState().getPlayer().getSpell();
		setVisible(spell != null && spell.getName().contains("Demonbane"));
	}
}
