package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.gearbonus.BlackMaskGearBonus;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJCheckBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.ComputeUtil;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;

@Singleton
public class OnSlayerTaskCheckBox extends StateBoundJCheckBox implements StateVisibleComponent
{

	private final BlackMaskGearBonus blackMaskGearBonus;

	@Inject
	public OnSlayerTaskCheckBox(PanelStateManager manager, BlackMaskGearBonus blackMaskGearBonus)
	{
		super(
			"On Slayer Task",
			manager,
			PanelState::setOnSlayerTask,
			PanelState::isOnSlayerTask
		);
		this.blackMaskGearBonus = blackMaskGearBonus;

		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setValue(false);
		setEditable(true);
		setVisible(false);
	}

	@Override
	public void updateVisibility()
	{
		ComputeUtil.computeSilent(() ->
		{
			ComputeContext ctx = new ComputeContext();
			ctx.put(ComputeInputs.ATTACKER_ITEMS, getState().getAttackerItems());

			setVisible(blackMaskGearBonus.isApplicable(ctx));
		});
	}
}
