package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpellSelectPanel extends StateBoundJComboBox<Spell> implements StateVisibleComponent
{

	@Inject
	public SpellSelectPanel(PanelStateManager manager)
	{
		super(
			Arrays.asList(Spell.values()),
			Spell::getDisplayName,
			"Spell",
			manager,
			PanelState::setSpell,
			PanelState::getSpell
		);

		setAlignmentX(CENTER_ALIGNMENT);
		setVisible(false);
	}

	@Override
	public void updateVisibility()
	{
		AttackStyle style = getState().getAttackStyle();
		setVisible(style != null && style.getAttackType() == AttackType.MAGIC);
		if (!isVisible())
		{
			setValue(null);
		}
	}
}
