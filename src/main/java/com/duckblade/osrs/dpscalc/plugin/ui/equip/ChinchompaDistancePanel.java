package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.gearbonus.ChinchompaDistanceGearBonus;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.ComputeUtil;
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
	private final ChinchompaDistanceGearBonus chinchompaDistanceGearBonus;

	private final JTextField distanceField;

	@Inject
	public ChinchompaDistancePanel(PanelStateManager manager, ChinchompaDistanceGearBonus chinchompaDistanceGearBonus)
	{
		this.manager = manager;
		this.chinchompaDistanceGearBonus = chinchompaDistanceGearBonus;

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
		getState().setAttackerDistance(Integer.parseInt(distanceField.getText()));
	}

	@Override
	public void fromState()
	{
		distanceField.setText(String.valueOf(getState().getAttackerDistance()));
	}

	@Override
	public void updateVisibility()
	{
		ComputeUtil.computeSilent(() ->
		{
			ComputeContext ctx = new ComputeContext();
			ctx.put(ComputeInputs.ATTACKER_ITEMS, getState().getAttackerItems());
			ctx.put(ComputeInputs.ATTACK_STYLE, getState().getAttackStyle());

			setVisible(chinchompaDistanceGearBonus.isApplicable(ctx));
		});
	}

	private void coerce()
	{
		if (Strings.isNullOrEmpty(distanceField.getText()))
		{
			distanceField.setText("1");
		}
	}
}
