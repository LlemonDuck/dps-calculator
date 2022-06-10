package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.DefenderSkillsComputable;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundStatBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.ComputeUtil;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.JPanel;
import lombok.Getter;
import net.runelite.api.Skill;

@Singleton
public class NpcSkillsPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<PanelState> writer(Skill skill)
	{
		return (state, lvl) -> state.getDefenderSkills().put(skill, lvl);
	}

	private static ToIntFunction<PanelState> reader(Skill skill)
	{
		return state -> state.getDefenderSkills().getOrDefault(skill, 0);
	}

	@Getter
	private final PanelStateManager manager;
	private final Map<Skill, StateBoundStatBox> skillBoxes = new HashMap<>(6);
	private final DefenderSkillsComputable defenderSkillsComputable;

	@Inject
	public NpcSkillsPanel(PanelStateManager manager, DefenderSkillsComputable defenderSkillsComputable)
	{
		this.manager = manager;
		this.defenderSkillsComputable = defenderSkillsComputable;

		skillBoxes.put(Skill.HITPOINTS, new StateBoundStatBox(manager, "hitpoints", "Hitpoints", false, writer(Skill.HITPOINTS), reader(Skill.HITPOINTS)));
		skillBoxes.put(Skill.ATTACK, new StateBoundStatBox(manager, "att", "Attack", false, writer(Skill.ATTACK), reader(Skill.ATTACK)));
		skillBoxes.put(Skill.STRENGTH, new StateBoundStatBox(manager, "str", "Strength", false, writer(Skill.STRENGTH), reader(Skill.STRENGTH)));
		skillBoxes.put(Skill.DEFENCE, new StateBoundStatBox(manager, "def", "Defence", false, writer(Skill.DEFENCE), reader(Skill.DEFENCE)));
		skillBoxes.put(Skill.MAGIC, new StateBoundStatBox(manager, "mage", "Magic", false, writer(Skill.MAGIC), reader(Skill.MAGIC)));
		skillBoxes.put(Skill.RANGED, new StateBoundStatBox(manager, "range", "Ranged", false, writer(Skill.RANGED), reader(Skill.RANGED)));
		add(new StatCategory("Combat Stats", new ArrayList<>(skillBoxes.values())));
		add(Box.createVerticalStrut(5));
		setMaximumSize(new Dimension(200, 134));
	}

	@Override
	public void toState()
	{
		skillBoxes.values().forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		skillBoxes.values().forEach(StateBoundStatBox::fromState);
	}

	public void fromScaled()
	{
		Skills scaled = ComputeUtil.tryCompute(() ->
		{
			ComputeContext ctx = new ComputeContext();
			ctx.put(ComputeInputs.DEFENDER_ATTRIBUTES, getState().getDefenderAttributes().toImmutable());
			ctx.put(ComputeInputs.DEFENDER_SKILLS, Skills.builder().levels(getState().getDefenderSkills()).build());
			ctx.put(ComputeInputs.RAID_PARTY_SIZE, getState().getRaidPartySize());

			return ctx.get(defenderSkillsComputable);
		});

		if (scaled != null)
		{
			skillBoxes.forEach((s, sbsb) -> sbsb.setValue(scaled.getTotals().getOrDefault(s, 0)));
		}
		else
		{
			fromState();
		}
	}

	public void setEditable(boolean editable)
	{
		skillBoxes.values().forEach(sbsb -> sbsb.setEditable(editable));
	}
}
