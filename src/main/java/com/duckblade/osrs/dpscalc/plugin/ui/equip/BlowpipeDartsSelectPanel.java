package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import static com.duckblade.osrs.dpscalc.calc.Constants.BLOWPIPE_IDS;
import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.plugin.config.BlowpipeDarts;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider.ALL_EQUIPMENT;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlowpipeDartsSelectPanel extends StateBoundJComboBox<EquipmentPiece> implements StateVisibleComponent
{

	@Inject
	public BlowpipeDartsSelectPanel(PanelStateManager manager)
	{
		super(
			Arrays.stream(BlowpipeDarts.values())
				.map(BlowpipeDarts::getItemId)
				.map(ALL_EQUIPMENT::get)
				.collect(Collectors.toList()),
			EquipmentPiece::getName,
			"Blowpipe Darts",
			manager,
			(ps, v) ->
			{
				EquipmentPiece weapon = ps.getPlayer().getEquipment().getWeapon();
				if (weapon != null)
				{
					weapon.setVars(weapon.getVars()
						.withBlowpipeDart(v));
				}
			},
			(ps) ->
			{
				EquipmentPiece weapon = ps.getPlayer().getEquipment().getWeapon();
				if (weapon == null || weapon.getVars() == null)
				{
					return null;
				}

				return weapon.getVars().getBlowpipeDart();
			}
		);

		setAlignmentX(CENTER_ALIGNMENT);
		setVisible(false);
		addBottomPadding(10);
	}

	public void updateVisibility()
	{
		EquipmentPiece weapon = getState().getPlayer().getEquipment().getWeapon();
		if (weapon == null || weapon.getVars() == null || weapon.getVars().getBlowpipeDart() == null)
		{
			setVisible(false);
			return;
		}

		setVisible(
			BLOWPIPE_IDS.contains(weapon.getVars().getBlowpipeDart().getId())
		);
	}
}
