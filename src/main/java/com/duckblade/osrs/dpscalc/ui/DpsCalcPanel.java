package com.duckblade.osrs.dpscalc.ui;

import com.duckblade.osrs.dpscalc.calc.CalcInput;
import com.duckblade.osrs.dpscalc.calc.CalcManager;
import com.duckblade.osrs.dpscalc.calc.CalcResult;
import com.duckblade.osrs.dpscalc.model.EquipmentStats;
import com.duckblade.osrs.dpscalc.model.ItemStats;
import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.model.WeaponMode;
import com.duckblade.osrs.dpscalc.ui.equip.EquipmentPanel;
import com.duckblade.osrs.dpscalc.ui.npc.NpcStatsPanel;
import com.duckblade.osrs.dpscalc.ui.prayer.PrayerPanel;
import com.duckblade.osrs.dpscalc.ui.result.CalcResultPanel;
import com.duckblade.osrs.dpscalc.ui.skills.SkillsPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.client.ui.PluginPanel;

public class DpsCalcPanel extends JPanel
{

	private final CalcManager calcManager;

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
	public DpsCalcPanel(CalcManager calcManager, NpcStatsPanel npcStatsPanel, EquipmentPanel equipmentPanel, SkillsPanel skillsPanel, PrayerPanel prayerPanel, CalcResultPanel resultPanel)
	{
		this.calcManager = calcManager;

		this.npcStatsPanel = npcStatsPanel;
		this.equipmentPanel = equipmentPanel;
		this.skillsPanel = skillsPanel;
		this.prayerPanel = prayerPanel;
		this.resultPanel = resultPanel;

		this.npcStatsPanel.setOnUpdated(this::updateNav);
		this.equipmentPanel.setOnUpdated(this::updateNav);
		this.skillsPanel.setOnUpdated(this::updateNav);
		this.prayerPanel.setOnUpdated(this::updateNav);

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

			WeaponMode weaponMode = equipmentPanel.getWeaponMode();
			Map<EquipmentInventorySlot, ItemStats> equipment = equipmentPanel.getEquipment();

			CalcInput input = CalcInput.builder()
					.npcTarget(npcStatsPanel.toNpcStats())
					.combatMode(weaponMode.getMode())
					.weaponMode(weaponMode)
					.playerEquipment(equipment)
					.equipmentStats(EquipmentStats.fromMap(equipment, weaponMode, equipmentPanel.getTbpDarts()))
					.playerSkills(skillsPanel.getSkills())
					.playerBoosts(skillsPanel.getBoosts())
					.spell(equipmentPanel.getSpell())
					.onSlayerTask(equipmentPanel.isOnSlayerTask())
					.activeHp(equipmentPanel.getActiveHp())
					.maxHp(equipmentPanel.getMaxHp())
					.usingMarkOfDarkness(equipmentPanel.isUsingMarkOfDarkness())
					.enabledPrayers(prayerPanel.getSelected())
					.prayerDrain(prayerPanel.getDrain())
					.build();

			CalcResult result = calcManager.calculateDPS(input);
			resultPanel.setValue(result);
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

	public void openNpcStats(NpcStats preload)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (preload != null)
				npcStatsPanel.loadNpcStats(preload);
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

	public void onEquipmentChanged()
	{
		equipmentPanel.loadFromClient();
	}
	public void onPrayersChanged()
	{
		prayerPanel.loadFromClient();
	}

	public void onStatChanged()
	{
		skillsPanel.loadFromClient();
	}

	public void onTargetChanged(NpcStats stats)
	{
		npcStatsPanel.loadNpcStats(stats);
	}

	public void updateNav() {
		SwingUtilities.invokeLater(() ->
		{
			npcStatsNav.setDescription(npcStatsPanel.getSummary());
			equipmentNav.setDescription(equipmentPanel.getSummary());
			skillsNav.setDescription(skillsPanel.getSummary());
			prayerNav.setDescription(prayerPanel.getSummary());
			calculateDps();
			revalidate();
			repaint();
		});
	}
}
