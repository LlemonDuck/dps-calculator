package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.EquipmentInventorySlot;

@Singleton
public class AttackStyleSelectPanel extends StateBoundJComboBox<AttackStyle>
{

	private WeaponCategory previousWeaponCategory = null;

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
		addBottomPadding(10);
	}

	@Override
	public void fromState()
	{
		if (previousWeaponCategory != (previousWeaponCategory = currentWeaponCategory()))
		{
			List<AttackStyle> weaponStyles = getState().getAttackerItems()
				.getOrDefault(EquipmentInventorySlot.WEAPON, ItemStats.EMPTY)
				.getWeaponCategory()
				.getAttackStyles();

			List<AttackStyle> selectableStyles = new ArrayList<>(weaponStyles.size() + 1);
			selectableStyles.addAll(weaponStyles);
			selectableStyles.add(AttackStyle.MANUAL_CAST);
			setItems(selectableStyles);
		}

		super.fromState();
	}

	private WeaponCategory currentWeaponCategory()
	{
		return getState().getAttackerItems()
			.getOrDefault(EquipmentInventorySlot.WEAPON, ItemStats.EMPTY)
			.getWeaponCategory();
	}
}
