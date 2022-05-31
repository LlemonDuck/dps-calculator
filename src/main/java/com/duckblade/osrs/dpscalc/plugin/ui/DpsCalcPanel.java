package com.duckblade.osrs.dpscalc.plugin.ui;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcData;
import com.duckblade.osrs.dpscalc.plugin.ui.equip.EquipmentPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.npc.NpcStatsPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.prayer.PrayerPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.result.CalcResultPanel;
import com.duckblade.osrs.dpscalc.plugin.ui.skills.SkillsPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.client.ui.PluginPanel;

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

	@Getter
	private final String panelId; // for cardlayout
	private static final AtomicInteger panelIdGenerator = new AtomicInteger(1); // to generate panelId

	@Inject
	public DpsCalcPanel(NpcStatsPanel npcStatsPanel, EquipmentPanel equipmentPanel, SkillsPanel skillsPanel, PrayerPanel prayerPanel, CalcResultPanel resultPanel)
	{
		this.npcStatsPanel = npcStatsPanel;
		this.equipmentPanel = equipmentPanel;
		this.skillsPanel = skillsPanel;
		this.prayerPanel = prayerPanel;
		this.resultPanel = resultPanel;

		this.panelId = Integer.toString(panelIdGenerator.getAndIncrement());

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		menuPanel = new JPanel();
		menuPanel.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		add(menuPanel);

		npcStatsNav = new MenuPanelNavEntry("NPC Stats", "Not Set", () -> openNpcStats(null));
		menuPanel.add(npcStatsNav);
		menuPanel.add(Box.createVerticalStrut(5));

		equipmentNav = new MenuPanelNavEntry("Equipment", "Not Set", this::openEquipment);
		menuPanel.add(equipmentNav);
		menuPanel.add(Box.createVerticalStrut(5));

		skillsNav = new MenuPanelNavEntry("Skills", "Not Set", this::openSkills);
		menuPanel.add(skillsNav);
		menuPanel.add(Box.createVerticalStrut(5));

		prayerNav = new MenuPanelNavEntry("Prayer", "Not Set", this::openPrayer);
		menuPanel.add(prayerNav);
		menuPanel.add(Box.createVerticalStrut(20));

		menuPanel.add(resultPanel);
	}

	private void calculateDps()
	{
		SwingUtilities.invokeLater(() ->
		{
			if (!npcStatsPanel.isReady() || !equipmentPanel.isReady() || !skillsPanel.isReady())
			{
				resultPanel.setValue(null);
				return;
			}

			NpcData npc = npcStatsPanel.toNpcStats();

			ComputeInput input = ComputeInput.builder()
				.attackerSkills(Skills.builder()
					.levels(skillsPanel.getSkills())
					.boosts(skillsPanel.getBoosts())
					.build())
				.attackerItems(equipmentPanel.getEquipment())
				.attackerPrayers(prayerPanel.getSelected())
				.attackStyle(equipmentPanel.getAttackStyle())
				.spell(equipmentPanel.getSpell())
				.blowpipeDarts(equipmentPanel.getTbpDarts())
				.defenderSkills(npc.getSkills())
				.defenderBonuses(npc.getDefensiveBonuses())
				.defenderAttributes(npc.getAttributes())
				.onSlayerTask(equipmentPanel.isOnSlayerTask())
				.usingMarkOfDarkness(equipmentPanel.isUsingMarkOfDarkness())
				.inWilderness(equipmentPanel.isInWilderness())
				.build();

			resultPanel.setValue(new ComputeContext(input));
		});
	}

	public void openMenu()
	{
		SwingUtilities.invokeLater(() ->
		{
			removeAll();
			add(menuPanel, BorderLayout.CENTER);
			npcStatsNav.setDescription(npcStatsPanel.getSummary());
			equipmentNav.setDescription(equipmentPanel.getSummary());
			skillsNav.setDescription(skillsPanel.getSummary());
			prayerNav.setDescription(prayerPanel.getSummary());
			calculateDps();
			revalidate();
			repaint();
		});
	}

	public void openNpcStats(NpcData preload)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (preload != null)
			{
				npcStatsPanel.loadNpcStats(preload);
			}
			removeAll();
			add(npcStatsPanel, BorderLayout.CENTER);
			revalidate();
			repaint();
		});
	}

	public void openEquipment()
	{
		SwingUtilities.invokeLater(() ->
		{
			removeAll();
			add(equipmentPanel, BorderLayout.CENTER);
			revalidate();
			repaint();
		});
	}

	public void openSkills()
	{
		SwingUtilities.invokeLater(() ->
		{
			removeAll();
			add(skillsPanel, BorderLayout.CENTER);
			revalidate();
			repaint();
		});
	}

	public void openPrayer()
	{
		SwingUtilities.invokeLater(() ->
		{
			removeAll();
			add(prayerPanel, BorderLayout.CENTER);
			revalidate();
			repaint();
		});
	}

}