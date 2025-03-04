package com.duckblade.osrs.dpscalc.plugin.ui.skills;

import com.duckblade.osrs.dpscalc.calc.model.Player;
import com.duckblade.osrs.dpscalc.calc.model.PlayerSkills;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProviderThreadProxy;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
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
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
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
import static net.runelite.api.Skill.HERBLORE;
import static net.runelite.api.Skill.HITPOINTS;
import static net.runelite.api.Skill.MAGIC;
import static net.runelite.api.Skill.MINING;
import static net.runelite.api.Skill.PRAYER;
import static net.runelite.api.Skill.RANGED;
import static net.runelite.api.Skill.STRENGTH;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class SkillsPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<ComputeInput> writer(Function<Player, PlayerSkills> mapSelector, Skill skill)
	{
		return (state, lvl) -> mapSelector.apply(state.getPlayer()).set(skill, lvl);
	}

	private static ToIntFunction<ComputeInput> reader(Function<Player, PlayerSkills> mapSelector, Skill skill)
	{
		return state -> mapSelector.apply(state.getPlayer()).get(skill);
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
			new StateBoundStatBox(
				manager,
				"hp",
				"Hitpoints",
				true,
				writer(Player::getSkills, HITPOINTS),
				reader(Player::getSkills, HITPOINTS)
			),
			new StateBoundStatBox(
				manager,
				"att",
				"Attack",
				true,
				writer(Player::getSkills, ATTACK),
				reader(Player::getSkills, ATTACK)
			),
			new StateBoundStatBox(
				manager,
				"str",
				"Strength",
				true,
				writer(Player::getSkills, STRENGTH),
				reader(Player::getSkills, STRENGTH)
			),
			new StateBoundStatBox(
				manager,
				"def",
				"Defence",
				true,
				writer(Player::getSkills, DEFENCE),
				reader(Player::getSkills, DEFENCE)
			),
			new StateBoundStatBox(
				manager,
				"range",
				"Ranged",
				true,
				writer(Player::getSkills, RANGED),
				reader(Player::getSkills, RANGED)
			),
			new StateBoundStatBox(
				manager,
				"mage",
				"Magic",
				true,
				writer(Player::getSkills, MAGIC),
				reader(Player::getSkills, MAGIC)
			),
			new StateBoundStatBox(
				manager,
				"prayer",
				"Prayer",
				true,
				writer(Player::getSkills, PRAYER),
				reader(Player::getSkills, PRAYER)
			),
			new StateBoundStatBox(
				manager,
				"mining",
				"Mining",
				true,
				writer(Player::getSkills, MINING),
				reader(Player::getSkills, MINING)
			),
			new StateBoundStatBox(
				manager,
				"herblore",
				"Herblore",
				true,
				writer(Player::getSkills, HERBLORE),
				reader(Player::getSkills, HERBLORE)
			)
		);
		add(new StatCategory("Player Stats", statBoxes));

		add(Box.createVerticalStrut(10));

		boostBoxes = Arrays.asList(
			new StateBoundStatBox(
				manager,
				"hp",
				"Hitpoints",
				true,
				writer(Player::getBoosts, HITPOINTS),
				reader(Player::getBoosts, HITPOINTS)
			),
			new StateBoundStatBox(
				manager,
				"att",
				"Attack",
				true,
				writer(Player::getBoosts, ATTACK),
				reader(Player::getBoosts, ATTACK)
			),
			new StateBoundStatBox(
				manager,
				"str",
				"Strength",
				true,
				writer(Player::getBoosts, STRENGTH),
				reader(Player::getBoosts, STRENGTH)
			),
			new StateBoundStatBox(
				manager,
				"def",
				"Defence",
				true,
				writer(Player::getBoosts, DEFENCE),
				reader(Player::getBoosts, DEFENCE)
			),
			new StateBoundStatBox(
				manager,
				"range",
				"Ranged",
				true,
				writer(Player::getBoosts, RANGED),
				reader(Player::getBoosts, RANGED)
			),
			new StateBoundStatBox(
				manager,
				"mage",
				"Magic",
				true,
				writer(Player::getBoosts, MAGIC),
				reader(Player::getBoosts, MAGIC)
			),
			new StateBoundStatBox(
				manager,
				"prayer",
				"Prayer",
				true,
				writer(Player::getBoosts, PRAYER),
				reader(Player::getBoosts, PRAYER)
			),
			new StateBoundStatBox(
				manager,
				"mining",
				"Mining",
				true,
				writer(Player::getBoosts, MINING),
				reader(Player::getBoosts, MINING)
			),
			new StateBoundStatBox(
				manager,
				"herblore",
				"Herblore",
				true,
				writer(Player::getBoosts, HERBLORE),
				reader(Player::getBoosts, HERBLORE)
			)
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
			PlayerSkills skills = clientDataProvider.getPlayer().getSkills();
			getState().getPlayer().setSkills(skills);

			PlayerSkills boosts = clientDataProvider.getPlayer().getBoosts();
			getState().getPlayer().setBoosts(boosts);

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
		return !Stream.of(HITPOINTS, ATTACK, STRENGTH, DEFENCE, RANGED, MAGIC)
			.anyMatch(s -> getState().getPlayer().getSkills().get(s) == 0);
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
