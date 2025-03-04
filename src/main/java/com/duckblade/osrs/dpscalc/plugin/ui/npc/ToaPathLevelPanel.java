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
public class ToaPathLevelPanel extends JPanel implements StateBoundComponent, StateVisibleComponent
{

	@Getter
	private final PanelStateManager manager;

	private final ToaArenaComputable toaArenaComputable;

	private final JTextField pathLevelField;

	private final List<Runnable> callbacks = new ArrayList<>();

	@Inject
	public ToaPathLevelPanel(PanelStateManager manager, ToaArenaComputable toaArenaComputable)
	{
		this.manager = manager;
		this.toaArenaComputable = toaArenaComputable;

		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
		setLayout(new GridLayout(2, 2));
		setVisible(false);

		pathLevelField = new JTextField("0", 2);
		pathLevelField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		pathLevelField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		pathLevelField.addFocusListener(new SelectAllFocusListener(pathLevelField));
		pathLevelField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());
		pathLevelField.addFocusListener(new FocusLostAdapter(e -> onChange()));
		pathLevelField.addActionListener(e -> onChange());

		add(new JLabel("Path Level"));
		add(pathLevelField);
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
		getState().setToaPathLevel(Integer.parseInt(pathLevelField.getText()));
	}

	@Override
	public void fromState()
	{
		pathLevelField.setText(String.valueOf(getState().getToaPathLevel()));
	}

	@Override
	public void updateVisibility()
	{
		ComputeUtil.computeSilent(() -> setVisible(fightingInsideToaPath()));
	}

	private boolean fightingInsideToaPath()
	{
		ComputeContext ctx = new ComputeContext();
		ctx.put(ComputeInputs.DEFENDER_ATTRIBUTES, getState().getDefenderAttributes().toImmutable());

		return ctx.get(toaArenaComputable) == ToaArena.FIGHTING_PATH_NPC;
	}

	private void coerce()
	{
		if (Strings.isNullOrEmpty(pathLevelField.getText()))
		{
			pathLevelField.setText("0");
		}
	}

	public void addCallback(Runnable r)
	{
		callbacks.add(r);
	}
}
