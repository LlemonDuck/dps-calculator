package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJCheckBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.runelite.api.Skill;
import net.runelite.client.ui.PluginPanel;

public class NpcStatsPanel extends JPanel
{

	private final CustomJCheckBox manualEntry;
	private final CustomJComboBox<NpcData> npcSelect;

	private final List<NpcStatBox<Skills, Skills.SkillsBuilder>> skillBoxes;
	private final List<NpcStatBox<DefenderAttributes, DefenderAttributes.DefenderAttributesBuilder>> attrBoxes;
	private final List<NpcStatBox<DefensiveBonuses, DefensiveBonuses.DefensiveBonusesBuilder>> bonusBoxes;
	private final List<NpcCheckBox> flagChecks;

	@Inject
	public NpcStatsPanel(NpcDataProvider npcDataProvider)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		manualEntry = new CustomJCheckBox("Manual Entry Mode");
		manualEntry.setCallback(() -> setManualMode(manualEntry.getValue()));
		add(manualEntry);

		add(Box.createVerticalStrut(5));

		List<NpcData> npcs = npcDataProvider.getAll()
			.stream()
			.sorted(Comparator.comparing(npc -> npc.getAttributes().getName()))
			.collect(Collectors.toList());
		npcSelect = new CustomJComboBox<>(npcs, npc -> npc.getAttributes().getName(), null);
		npcSelect.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 25));
		npcSelect.enableAutocomplete();
		npcSelect.setCallback(() -> loadValues(npcSelect.getValue()));
		add(npcSelect);

		add(Box.createRigidArea(new Dimension(1, 5)));

		skillBoxes = new ArrayList<>(6);
		attrBoxes = new ArrayList<>(3);
		bonusBoxes = new ArrayList<>(5);
		flagChecks = new ArrayList<>(6);

		// levels
		skillBoxes.add(new NpcStatBox<>("hitpoints", skillGetter(Skill.HITPOINTS), skillSetter(Skill.HITPOINTS)));
		skillBoxes.add(new NpcStatBox<>("att", skillGetter(Skill.ATTACK), skillSetter(Skill.ATTACK)));
		skillBoxes.add(new NpcStatBox<>("str", skillGetter(Skill.STRENGTH), skillSetter(Skill.STRENGTH)));
		skillBoxes.add(new NpcStatBox<>("def", skillGetter(Skill.DEFENCE), skillSetter(Skill.DEFENCE)));
		skillBoxes.add(new NpcStatBox<>("mage", skillGetter(Skill.MAGIC), skillSetter(Skill.MAGIC)));
		skillBoxes.add(new NpcStatBox<>("range", skillGetter(Skill.RANGED), skillSetter(Skill.RANGED)));
		add(new StatCategory("Combat Stats", skillBoxes));
		add(Box.createVerticalStrut(5));

		// offensive bonuses
		attrBoxes.add(new NpcStatBox<>("amagic", DefenderAttributes::getNpcId, DefenderAttributes.DefenderAttributesBuilder::npcId));
		attrBoxes.add(new NpcStatBox<>("amagic", DefenderAttributes::getAccuracyMagic, DefenderAttributes.DefenderAttributesBuilder::accuracyMagic));
		attrBoxes.add(new NpcStatBox<>("size", DefenderAttributes::getSize, DefenderAttributes.DefenderAttributesBuilder::size));
		add(new StatCategory("Bonuses", attrBoxes));
		add(Box.createVerticalStrut(5));

		// defensive bonuses
		bonusBoxes.add(new NpcStatBox<>("dstab", DefensiveBonuses::getDefenseStab, DefensiveBonuses.DefensiveBonusesBuilder::defenseStab));
		bonusBoxes.add(new NpcStatBox<>("dslash", DefensiveBonuses::getDefenseSlash, DefensiveBonuses.DefensiveBonusesBuilder::defenseSlash));
		bonusBoxes.add(new NpcStatBox<>("dcrush", DefensiveBonuses::getDefenseCrush, DefensiveBonuses.DefensiveBonusesBuilder::defenseCrush));
		bonusBoxes.add(new NpcStatBox<>("dmagic", DefensiveBonuses::getDefenseMagic, DefensiveBonuses.DefensiveBonusesBuilder::defenseMagic));
		bonusBoxes.add(new NpcStatBox<>("drange", DefensiveBonuses::getDefenseRanged, DefensiveBonuses.DefensiveBonusesBuilder::defenseRanged));
		add(new StatCategory("Defensive Bonuses", bonusBoxes));
		add(Box.createVerticalStrut(5));

		flagChecks.add(new NpcCheckBox("Demon", DefenderAttributes::isDemon, DefenderAttributes.DefenderAttributesBuilder::isDemon));
		flagChecks.add(new NpcCheckBox("Dragon", DefenderAttributes::isDragon, DefenderAttributes.DefenderAttributesBuilder::isDragon));
		flagChecks.add(new NpcCheckBox("Kalphite", DefenderAttributes::isKalphite, DefenderAttributes.DefenderAttributesBuilder::isKalphite));
		flagChecks.add(new NpcCheckBox("Leafy", DefenderAttributes::isLeafy, DefenderAttributes.DefenderAttributesBuilder::isLeafy));
		flagChecks.add(new NpcCheckBox("Undead", DefenderAttributes::isUndead, DefenderAttributes.DefenderAttributesBuilder::isUndead));
		flagChecks.add(new NpcCheckBox("Vampyre", DefenderAttributes::isVampyre, DefenderAttributes.DefenderAttributesBuilder::isVampyre));
		flagChecks.forEach(this::add);
	}

	private ToIntFunction<Skills> skillGetter(Skill skill)
	{
		return skills -> skills.getLevels().getOrDefault(skill, 0);
	}

	private ObjIntConsumer<Skills.SkillsBuilder> skillSetter(Skill skill)
	{
		return (builder, lvl) -> builder.level(skill, lvl);
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
		return manualEntry.getValue() || npcSelect.getValue() != null;
	}

	public NpcData toNpcStats()
	{
		if (!manualEntry.getValue())
		{
			return npcSelect.getValue();
		}

		Skills.SkillsBuilder skillsBuilder = Skills.builder();
		skillBoxes.forEach(sb -> sb.consumeValue(skillsBuilder));

		DefenderAttributes.DefenderAttributesBuilder attrBuilder = DefenderAttributes.builder();
		attrBoxes.forEach(ab -> ab.consumeValue(attrBuilder));
		flagChecks.forEach(fc -> fc.consumeValue(attrBuilder));

		DefensiveBonuses.DefensiveBonusesBuilder bonusesBuilder = DefensiveBonuses.builder();
		bonusBoxes.forEach(bb -> bb.consumeValue(bonusesBuilder));

		return NpcData.builder()
			.skills(skillsBuilder.build())
			.attributes(attrBuilder.build())
			.defensiveBonuses(bonusesBuilder.build())
			.build();
	}

	public void loadNpcStats(NpcData stats)
	{
		manualEntry.setValue(false);
		npcSelect.setValue(stats);
		loadValues(stats);
	}

	// skips combo box setting
	private void loadValues(NpcData stats)
	{
		skillBoxes.forEach(sb -> sb.setValue(stats.getSkills()));
		attrBoxes.forEach(ab -> ab.setValue(stats.getAttributes()));
		flagChecks.forEach(fc -> fc.setValue(stats.getAttributes()));
		bonusBoxes.forEach(bb -> bb.setValue(stats.getDefensiveBonuses()));
	}

	private void setAllEditable(boolean editable)
	{
		skillBoxes.forEach(sb -> sb.setEditable(editable));
		attrBoxes.forEach(ab -> ab.setEditable(editable));
		flagChecks.forEach(fc -> fc.setEditable(editable));
		bonusBoxes.forEach(bb -> bb.setEditable(editable));
	}

	public String getSummary()
	{
		if (!isReady())
		{
			return "Not Set";
		}

		return manualEntry.getValue() ? "Entered Manually" : npcSelect.getValue().getAttributes().getName();
	}

}
