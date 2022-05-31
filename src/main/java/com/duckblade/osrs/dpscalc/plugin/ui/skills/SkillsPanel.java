package com.duckblade.osrs.dpscalc.plugin.ui.skills;

import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
import com.google.common.collect.ImmutableMap;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import static net.runelite.api.Skill.ATTACK;
import static net.runelite.api.Skill.DEFENCE;
import static net.runelite.api.Skill.MAGIC;
import static net.runelite.api.Skill.PRAYER;
import static net.runelite.api.Skill.RANGED;
import static net.runelite.api.Skill.STRENGTH;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.ui.PluginPanel;

public class SkillsPanel extends JPanel
{

	private final Client client;
	private final ClientThread clientThread;

	private final Map<Skill, StatBox> statBoxes;
	private final Map<Skill, StatBox> boostBoxes;

	@Inject
	public SkillsPanel(@Nullable Client client, @Nullable ClientThread clientThread)
	{
		this.client = client;
		this.clientThread = clientThread;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton loadFromClientButton = new JButton("Load From Client");
		loadFromClientButton.addActionListener(e -> loadFromClient());
		loadFromClientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(loadFromClientButton);

		add(Box.createVerticalStrut(10));

		statBoxes = new ImmutableMap.Builder<Skill, StatBox>()
			.put(ATTACK, new StatBox("att", true))
			.put(STRENGTH, new StatBox("str", true))
			.put(DEFENCE, new StatBox("def", true))
			.put(MAGIC, new StatBox("mage", true))
			.put(RANGED, new StatBox("range", true))
			.put(PRAYER, new StatBox("prayer", true))
			.build();
		add(new StatCategory("Player Stats", new ArrayList<>(statBoxes.values())));

		add(Box.createVerticalStrut(10));

		boostBoxes = new ImmutableMap.Builder<Skill, StatBox>()
			.put(ATTACK, new StatBox("att", true))
			.put(STRENGTH, new StatBox("str", true))
			.put(DEFENCE, new StatBox("def", true))
			.put(MAGIC, new StatBox("mage", true))
			.put(RANGED, new StatBox("range", true))
			.put(PRAYER, new StatBox("prayer", true))
			.build();
		add(new StatCategory("Boosts", new ArrayList<>(boostBoxes.values())));

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

	private void loadFromClient()
	{
		if (client == null || clientThread == null)
		{
			return; // ui test
		}

		clientThread.invokeLater(() ->
		{
			Player p = client.getLocalPlayer();
			if (p == null)
			{
				return;
			}

			statBoxes.forEach((skill, box) -> box.setValue(client.getRealSkillLevel(skill)));
		});
	}

	public boolean isReady()
	{
		return statBoxes.values()
			.stream()
			.anyMatch(sb -> sb.getValue() == 0);
	}

	public String getSummary()
	{
		if (isReady())
		{
			return "Set";
		}

		return "Not Set";
	}

	public Map<Skill, Integer> getSkills()
	{
		Map<Skill, Integer> results = new HashMap<>(6);
		statBoxes.forEach((k, v) -> results.put(k, v.getValue()));
		return results;
	}

	public void setSkills(Map<Skill, Integer> newSkills)
	{
		newSkills.forEach((s, v) -> statBoxes.get(s).setValue(v));
	}

	public Map<Skill, Integer> getBoosts()
	{

		Map<Skill, Integer> results = new HashMap<>(6);
		boostBoxes.forEach((k, v) -> results.put(k, v.getValue()));
		return results;
	}

	public void setBoosts(Map<Skill, Integer> newSkills)
	{
		newSkills.forEach((s, v) -> boostBoxes.get(s).setValue(v));
	}

	public void applyBoostPreset(SkillBoostPreset boostPreset)
	{
		if (boostPreset != null)
		{
			boostPreset.apply(statBoxes, boostBoxes);
		}
	}
}
