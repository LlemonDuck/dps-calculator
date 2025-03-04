package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory;
import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.PlayerCombatStyle;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayerCombatStyleSelectPanel extends StateBoundJComboBox<PlayerCombatStyle>
{

	private EquipmentCategory previous = null;

	@Inject
	public PlayerCombatStyleSelectPanel(PanelStateManager manager)
	{
		super(
			EquipmentCategory.UNARMED.getStyles(),
			PlayerCombatStyle::getName,
			"Attack Style",
			manager,
			(ps, v) -> ps.getPlayer().setStyle(v),
			(ps) -> ps.getPlayer().getStyle()
		);

		setAlignmentX(CENTER_ALIGNMENT);
		addBottomPadding(10);
	}

	@Override
	public void fromState()
	{
		if (previous != (previous = currentWeaponCategory()))
		{
			List<PlayerCombatStyle> weaponStyles = currentWeaponCategory().getStyles();

			List<PlayerCombatStyle> selectableStyles = new ArrayList<>(weaponStyles.size() + 1);
			selectableStyles.addAll(weaponStyles);
			selectableStyles.add(PlayerCombatStyle.MANUAL_CAST);
			setItems(selectableStyles);
		}

		super.fromState();
	}

	private EquipmentCategory currentWeaponCategory()
	{
		EquipmentPiece weapon = getState().getPlayer().getEquipment().getWeapon();
		if (weapon == null)
		{
			return EquipmentCategory.UNARMED;
		}

		return weapon.getCategory();
	}
}
