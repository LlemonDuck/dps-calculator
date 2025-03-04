package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.FocusLostAdapter;
import com.duckblade.osrs.dpscalc.plugin.ui.util.JTextFieldIntOnlyKeyAdapter;
import com.duckblade.osrs.dpscalc.plugin.ui.util.SelectAllFocusListener;
import com.google.common.base.Strings;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Getter;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class ChinchompaDistancePanel extends JPanel implements StateBoundComponent, StateVisibleComponent
{

	@Getter
	private final PanelStateManager manager;

	private final JTextField distanceField;

	@Inject
	public ChinchompaDistancePanel(PanelStateManager manager)
	{
		this.manager = manager;

		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 40));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 40));
		setLayout(new GridLayout(2, 2));
		setVisible(false);

		distanceField = new JTextField("1", 2);
		distanceField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		distanceField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		distanceField.addFocusListener(new SelectAllFocusListener(distanceField));
		distanceField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());
		distanceField.addFocusListener(new FocusLostAdapter(e -> toState()));
		distanceField.addActionListener(e -> toState());

		add(new JLabel("Distance"));
		add(distanceField);
		add(Box.createVerticalStrut(10));
	}

	@Override
	public void toState()
	{
		coerce();
		getState().getPlayer().getBuffs().setChinchompaDistance(Integer.parseInt(distanceField.getText()));
	}

	@Override
	public void fromState()
	{
		distanceField.setText(String.valueOf(getState().getPlayer().getBuffs().getChinchompaDistance()));
	}

	@Override
	public void updateVisibility()
	{
		EquipmentPiece weapon = getState().getPlayer().getEquipment().getWeapon();
		setVisible(weapon != null && weapon.getName().contains("chinchompa"));
	}

	private void coerce()
	{
		if (Strings.isNullOrEmpty(distanceField.getText()))
		{
			distanceField.setText("1");
		}
	}
}
