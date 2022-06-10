package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.skills.TheatreEntryModeSkillScaling;
import com.duckblade.osrs.dpscalc.calc.defender.skills.TheatreSkillScaling;
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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Getter;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class RaidPartySizePanel extends JPanel implements StateBoundComponent, StateVisibleComponent
{

	@Getter
	private final PanelStateManager manager;
	private final TheatreSkillScaling theatreSkillScaling;
	private final TheatreEntryModeSkillScaling theatreEntryModeSkillScaling;

	private final JTextField partySizeField;

	private final List<Runnable> callbacks = new ArrayList<>();

	@Inject
	public RaidPartySizePanel(PanelStateManager manager, TheatreSkillScaling theatreSkillScaling, TheatreEntryModeSkillScaling theatreEntryModeSkillScaling)
	{
		this.manager = manager;
		this.theatreSkillScaling = theatreSkillScaling;
		this.theatreEntryModeSkillScaling = theatreEntryModeSkillScaling;

		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
		setLayout(new GridLayout(2, 2));
		setVisible(false);

		partySizeField = new JTextField("1", 2);
		partySizeField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		partySizeField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		partySizeField.addFocusListener(new SelectAllFocusListener(partySizeField));
		partySizeField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());
		partySizeField.addFocusListener(new FocusLostAdapter(e -> onChange()));
		partySizeField.addActionListener(e -> onChange());

		add(new JLabel("Party Size"));
		add(partySizeField);
		add(Box.createVerticalStrut(10));
	}

	private void onChange()
	{
		toState();
		callbacks.forEach(Runnable::run);
	}

	@Override
	public void toState()
	{
		coerce();
		getState().setRaidPartySize(Integer.parseInt(partySizeField.getText()));
	}

	@Override
	public void fromState()
	{
		partySizeField.setText(String.valueOf(getState().getRaidPartySize()));
	}

	@Override
	public void updateVisibility()
	{
		ComputeUtil.computeSilent(() ->
		{
			ComputeContext ctx = new ComputeContext();
			ctx.put(ComputeInputs.DEFENDER_ATTRIBUTES, getState().getDefenderAttributes().toImmutable());

			setVisible(theatreSkillScaling.isApplicable(ctx) || theatreEntryModeSkillScaling.isApplicable(ctx));
		});
	}

	private void coerce()
	{
		if (Strings.isNullOrEmpty(partySizeField.getText()))
		{
			partySizeField.setText("1");
		}
	}

	public void addCallback(Runnable r)
	{
		callbacks.add(r);
	}
}
