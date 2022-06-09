package com.duckblade.osrs.dpscalc.plugin.ui;

import com.duckblade.osrs.dpscalc.plugin.ui.equip.EquipmentPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.npc.NpcStatsPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.prayer.PrayerPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.result.CalcResultPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.skills.SkillsPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class DpsCalcPanel extends JPanel
{

	private final JPanel menuPanel;

	private final MenuPanelNavEntry npcStatsNav;
	private final NpcStatsPanel npcStatsPanel;

	private final MenuPanelNavEntry equipmentNav;
	private final EquipmentPanel equipmentPanel;

	private final MenuPanelNavEntry skillsNav;
	private final SkillsPanel skillsPanel;

	private final MenuPanelNavEntry prayerNav;
	private final PrayerPanel prayerPanel;

	private final CalcResultPanel resultPanel;

	@Inject
	public DpsCalcPanel(NpcStatsPanel npcStatsPanel, EquipmentPanel equipmentPanel, SkillsPanel skillsPanel, PrayerPanel prayerPanel, CalcResultPanel resultPanel)
	{
		this.npcStatsPanel = npcStatsPanel;
		this.equipmentPanel = equipmentPanel;
		this.skillsPanel = skillsPanel;
		this.prayerPanel = prayerPanel;
		this.resultPanel = resultPanel;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		menuPanel = new JPanel();
		menuPanel.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		add(menuPanel);

		JButton loadAllFromClientButton = new JButton("Load All From Client");
		loadAllFromClientButton.addActionListener(e -> loadAllFromClient());
		loadAllFromClientButton.setAlignmentX(CENTER_ALIGNMENT);
		menuPanel.add(loadAllFromClientButton);
		menuPanel.add(Box.createVerticalStrut(10));

		npcStatsNav = new MenuPanelNavEntry("NPC Stats", "Not Set", () -> openPanel(npcStatsPanel));
		menuPanel.add(npcStatsNav);
		menuPanel.add(Box.createVerticalStrut(5));

		equipmentNav = new MenuPanelNavEntry("Equipment", "Not Set", () -> openPanel(equipmentPanel));
		menuPanel.add(equipmentNav);
		menuPanel.add(Box.createVerticalStrut(5));

		skillsNav = new MenuPanelNavEntry("Skills", "Not Set", () -> openPanel(skillsPanel));
		menuPanel.add(skillsNav);
		menuPanel.add(Box.createVerticalStrut(5));

		prayerNav = new MenuPanelNavEntry("Prayer", "None", () -> openPanel(prayerPanel));
		menuPanel.add(prayerNav);
		menuPanel.add(Box.createVerticalStrut(20));

		menuPanel.add(resultPanel);
	}

	private void updateResultPanel()
	{
		SwingUtilities.invokeLater(() ->
		{
			resultPanel.clear();
			if (npcStatsPanel.isReady() && equipmentPanel.isReady() && skillsPanel.isReady())
			{
				resultPanel.fromState();
			}
		});
	}

	public void openMenu()
	{
		SwingUtilities.invokeLater(() ->
		{
			removeAll();
			add(menuPanel, BorderLayout.CENTER);

			npcStatsPanel.fromState();
			npcStatsNav.setDescription(npcStatsPanel.getSummary());

			equipmentPanel.fromState();
			equipmentNav.setDescription(equipmentPanel.getSummary());

			skillsPanel.fromState();
			skillsNav.setDescription(skillsPanel.getSummary());

			prayerPanel.fromState();
			prayerNav.setDescription(prayerPanel.getSummary());

			updateResultPanel();
			revalidate();
			repaint();
		});
	}

	public void openPanel(Component c)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (c instanceof StateBoundComponent)
			{
				((StateBoundComponent) c).fromState();
			}

			removeAll();
			add(c, BorderLayout.CENTER);
			revalidate();
			repaint();
		});
	}

	public void openNpcPanel()
	{
		openPanel(npcStatsPanel);
	}

	public void loadAllFromClient()
	{
		equipmentPanel.loadFromClient();
		skillsPanel.loadFromClient();
		prayerPanel.loadFromClient();
		openMenu();
	}

}