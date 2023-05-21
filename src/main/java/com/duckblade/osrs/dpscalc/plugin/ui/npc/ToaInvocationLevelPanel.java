package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.ToaArena;
import com.duckblade.osrs.dpscalc.calc.ToaArenaComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.ComputeUtil;
import com.duckblade.osrs.dpscalc.plugin.ui.util.FocusLostAdapter;
import com.duckblade.osrs.dpscalc.plugin.ui.util.JTextFieldIntOnlyKeyAdapter;
import com.duckblade.osrs.dpscalc.plugin.ui.util.SelectAllFocusListener;
import com.google.common.base.Strings;
import lombok.Getter;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ToaInvocationLevelPanel extends JPanel implements StateBoundComponent, StateVisibleComponent
{

	@Getter
	private final PanelStateManager manager;

	private final ToaArenaComputable toaArenaComputable;

	private final JTextField invocationLevelField;

	private final List<Runnable> callbacks = new ArrayList<>();

	@Inject
	public ToaInvocationLevelPanel(PanelStateManager manager, ToaArenaComputable toaArenaComputable)
	{
		this.manager = manager;
		this.toaArenaComputable = toaArenaComputable;

		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
		setLayout(new GridLayout(2, 2));
		setVisible(false);

		invocationLevelField = new JTextField("0", 2);
		invocationLevelField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		invocationLevelField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		invocationLevelField.addFocusListener(new SelectAllFocusListener(invocationLevelField));
		invocationLevelField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());
		invocationLevelField.addFocusListener(new FocusLostAdapter(e -> onChange()));
		invocationLevelField.addActionListener(e -> onChange());

		add(new JLabel("Invocation Level"));
		add(invocationLevelField);
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
		getState().setToaInvocationLevel(Integer.parseInt(invocationLevelField.getText()));
	}

	@Override
	public void fromState()
	{
		invocationLevelField.setText(String.valueOf(getState().getToaInvocationLevel()));
	}

	@Override
	public void updateVisibility()
	{
		ComputeUtil.computeSilent(() -> setVisible(fightingInsideToa()));
	}

	private boolean fightingInsideToa()
	{
		ComputeContext ctx = new ComputeContext();
		ctx.put(ComputeInputs.DEFENDER_ATTRIBUTES, getState().getDefenderAttributes().toImmutable());

		ToaArena arena = ctx.get(toaArenaComputable);

		return arena == ToaArena.FIGHTING_WARDENS
			|| arena == ToaArena.FIGHTING_PATH_NPC;
	}

	private void coerce()
	{
		if (Strings.isNullOrEmpty(invocationLevelField.getText()))
		{
			invocationLevelField.setText("0");
		}
	}

	public void addCallback(Runnable r)
	{
		callbacks.add(r);
	}
}
