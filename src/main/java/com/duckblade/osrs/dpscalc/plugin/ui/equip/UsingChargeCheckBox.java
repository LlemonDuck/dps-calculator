package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.maxhit.magic.SpellcastingMaxHitBonusComputable;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
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
			"Using Mark of Darkness",
			manager,
			PanelState::setUsingMarkOfDarkness,
			PanelState::isUsingMarkOfDarkness
		);

		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setValue(false);
		setEditable(true);
		setVisible(false);
	}

	@Override
	public void updateVisibility()
	{
		setVisible(SpellcastingMaxHitBonusComputable.GOD_SPELLS.contains(getState().getSpell()));
	}
}
