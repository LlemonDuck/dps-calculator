package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.EquipmentInventorySlot;

@Singleton
public class AttackStyleSelectPanel extends StateBoundJComboBox<AttackStyle>
{

	@Inject
	public AttackStyleSelectPanel(PanelStateManager manager)
	{
		super(
			WeaponCategory.UNARMED.getAttackStyles(),
			AttackStyle::getDisplayName,
			"Attack Style",
			manager,
			PanelState::setAttackStyle,
			PanelState::getAttackStyle
		);

		setAlignmentX(CENTER_ALIGNMENT);
	}

	@Override
	public void fromState()
	{
		setItems(
			getState().getAttackerItems()
				.getOrDefault(EquipmentInventorySlot.WEAPON, ItemStats.EMPTY)
				.getWeaponCategory()
				.getAttackStyles()
		);
		super.fromState();
	}
}
