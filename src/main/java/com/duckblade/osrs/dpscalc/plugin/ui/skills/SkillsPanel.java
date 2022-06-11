package com.duckblade.osrs.dpscalc.plugin.ui.skills;

import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProviderThreadProxy;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundStatBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.LoadFromClientButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.api.Skill;
import static net.runelite.api.Skill.ATTACK;
import static net.runelite.api.Skill.DEFENCE;
import static net.runelite.api.Skill.MAGIC;
import static net.runelite.api.Skill.PRAYER;
import static net.runelite.api.Skill.RANGED;
import static net.runelite.api.Skill.STRENGTH;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class SkillsPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<PanelState> writer(Function<PanelState, Map<Skill, Integer>> mapSelector, Skill skill)
	{
		return (state, lvl) -> mapSelector.apply(state).put(skill, lvl);
	}

	private static ToIntFunction<PanelState> reader(Function<PanelState, Map<Skill, Integer>> mapSelector, Skill skill)
	{
		return state -> mapSelector.apply(state).getOrDefault(skill, 0);
	}

	@Getter
	private final PanelStateManager manager;
	private final ClientDataProviderThreadProxy clientDataProviderThreadProxy;
	private final List<StateBoundStatBox> statBoxes;
	private final List<StateBoundStatBox> boostBoxes;

	@Inject
	public SkillsPanel(PanelStateManager manager, ClientDataProviderThreadProxy clientDataProviderThreadProxy)
	{
		this.manager = manager;
		this.clientDataProviderThreadProxy = clientDataProviderThreadProxy;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new LoadFromClientButton(this::loadFromClient));

		statBoxes = Arrays.asList(
			new StateBoundStatBox(manager, "att", "Attack", true, writer(PanelState::getAttackerSkills, ATTACK), reader(PanelState::getAttackerSkills, ATTACK)),
			new StateBoundStatBox(manager, "str", "Strength", true, writer(PanelState::getAttackerSkills, STRENGTH), reader(PanelState::getAttackerSkills, STRENGTH)),
			new StateBoundStatBox(manager, "def", "Defence", true, writer(PanelState::getAttackerSkills, DEFENCE), reader(PanelState::getAttackerSkills, DEFENCE)),
			new StateBoundStatBox(manager, "mage", "Magic", true, writer(PanelState::getAttackerSkills, MAGIC), reader(PanelState::getAttackerSkills, MAGIC)),
			new StateBoundStatBox(manager, "range", "Ranged", true, writer(PanelState::getAttackerSkills, RANGED), reader(PanelState::getAttackerSkills, RANGED)),
			new StateBoundStatBox(manager, "prayer", "Prayer", true, writer(PanelState::getAttackerSkills, PRAYER), reader(PanelState::getAttackerSkills, PRAYER))
		);
		add(new StatCategory("Player Stats", statBoxes));

		add(Box.createVerticalStrut(10));

		boostBoxes = Arrays.asList(
			new StateBoundStatBox(manager, "att", "Attack", true, writer(PanelState::getAttackerBoosts, ATTACK), reader(PanelState::getAttackerBoosts, ATTACK)),
			new StateBoundStatBox(manager, "str", "Strength", true, writer(PanelState::getAttackerBoosts, STRENGTH), reader(PanelState::getAttackerBoosts, STRENGTH)),
			new StateBoundStatBox(manager, "def", "Defence", true, writer(PanelState::getAttackerBoosts, DEFENCE), reader(PanelState::getAttackerBoosts, DEFENCE)),
			new StateBoundStatBox(manager, "mage", "Magic", true, writer(PanelState::getAttackerBoosts, MAGIC), reader(PanelState::getAttackerBoosts, MAGIC)),
			new StateBoundStatBox(manager, "range", "Ranged", true, writer(PanelState::getAttackerBoosts, RANGED), reader(PanelState::getAttackerBoosts, RANGED)),
			new StateBoundStatBox(manager, "prayer", "Prayer", true, writer(PanelState::getAttackerBoosts, PRAYER), reader(PanelState::getAttackerBoosts, PRAYER))
		);
		add(new StatCategory("Boosts", boostBoxes));

		add(Box.createVerticalStrut(10));

		JPanel commonBoostsPanel = new JPanel();
		commonBoostsPanel.setMaximumSize(new Dimension(200, 40));
		commonBoostsPanel.setLayout(new GridLayout(1, 3));
		commonBoostsPanel.add(new SkillBoostPresetButton(this, SkillBoostPreset.SUPER_COMBAT_POTION, "boost_super_combat_potion.png"));
		commonBoostsPanel.add(new SkillBoostPresetButton(this, SkillBoostPreset.RANGING_POTION, "boost_ranging_potion.png"));
		commonBoostsPanel.add(new SkillBoostPresetButton(this, SkillBoostPreset.IMBUED_HEART, "boost_imbued_heart.png"));
		add(commonBoostsPanel);

		add(Box.createVerticalStrut(10));

		List<SkillBoostPreset> presets = Arrays.asList(SkillBoostPreset.values());
		presets.sort(Comparator.comparing(SkillBoostPreset::getDisplayName));
		CustomJComboBox<SkillBoostPreset> presetSelect = new CustomJComboBox<>(presets, SkillBoostPreset::getDisplayName, null);
		add(presetSelect);

		JButton applyPresetButton = new JButton("Apply");
		applyPresetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		applyPresetButton.setMinimumSize(new Dimension(0, 35));
		applyPresetButton.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));
		applyPresetButton.addActionListener(e ->
		{
			SkillBoostPreset preset = presetSelect.getValue();
			applyBoostPreset(preset);
		});
		add(applyPresetButton);
	}

	public void loadFromClient()
	{
		clientDataProviderThreadProxy.tryAcquire(clientDataProvider ->
		{
			Skills playerSkills = clientDataProvider.getPlayerSkills();
			for (Skill s : new Skill[]{ATTACK, STRENGTH, DEFENCE, MAGIC, RANGED, PRAYER})
			{
				getState().getAttackerSkills().put(s, playerSkills.getLevels().getOrDefault(s, 0));
				getState().getAttackerBoosts().put(s, playerSkills.getBoosts().getOrDefault(s, 0));
			}
			SwingUtilities.invokeLater(this::fromState);
		});
	}

	@Override
	public void toState()
	{
		statBoxes.forEach(StateBoundStatBox::toState);
		boostBoxes.forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		statBoxes.forEach(StateBoundStatBox::fromState);
		boostBoxes.forEach(StateBoundStatBox::fromState);
	}

	public boolean isReady()
	{
		return getState().getAttackerSkills()
			.values()
			.stream()
			.anyMatch(i -> i != 0);
	}

	public String getSummary()
	{
		if (isReady())
		{
			return "Set";
		}

		return "Not Set";
	}

	public void applyBoostPreset(SkillBoostPreset boostPreset)
	{
		if (boostPreset != null)
		{
			boostPreset.apply(getState());
			boostBoxes.forEach(StateBoundStatBox::fromState);
		}
	}
}
