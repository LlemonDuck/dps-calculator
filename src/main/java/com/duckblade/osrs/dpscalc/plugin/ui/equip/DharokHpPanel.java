package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.multihit.DharoksDptComputable;
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
import net.runelite.api.Skill;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class DharokHpPanel extends JPanel implements StateBoundComponent, StateVisibleComponent
{

	@Getter
	private final PanelStateManager manager;
	private final DharoksDptComputable dharoksDptComputable;

	private final JTextField currentHpField, maxHpField;

	@Inject
	public DharokHpPanel(PanelStateManager manager, DharoksDptComputable dharoksDptComputable)
	{
		this.manager = manager;
		this.dharoksDptComputable = dharoksDptComputable;

		setMinimumSize(new Dimension(0, 70));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 70));
		setLayout(new GridLayout(3, 2));
		setVisible(false);

		currentHpField = new JTextField("1", 3);
		currentHpField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		currentHpField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		currentHpField.addFocusListener(new SelectAllFocusListener(currentHpField));
		currentHpField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());
		currentHpField.addFocusListener(new FocusLostAdapter(e -> toState()));
		currentHpField.addActionListener(e -> toState());

		maxHpField = new JTextField("99", 3);
		maxHpField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		maxHpField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		maxHpField.addFocusListener(new SelectAllFocusListener(maxHpField));
		maxHpField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());
		maxHpField.addFocusListener(new FocusLostAdapter(e -> toState()));
		maxHpField.addActionListener(e -> toState());

		add(new JLabel("Curr. HP"));
		add(new JLabel("Max HP"));
		add(currentHpField);
		add(maxHpField);
		add(Box.createVerticalStrut(10));
	}

	@Override
	public void toState()
	{
		coerce();
		int currentHp = Integer.parseInt(currentHpField.getText());
		int maxHp = Integer.parseInt(maxHpField.getText());

		getState().getAttackerSkills().put(Skill.HITPOINTS, maxHp);
		getState().getAttackerBoosts().put(Skill.HITPOINTS, currentHp - maxHp);
	}

	@Override
	public void fromState()
	{
		int maxHp = getState().getAttackerSkills().getOrDefault(Skill.HITPOINTS, 99);
		int currentHp = maxHp + getState().getAttackerBoosts().getOrDefault(Skill.HITPOINTS, 0);

		currentHpField.setText(String.valueOf(currentHp));
		maxHpField.setText(String.valueOf(maxHp));
	}

	@Override
	public void updateVisibility()
	{
		ComputeUtil.computeSilent(() ->
		{
			ComputeContext ctx = new ComputeContext();
			ctx.put(ComputeInputs.ATTACKER_ITEMS, getState().getAttackerItems());
			ctx.put(ComputeInputs.ATTACK_STYLE, getState().getAttackStyle());

			setVisible(dharoksDptComputable.isApplicable(ctx));
		});
	}

	private void coerce()
	{
		if (Strings.isNullOrEmpty(currentHpField.getText()))
		{
			currentHpField.setText("1");
		}
		if (Strings.isNullOrEmpty(maxHpField.getText()))
		{
			currentHpField.setText("99");
		}
	}
}
