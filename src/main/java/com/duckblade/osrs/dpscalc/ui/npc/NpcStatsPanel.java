package com.duckblade.osrs.dpscalc.ui.npc;

import com.duckblade.osrs.dpscalc.NpcDataManager;
import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.ui.util.CustomJComboBox;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class NpcStatsPanel extends JPanel
{

	private final JCheckBox manualEntry;
	private final CustomJComboBox<NpcStats> npcSelect;

	private final List<NpcStatBox> statBoxes;
	private final List<NpcCheckBox> flagChecks;

	@Inject
	public NpcStatsPanel(NpcDataManager npcDataManager)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel manualEntryPanel = new JPanel();
		manualEntryPanel.setLayout(new BorderLayout());
		add(manualEntryPanel);

		manualEntry = new JCheckBox();
		manualEntry.addActionListener(e -> setManualMode(manualEntry.isSelected()));
		manualEntryPanel.add(manualEntry, BorderLayout.WEST);

		JLabel manualEntryLabel = new JLabel("Manual Entry Mode");
		manualEntryLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				manualEntry.setSelected(!manualEntry.isSelected());
				setManualMode(manualEntry.isSelected());
			}
		});
		manualEntryPanel.add(manualEntryLabel, BorderLayout.CENTER);

		add(Box.createRigidArea(new Dimension(1, 5)));

		npcSelect = new CustomJComboBox<>(npcDataManager.getAll(), NpcStats::getName, null);
		npcSelect.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 25));
		npcSelect.enableAutocomplete();
		npcSelect.setCallback(() -> loadValues(npcSelect.getValue()));
		add(npcSelect);

		add(Box.createRigidArea(new Dimension(1, 5)));

		statBoxes = new ArrayList<>(6 + 6 + 5);
		flagChecks = new ArrayList<>(6);

		// levels
		statBoxes.add(new NpcStatBox("hitpoints", NpcStats::getLevelHp, NpcStats.NpcStatsBuilder::levelHp));
		statBoxes.add(new NpcStatBox("att", NpcStats::getLevelAttack, NpcStats.NpcStatsBuilder::levelAttack));
		statBoxes.add(new NpcStatBox("str", NpcStats::getLevelStrength, NpcStats.NpcStatsBuilder::levelStrength));
		statBoxes.add(new NpcStatBox("def", NpcStats::getLevelDefense, NpcStats.NpcStatsBuilder::levelDefense));
		statBoxes.add(new NpcStatBox("mage", NpcStats::getLevelMagic, NpcStats.NpcStatsBuilder::levelMagic));
		statBoxes.add(new NpcStatBox("range", NpcStats::getLevelRanged, NpcStats.NpcStatsBuilder::levelRanged));
		add(new StatCategory("Combat Stats", statBoxes.subList(0, 6)));
		add(Box.createVerticalStrut(5));

		// offensive bonuses
		statBoxes.add(new NpcStatBox("attbns", NpcStats::getAttackBonus, NpcStats.NpcStatsBuilder::attackBonus));
		statBoxes.add(new NpcStatBox("strbns", NpcStats::getStrengthBonus, NpcStats.NpcStatsBuilder::strengthBonus));
		statBoxes.add(new NpcStatBox("amagic", NpcStats::getMagicAccuracy, NpcStats.NpcStatsBuilder::magicAccuracy));
		statBoxes.add(new NpcStatBox("mbns", NpcStats::getMagicDamage, NpcStats.NpcStatsBuilder::magicDamage));
		statBoxes.add(new NpcStatBox("arange", NpcStats::getRangedAccuracy, NpcStats.NpcStatsBuilder::rangedAccuracy));
		statBoxes.add(new NpcStatBox("rngbns", NpcStats::getRangedStrength, NpcStats.NpcStatsBuilder::rangedStrength));
		add(new StatCategory("Offensive Bonuses", statBoxes.subList(6, 12)));
		add(Box.createVerticalStrut(5));

		// defensive bonuses
		statBoxes.add(new NpcStatBox("dstab", NpcStats::getDefenseStab, NpcStats.NpcStatsBuilder::defenseStab));
		statBoxes.add(new NpcStatBox("dslash", NpcStats::getDefenseSlash, NpcStats.NpcStatsBuilder::defenseSlash));
		statBoxes.add(new NpcStatBox("dcrush", NpcStats::getDefenseCrush, NpcStats.NpcStatsBuilder::defenseCrush));
		statBoxes.add(new NpcStatBox("dmagic", NpcStats::getDefenseMagic, NpcStats.NpcStatsBuilder::defenseMagic));
		statBoxes.add(new NpcStatBox("drange", NpcStats::getDefenseRanged, NpcStats.NpcStatsBuilder::defenseRanged));
		add(new StatCategory("Defensive Bonuses", statBoxes.subList(12, 17)));
		add(Box.createVerticalStrut(5));

		flagChecks.add(new NpcCheckBox("Demon", NpcStats::isDemon, NpcStats.NpcStatsBuilder::isDemon));
		flagChecks.add(new NpcCheckBox("Dragon", NpcStats::isDragon, NpcStats.NpcStatsBuilder::isDragon));
		flagChecks.add(new NpcCheckBox("Kalphite", NpcStats::isKalphite, NpcStats.NpcStatsBuilder::isKalphite));
		flagChecks.add(new NpcCheckBox("Leafy", NpcStats::isLeafy, NpcStats.NpcStatsBuilder::isLeafy));
		flagChecks.add(new NpcCheckBox("Undead", NpcStats::isUndead, NpcStats.NpcStatsBuilder::isUndead));
		flagChecks.add(new NpcCheckBox("Vampyre", NpcStats::isVampyre, NpcStats.NpcStatsBuilder::isVampyre));
		flagChecks.forEach(this::add);
	}

	public void setManualMode(boolean manualMode)
	{
		if (!manualMode)
		{
			npcSelect.setValue(null);
		}
		npcSelect.setVisible(!manualMode);
		setAllEditable(manualMode);
	}

	public boolean isReady()
	{
		return manualEntry.isSelected() || npcSelect.getValue() != null;
	}

	public NpcStats toNpcStats()
	{
		if (!manualEntry.isSelected())
			return npcSelect.getValue();

		NpcStats.NpcStatsBuilder builder = NpcStats.builder();
		statBoxes.forEach(sb -> sb.consumeValue(builder));
		flagChecks.forEach(fc -> fc.consumeValue(builder));
		return builder.build();
	}

	public void loadNpcStats(NpcStats stats)
	{
		manualEntry.setSelected(false);
		npcSelect.setValue(stats);
		loadValues(stats);
	}

	// skips combo box setting
	private void loadValues(NpcStats stats)
	{
		statBoxes.forEach(sb -> sb.setValue(stats));
		flagChecks.forEach(fc -> fc.setValue(stats));
	}

	private void setAllEditable(boolean editable)
	{
		statBoxes.forEach(sb -> sb.setEditable(editable));
		flagChecks.forEach(fc -> fc.setEditable(editable));
	}

	public String getSummary()
	{
		if (!isReady())
			return "Not Set";

		return manualEntry.isSelected() ? "Entered Manually" : npcSelect.getValue().getName();
	}

}
