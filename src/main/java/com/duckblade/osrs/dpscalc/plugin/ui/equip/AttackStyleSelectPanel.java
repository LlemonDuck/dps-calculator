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
		WeaponCategory currentWeaponCategory = getState().getAttackerItems()
				.getOrDefault(EquipmentInventorySlot.WEAPON, ItemStats.EMPTY)
				.getWeaponCategory();

		if (previousWeaponCategory != (previousWeaponCategory = currentWeaponCategory))
		{
			List<AttackStyle> selectableStyles = new ArrayList<>(currentWeaponCategory.getAttackStyles().size() + 1);
			selectableStyles.addAll(currentWeaponCategory.getAttackStyles());
			// No matter the weapon, you can always manually cast spells
			selectableStyles.add(AttackStyle.MANUAL_CAST);
			setItems(selectableStyles);

			// reset attack style if it is no longer valid
			if (!selectableStyles.contains(getState().getAttackStyle()))
			{
				getState().setAttackStyle(null);
			}
		}

		super.fromState();
	}
}
